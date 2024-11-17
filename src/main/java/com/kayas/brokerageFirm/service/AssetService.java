package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.entity.Asset;

import java.util.List;

public interface AssetService {

    List<Asset> getAssetsByUserId(Long userId);

    Asset getAssetByUserIdAndName(Long userId, String assetName);

    void updateAssetSize(Asset asset, Double sizeChange);

    void updateAssetSize(Asset asset, Double sizeChange, Double usableSizeChange);

    void validateBuyOrder(Asset tryAsset, Double requestedOrderAmount);

    void validateSellOrder(Asset requestedAsset, Double requestedSize);

}

