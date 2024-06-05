package inventory_management.module.inventory.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InventoryRequest {

    @NotEmpty(message = "Product name cannot be empty.")
    private String name;

    @NotEmpty(message = "Category Id cannot be empty.")
    private Integer categoryId;

    @NotEmpty(message = "Brand Id cannot be empty.")
    private Integer brandId;

    @NotEmpty(message = "Stock in Hand cannot be empty.")
    private Integer stockInHand;

    @NotEmpty(message = "Unit price cannot be empty.")
    private Double unitPrice;

    @NotEmpty(message = "Supplier Id cannot be empty.")
    private Integer supplierId;

    private String remarks;
}
