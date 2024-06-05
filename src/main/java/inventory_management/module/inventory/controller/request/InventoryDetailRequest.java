package inventory_management.module.inventory.controller.request;

import inventory_management.module.inventory.enums.TransactionStatus;
import inventory_management.module.inventory.enums.TransactionType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class InventoryDetailRequest {

    @NotEmpty(message = "Product Id cannot be empty.")
    private Integer productId;

    @NotEmpty(message = "Transaction type cannot be empty.")
    private TransactionType transactionType;

    @NotEmpty(message = "Quantity cannot be empty.")
    private Integer quantity;

    // will be null for update api
    private LocalDate transactionDate;

    private String description;

    @NotEmpty(message = "Transaction status cannot be empty.")
    private TransactionStatus transactionStatus;

    private String remarks;
}
