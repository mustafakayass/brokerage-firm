package com.kayas.brokerageFirm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Entity
@Table(name = "`ORDER`")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long userId;

    @Setter
    private String assetName;

    @Setter
    private BigDecimal size;

    @Setter
    private BigDecimal price;

    @Setter
    private String status;

    @Setter
    private String orderSide;

    @Setter
    private Date createDate;

    public Order(Long userId, String assetName, String orderSide, BigDecimal size, BigDecimal price, String status, Date createDate) {
        this.userId = userId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
        this.status = status;
        this.createDate = createDate;
    }
}
