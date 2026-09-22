package com.back.boundedContext.market.app;

import com.back.boundedContext.market.out.OrderRepository;
import com.back.shared.Cash.event.CashOrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    public void handle(CashOrderPaymentSucceededEvent event) {
        Order order = orderRepository.findById(event.getOrder().getId()).get();

        order.completePayment();
    }
}
