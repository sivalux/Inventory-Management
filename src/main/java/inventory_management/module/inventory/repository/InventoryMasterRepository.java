package inventory_management.module.inventory.repository;

import inventory_management.module.inventory.entity.InventoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMasterRepository extends JpaRepository<InventoryMaster,Integer> {
}
