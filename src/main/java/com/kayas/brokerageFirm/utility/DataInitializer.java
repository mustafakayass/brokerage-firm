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
            assetRepository.save(new Asset(1L, "BTC", 1.00, 1.00));
            assetRepository.save(new Asset(1L, "TRY", 100000.00, 10000.00));
            assetRepository.save(new Asset(1L, "USD", 10000.00, 10000.00));
            assetRepository.save(new Asset(1L, "XAU", 100.00, 100.00));
            assetRepository.save(new Asset(2L, "TRY", 100000.00, 100000.00));
            assetRepository.save(new Asset(2L, "USD", 1000.00, 1000.00));
        }
    }

    private void initializeOrders() {
        if (orderRepository.count() == 0) {
            orderRepository.save(new Order(1L, "BTC", "B", 2.00, 78500.00, "M",new Date()));
        }
    }
}

