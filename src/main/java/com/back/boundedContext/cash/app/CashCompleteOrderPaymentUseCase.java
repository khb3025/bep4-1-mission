package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void completeOrderPayment(OrderDto orderDto, long pgPaymentAmount) {
        Wallet customerWallet = cashSupport.findWalletByHolderId(orderDto.getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        // requestPayment 의 두번째 인자는 충전할 금액
        // 충전할 금액이 있으면 wallet에 balance(잔액)에 충전
        if (pgPaymentAmount > 0) {
            customerWallet.credit(
                    pgPaymentAmount,
                    CashLog.EventType.충전__PG결제_토스페이먼츠,
                    orderDto.getModelTypeCode(),
                    orderDto.getId()
            );
        }

        // 지불 여부 확인 (잔액이 주문 금액보다 크거나 같으면 true)
        boolean canPay = customerWallet.getBalance() >= orderDto.getSalePrice();

        if (canPay) {

            // 지불 할 수 있으면 금액 차감
            customerWallet.debit(
                    orderDto.getSalePrice(),
                    CashLog.EventType.사용__주문결제,
                    orderDto.getModelTypeCode(),
                    orderDto.getId()
            );

            // 이커머스 플랫폼이 관리하는 정산 계정 (사용자 금액 차감에 대한 임시보관)
            holdingWallet.credit(
                    orderDto.getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    orderDto.getModelTypeCode(),
                    orderDto.getId()
            );

            // 캐시 주문 결제 성공 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentSucceededEvent(
                            orderDto,
                            pgPaymentAmount
                    )
            );
        } else {
            // 캐시 주문 결제 실패 이벤트 발행
            eventPublisher.publish(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(orderDto.getId()),
                            orderDto,
                            pgPaymentAmount,
                            orderDto.getSalePrice() - customerWallet.getBalance()
                    )
            );
        }
    }
}
