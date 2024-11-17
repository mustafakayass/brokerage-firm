package com.kayas.brokerageFirm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double size;

    @Getter @Setter
    private Double usableSize;

    public Asset(Long userId, String assetName, Double size, Double usableSize) {
        this.userId = userId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
    }
}
