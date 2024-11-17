package com.kayas.brokerageFirm.api;

import com.kayas.brokerageFirm.dto.request.DepositRequest;
import com.kayas.brokerageFirm.dto.request.WithdrawalRequest;
import com.kayas.brokerageFirm.dto.common.BaseResponse;
import com.kayas.brokerageFirm.entity.Asset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface WalletApi {
    ResponseEntity<List<Asset>> listAssets(@RequestParam(value = "userId", required = false) Long userId);
    ResponseEntity<BaseResponse<String>> createDeposit(DepositRequest request);
    ResponseEntity<BaseResponse<String>> createWithdrawal(WithdrawalRequest withdrawalRequest);
}
