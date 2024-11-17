package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.exception.AssetValidationException;
import com.kayas.brokerageFirm.exception.InsufficientAssetException;
import com.kayas.brokerageFirm.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;
    
    @InjectMocks
    private AssetServiceImpl assetService;

    @Test
    void getAssetsByUserId_whenAssetsExist_shouldReturnList() {
        // Arrange
        Long userId = 1L; // Kullanıcı ID'si
        List<Asset> assets = Arrays.asList(
                new Asset(1L, "BTC", 1.0, 1.0),
                new Asset(1L, "TRY", 100000.0, 100000.0)
        );
        // UserId'ye göre asset'leri döndüren stub
        when(assetRepository.getAssetsByUserId(userId)).thenReturn(assets);

        // Act
        List<Asset> result = assetService.getAssetsByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(assetRepository).getAssetsByUserId(userId); // doğru metot çağrıldığını doğrula
    }

    @Test
    void getAssetsByUserId_whenNoAssetsExist_shouldThrowException() {
        // Arrange
        Long userId = 1L;
        // Boş bir liste döner
        when(assetRepository.getAssetsByUserId(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(InsufficientAssetException.class, () -> {
            assetService.getAssetsByUserId(userId);
        });
        assertEquals("Asset not found, for user id: " + userId, exception.getMessage());
    }


    @Test
    void validateBuyOrder_whenInsufficientBalance_shouldThrowInsufficientAssetException() {
        // Arrange
        Asset tryAsset = new Asset(1L, "TRY", 1000.0, 1000.0); // Usable size 1000.0
        Double requestedAmount = 2000.0; // Requested amount is higher than usable size

        // Act & Assert
        // Bu durumda, usable size yetersiz olduğu için InsufficientAssetException fırlatılmalı
        assertThrows(InsufficientAssetException.class, () ->
                assetService.validateBuyOrder(tryAsset, requestedAmount)
        );
    }

    @Test
    void validateBuyOrder_whenInvalidAmount_shouldThrowAssetValidationException() {
        // Arrange
        Asset tryAsset = new Asset(1L, "TRY", 1000.0, 1000.0); // Usable size 1000.0
        Double requestedAmount = -100.0; // Invalid requested amount (negative)

        // Act & Assert
        // Bu durumda, geçersiz bir miktar olduğunda AssetValidationException fırlatılmalı
        assertThrows(AssetValidationException.class, () ->
                assetService.validateBuyOrder(tryAsset, requestedAmount)
        );
    }

    @Test
    void validateBuyOrder_whenNullAmount_shouldThrowAssetValidationException() {
        // Arrange
        Asset tryAsset = new Asset(1L, "TRY", 1000.0, 1000.0); // Usable size 1000.0
        Double requestedAmount = null; // Null amount

        // Act & Assert
        // Null bir miktar olduğunda AssetValidationException fırlatılmalı
        assertThrows(AssetValidationException.class, () ->
                assetService.validateBuyOrder(tryAsset, requestedAmount)
        );
    }

    @Test
    void validateBuyOrder_whenAssetIsNull_shouldThrowAssetValidationException() {
        // Arrange
        Asset tryAsset = null; // Asset is null
        Double requestedAmount = 1000.0; // Valid amount

        // Act & Assert
        // Asset null olduğunda AssetValidationException fırlatılmalı
        assertThrows(AssetValidationException.class, () ->
                assetService.validateBuyOrder(tryAsset, requestedAmount)
        );
    }

    @Test
    void validateBuyOrder_whenValidAmount_shouldNotThrowException() {
        // Arrange
        Asset tryAsset = new Asset(1L, "TRY", 2000.0, 2000.0); // Usable size is sufficient
        Double requestedAmount = 1000.0; // Valid requested amount

        // Act & Assert
        // Geçerli bir miktar olduğunda herhangi bir exception fırlatılmamalı
        assertDoesNotThrow(() -> assetService.validateBuyOrder(tryAsset, requestedAmount));
    }


    @Test
    void validateSellOrder_whenSufficientBalance_shouldNotThrowException() {
        // Arrange
        Asset btcAsset = new Asset(1L, "BTC", 2.0, 2.0);
        Double requestedSize = 1.0;
        
        // Act & Assert
        assertDoesNotThrow(() -> 
            assetService.validateSellOrder(btcAsset, requestedSize)
        );
    }

    @Test
    void updateAssetSize_shouldUpdateCorrectly() {
        // Arrange
        Asset asset = new Asset(1L, "BTC", 1.0, 1.0);
        Double sizeChange = 0.5;
        
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);
        
        // Act
        assetService.updateAssetSize(asset, sizeChange);
        
        // Assert
        assertEquals(1.5, asset.getUsableSize());
        verify(assetRepository).save(asset);
    }

    @Test
    void getAssetByCustomerIdAndName_whenAssetNotFound_shouldThrowException() {
        // Arrange
        when(assetRepository.getAssetByUserIdAndAssetName(any(), any()))
            .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(InsufficientAssetException.class, () -> 
            assetService.getAssetByUserIdAndName(1L, "BTC")
        );
    }

    @Test
    void updateAssetSize_withBothSizeAndUsableSize_shouldUpdateBoth() {
        // Arrange
        Asset asset = new Asset(1L, "BTC", 1.0, 1.0);
        Double sizeChange = 0.5;
        Double usableSizeChange = 0.3;
        
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);
        
        // Act
        assetService.updateAssetSize(asset, usableSizeChange,sizeChange);
        
        // Assert
        assertEquals(1.5, asset.getSize());
        assertEquals(1.3, asset.getUsableSize());
        verify(assetRepository).save(asset);
    }

    @Test
    void validateSellOrder_whenInsufficientBalance_shouldThrowException() {
        // Arrange
        Asset asset = new Asset(1L, "BTC", 1.0, 1.0);
        Double requestedSize = 2.0;
        
        // Act & Assert
        assertThrows(InsufficientAssetException.class, () -> 
            assetService.validateSellOrder(asset, requestedSize)
        );
    }
}