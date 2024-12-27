package com.kayas.brokerageFirm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "`ASSET`")
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String assetName;

    @Getter @Setter
    private BigDecimal size;

    @Getter @Setter
    private BigDecimal usableSize;

    public Asset(Long userId, String assetName, BigDecimal size, BigDecimal usableSize) {
        this.userId = userId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
    }
}
