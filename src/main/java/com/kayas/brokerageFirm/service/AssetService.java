package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.entity.Asset;

import java.math.BigDecimal;
import java.util.List;

public interface AssetService {

    List<Asset> getAssetsByUserId(Long userId);

    Asset getAssetByUserIdAndName(Long userId, String assetName);

    Asset getOrCreateAsset(Long userId, String assetName);

    void updateAssetSize(Asset asset, BigDecimal sizeChange);

    void updateAssetSize(Asset asset, BigDecimal sizeChange, BigDecimal usableSizeChange);

    void validateBuyOrder(Asset tryAsset, BigDecimal requestedOrderAmount);

    void validateSellOrder(Asset requestedAsset, BigDecimal requestedSize);

    Asset saveAsset(Asset asset);
}
