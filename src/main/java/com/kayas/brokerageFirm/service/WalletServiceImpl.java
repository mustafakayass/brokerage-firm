package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.dto.request.DepositRequest;
import com.kayas.brokerageFirm.dto.request.WithdrawalRequest;
import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;

    @Override
    public List<Asset> listAssets(Long userId) {
        User currentUser = userService.getCurrentUser();

        if (!userService.isAdmin(currentUser)) {
            userId = currentUser.getId();
        }
        
        return assetService.getAssetsByUserId(userId);
    }

    @Override
    public String createDeposit(DepositRequest request) {

        AdminValidationResponse response = userService.validateUserAccess(request.getUserId());
        Asset asset = assetService.getAssetByUserIdAndName(response.getId(), "TRY");
        assetService.updateAssetSize(asset, request.getAmount(), request.getAmount());
        
        return "Deposit success: " + request.getAmount() + " TRY, has been deposited.";
    }

    @Override
    public String createWithdrawal(WithdrawalRequest request) {
        
        AdminValidationResponse response = userService.validateUserAccess(request.getUserId());
        Asset asset = assetService.getAssetByUserIdAndName(response.getId(), "TRY");
        assetService.validateBuyOrder(asset, request.getAmount());
        assetService.updateAssetSize(asset, -request.getAmount(), -request.getAmount());
        
        return "Withdrawal success: " + request.getAmount() + " TRY, for IBAN: " + request.getIban() + " has been created.";
    }
}

