package inventory_management.module.inventory.repository;

import inventory_management.module.inventory.entity.InventoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMasterRepository extends JpaRepository<InventoryMaster,Integer> {

    Page<InventoryMaster> findByNameContaining(String productName, Pageable pageable);
}
