package inventory_management.module.inventory.controller.response;

import inventory_management.module.inventory.entity.InventoryDetail;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class InventoryDetailResponse {

    private Integer transactionId;

    private Integer productId;

    private String productName;

    private String transactionType;

    private Integer quantity;

    private LocalDate transactionDate;

    private String description;

    private BigDecimal amount;

    private String transactionStatus;

    private String remarks;

    public InventoryDetailResponse(InventoryDetail inventoryDetail){
        setTransactionId(inventoryDetail.getTransactionId());
        setProductId(inventoryDetail.getProductId().getProductId());
        setProductName(inventoryDetail.getProductId().getName());
        setTransactionType(inventoryDetail.getTransactionType().name());
        setQuantity(inventoryDetail.getQuantity());
        setTransactionDate(inventoryDetail.getTransactionDate());
        setDescription(inventoryDetail.getDescription());
        setAmount(inventoryDetail.getAmount());
        setTransactionStatus(inventoryDetail.getTransactionStatus().name());
        setRemarks(inventoryDetail.getRemarks());
    }
}
