package inventory_management.module.inventory.controller.response;

import inventory_management.module.inventory.entity.InventoryMaster;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryResponse {

    private Integer id;

    private String name;

    private Integer categoryId;

    private String categoryName;

    private Integer brandId;

    private String brandName;

    private Integer stockInHand;

    private Double unitPrice;

    private Integer supplierId;

    private String supplierName;

    private String remarks;

    public InventoryResponse(InventoryMaster inventoryMaster){
        setId(inventoryMaster.getProductId());
        setName(inventoryMaster.getName());
        setCategoryId(inventoryMaster.getCategoryId().getId());
        setCategoryName(inventoryMaster.getCategoryId().getName());
        setBrandId(inventoryMaster.getBrandId().getId());
        setBrandName(inventoryMaster.getBrandId().getName());
        setStockInHand(inventoryMaster.getStockInHand());
        setUnitPrice(inventoryMaster.getUnitPrice());
        setSupplierId(inventoryMaster.getSupplierId().getId());
        setSupplierName(inventoryMaster.getSupplierId().getName());
        setRemarks(inventoryMaster.getRemarks());
    }
}
