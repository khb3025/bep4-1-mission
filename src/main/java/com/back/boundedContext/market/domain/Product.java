package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Table(name="MARKET_PRODUCT")
@Entity
public class Product extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY)
    private MarketMember seller;
    private String sourceTypeCode;
    private int sourceId;
    private String name;
    private String description;
    private long price;
    private long salePrice;

    public Product(
            MarketMember seller,
            String sourceTypeCode,
            int sourceId,
            String name,
            String description,
            long price,
            long salePrice
    ) {
        this.seller = seller;
        this.sourceTypeCode = sourceTypeCode;
        this.sourceId = sourceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.salePrice = salePrice;
    }

}
