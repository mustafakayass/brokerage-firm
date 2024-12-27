package com.kayas.brokerageFirm.service;


import com.kayas.brokerageFirm.dto.request.DepositRequest;
import com.kayas.brokerageFirm.dto.request.WithdrawalRequest;
import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.entity.Asset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    
    @Mock
    private AssetService assetService;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void createDeposit_shouldSucceed() {
        // Arrange
        DepositRequest request = new DepositRequest();
        request.setUserId(1L);
        request.setAmount(BigDecimal.valueOf(1000.0));

        AdminValidationResponse validationResponse = new AdminValidationResponse();
        validationResponse.setId(1L);

        Asset tryAsset = new Asset(1L, "TRY", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1000.0));

        // Stub: AdminValidationResponse dön
        when(userService.validateUserAccess(request.getUserId())).thenReturn(validationResponse);

        // Stub: TRY varlığı dön
        when(assetService.getAssetByUserIdAndName(validationResponse.getId(), "TRY")).thenReturn(tryAsset);

        // Act
        String result = walletService.createDeposit(request);

        // Assert
        // assetService.updateAssetSize metodu doğru parametrelerle çağrılmış mı kontrol et
        verify(assetService).updateAssetSize(tryAsset, request.getAmount(), request.getAmount());

        // Sonucun başarılı olduğunu doğrula
        assertTrue(result.contains("Deposit success: 1000.0 TRY, has been deposited."));
    }



    @Test
    void createWithdrawal_shouldSucceed() {
        // Arrange
        WithdrawalRequest request = new WithdrawalRequest();
        request.setUserId(1L);
        request.setAmount(BigDecimal.valueOf(500.0));
        request.setIban("TR123456789012345678901234");

        AdminValidationResponse validationResponse = new AdminValidationResponse();
        validationResponse.setId(1L);

        Asset tryAsset = new Asset(1L, "TRY", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1000.0));

        // Stub: AdminValidationResponse dön
        when(userService.validateUserAccess(request.getUserId())).thenReturn(validationResponse);

        // Stub: TRY varlığı dön
        when(assetService.getAssetByUserIdAndName(validationResponse.getId(), "TRY")).thenReturn(tryAsset);

        // Act
        String result = walletService.createWithdrawal(request);

        // Assert
        // assetService.validateBuyOrder metodu doğru parametrelerle çağrılmış mı kontrol et
        verify(assetService).validateBuyOrder(tryAsset, request.getAmount());

        // assetService.updateAssetSize metodu doğru parametrelerle çağrılmış mı kontrol et
        verify(assetService).updateAssetSize(tryAsset, request.getAmount().negate(), request.getAmount().negate());

        // Sonucun başarılı olduğunu doğrula
        assertTrue(result.contains("Withdrawal success: 500.0 TRY, for IBAN: TR123456789012345678901234 has been created."));
    }

} 