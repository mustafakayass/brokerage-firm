package com.kayas.brokerageFirm.controller;

import com.kayas.brokerageFirm.api.WalletApi;
import com.kayas.brokerageFirm.dto.request.DepositRequest;
import com.kayas.brokerageFirm.dto.request.WithdrawalRequest;
import com.kayas.brokerageFirm.dto.common.BaseResponse;
import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController implements WalletApi {

    @Autowired
    private WalletService walletService;

    @Override
    @GetMapping("/assets")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam(value = "userId", required = false) Long userId) {
        List<Asset> filteredAssets = walletService.listAssets(userId);
        return ResponseEntity.ok(filteredAssets);
    }

    @Override
    @PostMapping("/deposits")
    public ResponseEntity<BaseResponse<String>> createDeposit(@Valid @RequestBody DepositRequest request) {
        String result = walletService.createDeposit(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Override
    @PostMapping("/withdrawals")
    public ResponseEntity<BaseResponse<String>> createWithdrawal(@RequestBody @Valid WithdrawalRequest request) {
        String result = walletService.createWithdrawal(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}


