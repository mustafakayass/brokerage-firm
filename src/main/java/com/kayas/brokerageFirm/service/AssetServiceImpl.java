package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.exception.AssetValidationException;
import com.kayas.brokerageFirm.exception.InsufficientAssetException;
import com.kayas.brokerageFirm.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

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
    public Asset getOrCreateAsset(Long userId, String assetName) {
        return assetRepository.getAssetByUserIdAndAssetName(userId, assetName)
                .orElseGet(() -> {
                    Asset newAsset = new Asset();
                    newAsset.setUserId(userId);
                    newAsset.setAssetName(assetName);
                    newAsset.setSize(BigDecimal.valueOf(0.0));
                    newAsset.setUsableSize(BigDecimal.valueOf(0.0));
                    return assetRepository.save(newAsset);
                });
    }

    @Override
    public void updateAssetSize(Asset asset, BigDecimal usableSizeChange) {
        asset.setUsableSize(asset.getUsableSize().add(usableSizeChange));
        assetRepository.save(asset);
    }

    @Override
    public void updateAssetSize(Asset asset, BigDecimal usableSizeChange, BigDecimal sizeChange) {
        asset.setSize(asset.getSize().add(sizeChange));
        asset.setUsableSize(asset.getUsableSize().add(usableSizeChange));
        assetRepository.save(asset);
    }

    @Override
    public void validateBuyOrder(Asset tryAsset, BigDecimal requestedOrderAmount) {
        if (tryAsset == null) {
            throw new AssetValidationException("TRY asset not found");
        }
        if (requestedOrderAmount == null || requestedOrderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AssetValidationException("Invalid order amount");
        }
        if (tryAsset.getUsableSize().compareTo(requestedOrderAmount) < 0) {
            throw new InsufficientAssetException("Insufficient TRY balance for BUY operation.");
        }
    }


    @Override
    public void validateSellOrder(Asset requestedAsset, BigDecimal requestedSize) {
        if (requestedAsset.getUsableSize().compareTo(requestedSize) < 0) {
            throw new InsufficientAssetException("Insufficient assets available for SELL operation.");
        }
    }

    @Override
    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

}
