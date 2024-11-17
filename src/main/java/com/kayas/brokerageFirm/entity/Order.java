package com.kayas.brokerageFirm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double size;

    @Setter
    private Double price;

    @Setter
    private String status;

    @Setter
    private String orderSide;

    @Setter
    private Date createDate;

    public Order(Long userId, String assetName, String orderSide, Double size, Double price, String status, Date createDate) {
        this.userId = userId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
        this.status = status;
        this.createDate = createDate;
    }
}
