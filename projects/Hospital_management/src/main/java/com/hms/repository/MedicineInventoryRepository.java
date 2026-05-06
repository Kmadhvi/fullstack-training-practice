package com.hms.repository;

import com.hms.entity.MedicineInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long> {
    Optional<MedicineInventory> findByMedicineNameIgnoreCaseAndBatchNumber(String medicineName, String batchNumber);

    @Query("select m from MedicineInventory m where m.quantityAvailable <= m.reorderLevel")
    List<MedicineInventory> findLowStockItems();
}
