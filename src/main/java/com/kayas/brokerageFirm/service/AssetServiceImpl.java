package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.exception.InsufficientAssetException;
import com.kayas.brokerageFirm.exception.AssetValidationException;
import com.kayas.brokerageFirm.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public List<Asset> getAssetsByUserId(Long userId) {
        List<Asset> assetList = assetRepository.getAssetsByUserId(userId);
        if (assetList == null || assetList.isEmpty())
            throw new InsufficientAssetException("Asset not found, for user id: " + userId);
        return assetList;
    }

    @Override
    public Asset getAssetByUserIdAndName(Long userId, String assetName) {
        return assetRepository.getAssetByUserIdAndAssetName(userId, assetName)
                .orElseThrow(() -> new InsufficientAssetException("Asset not found: " + assetName + "for user id: " + userId));
    }

    @Override
    public void updateAssetSize(Asset asset, Double usableSizeChange) {
        asset.setUsableSize(asset.getUsableSize() + usableSizeChange);
        assetRepository.save(asset);
    }

    @Override
    public void updateAssetSize(Asset asset, Double usableSizeChange, Double sizeChange) {
        asset.setSize(asset.getSize() + sizeChange);
        asset.setUsableSize(asset.getUsableSize() + usableSizeChange);
        assetRepository.save(asset);
    }

    @Override
    public void validateBuyOrder(Asset tryAsset, Double requestedOrderAmount) {
        if (tryAsset == null) {
            throw new AssetValidationException("TRY asset not found");
        }
        if (requestedOrderAmount == null || requestedOrderAmount <= 0) {
            throw new AssetValidationException("Invalid order amount");
        }
        if (tryAsset.getUsableSize() < requestedOrderAmount) {
            throw new InsufficientAssetException("Insufficient TRY balance for BUY operation.");
        }
    }


    @Override
    public void validateSellOrder(Asset requestedAsset, Double requestedSize) {
        if (requestedAsset.getUsableSize() < requestedSize) {
            throw new InsufficientAssetException("Insufficient assets available for SELL operation.");
        }
    }
}
