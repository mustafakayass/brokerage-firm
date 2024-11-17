package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.request.DepositRequest;
import com.kayas.brokerageFirm.dto.request.WithdrawalRequest;
import com.kayas.brokerageFirm.entity.Asset;

import java.util.List;

public interface WalletService {
    List<Asset> listAssets(Long userId);
    String createDeposit(DepositRequest request);
    String createWithdrawal(WithdrawalRequest withdrawalRequest);
}

