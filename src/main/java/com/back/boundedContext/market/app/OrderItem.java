package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Product;
import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
@Table(name="MARKET_ORDER_ITEM")
public class OrderItem extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private Order order;

    @ManyToOne(fetch = LAZY)
    private Product product;

    private String productName;

    private long price;

    private long salePrice;

    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, String productName, long price, long salePrice) {
        this.order = order;
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
    }
}
