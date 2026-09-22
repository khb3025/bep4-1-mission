package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.Cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.Cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void handle(MarketOrderPaymentRequestedEvent event) {
        Wallet customerWallet = cashSupport.findWalletByHolderId(event.getOrder().getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        // requestPayment 의 두번째 인자는 충전할 금액
        // 충전할 금액이 있으면 wallet에 balance(잔액)에 충전
        if (event.getPgPaymentAmount() > 0) {
            customerWallet.credit(
                    event.getPgPaymentAmount(),
                    CashLog.EventType.충전__PG결제_토스페이먼츠,
                    "Order",
                    event.getOrder().getId()
            );
        }

        // 지불 여부 확인 (잔액이 주문 금액보다 크거나 같으면 true)
        boolean canPay = customerWallet.getBalance() >= event.getOrder().getSalePrice();

        if (canPay) {

            // 지불 할 수 있으면 금액 차감
            customerWallet.debit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.사용__주문결제,
                    "Order",
                    event.getOrder().getId()
            );

            // 이커머스 플랫폼이 관리하는 정산 계정 (사용자 금액 차감에 대한 임시보관)
            holdingWallet.credit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    "Order",
                    event.getOrder().getId()
            );

            // 캐시 주문 결제 성공 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentSucceededEvent(
                            event.getOrder(),
                            event.getPgPaymentAmount()
                    )
            );
        } else {
            // 캐시 주문 결제 실패 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(event.getOrder().getId()),
                            event.getOrder(),
                            event.getPgPaymentAmount(),
                            event.getOrder().getSalePrice() - customerWallet.getBalance()
                    )
            );
        }
    }
}
