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

    @Query("SELECT a FROM Asset a WHERE a.userId = :userId")
    List<Asset> getAssetsByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Asset a WHERE a.userId = :userId AND a.assetName = :assetName")
    Optional<Asset> getAssetByUserIdAndAssetName(@Param("userId") Long userId, @Param("assetName") String assetName);
}
