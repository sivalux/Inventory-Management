package inventory_management.module.inventory.repository;

import inventory_management.module.inventory.entity.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail,Integer> {
}
