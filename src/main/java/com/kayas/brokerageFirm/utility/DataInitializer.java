package com.kayas.brokerageFirm.utility;

import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.repository.OrderRepository;
import com.kayas.brokerageFirm.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AssetRepository assetRepository;
    private final OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataInitializer(AssetRepository assetRepository, OrderRepository orderRepository) {
        this.assetRepository = assetRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializeAssets();
        initializeOrders();
    }

    private void initializeAssets() {
        if (assetRepository.count() == 0) {
            assetRepository.save(new Asset(1L, "BTC", BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0)));
            assetRepository.save(new Asset(1L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0)));
            assetRepository.save(new Asset(1L, "USD", BigDecimal.valueOf(10000.0), BigDecimal.valueOf(10000.0)));
            assetRepository.save(new Asset(1L, "XAU", BigDecimal.valueOf(100.0), BigDecimal.valueOf(100.0)));
            assetRepository.save(new Asset(2L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0)));
            assetRepository.save(new Asset(2L, "USD", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1000.0)));
        }
    }

    private void initializeOrders() {
        if (orderRepository.count() == 0) {
            orderRepository.save(new Order(1L, "BTC", "B", BigDecimal.valueOf(2.0), BigDecimal.valueOf(78500.0), "M",new Date()));
        }
    }
}

