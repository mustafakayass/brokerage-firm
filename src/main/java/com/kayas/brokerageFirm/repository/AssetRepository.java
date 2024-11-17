package com.kayas.brokerageFirm.repository;

import com.kayas.brokerageFirm.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT o FROM Asset o WHERE (:userId IS NULL OR o.userId = :userId)")
    List<Asset> getAssetsByUserId(@Param("userId") Long userId);

    Optional<Asset> getAssetByUserIdAndAssetName(Long userId, String assetName);
}
