package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name="MARKET_ORDER")
public class Order extends BaseIdAndTime {

    private LocalDateTime requestPaymentDate; // 지불 요청 날짜
    private LocalDateTime paymentDate; // 지불 처리 날짜
    private LocalDateTime cancelDate;
    @ManyToOne(fetch = LAZY)
    private MarketMember buyer;
    private long price;
    private long salePrice;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getBuyer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product) {
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );

        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();
    }

    public void completePayment() {
        paymentDate = LocalDateTime.now();
        publishEvent(
                new MarketOrderPaymentCompletedEvent(
                        toDto()
                )
        );
    }

    public boolean isPaid() {
        return paymentDate != null;
    }

    public void requestPayment(long pgPaymentAmount) {
        requestPaymentDate = LocalDateTime.now();

        publishEvent(
                new MarketOrderPaymentRequestedEvent(
                        this.toDto(),
                        pgPaymentAmount
                )
        );
    }

    public void cancelRequestPayment() {
        requestPaymentDate = null;
    }

    public boolean isCanceled() {
        return cancelDate != null;
    }

    public boolean isPaymentInProgress() {
        return requestPaymentDate != null && paymentDate == null && cancelDate == null;
    }
    public OrderDto toDto(){
        return new OrderDto(
            this.getId(),
            this.getCreateDate(),
            this.getModifyDate(),
            this.getBuyer().getId(),
            this.getBuyer().getNickname(),
            this.getPrice(),
            this.getSalePrice(),
            this.getRequestPaymentDate(),
            this.getPaymentDate()
        );
    }
}
