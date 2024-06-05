package inventory_management.module.inventory.service;

import inventory_management.module.inventory.controller.request.InventoryRequest;
import inventory_management.module.inventory.controller.response.InventoryResponse;
import inventory_management.util.PageResponse;

public interface InventoryService {

    /**
     * service method for create inventory
     * @return product Id
     */
    Integer addInventory(final InventoryRequest request);

    /**
     * service method for update inventory
     * @param productId
     * @param request
     * @return productId of updated inventory
     */
    Integer updateInventory(final Integer productId, final InventoryRequest request);

    /**
     * service method for delete inventory
     * @param productId
     * @return productId of deleted inventory
     */
    Integer deleteInventory(final Integer productId);

    /**
     * service method for get inventory by id
     * @param productId
     * @return inventory response
     */
    InventoryResponse getInventoryById(final Integer productId);

    /**
     * service method for get inventories by product name get all inventories
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    PageResponse getAllInventories(final Integer pageNo, final Integer pageSize, final String productName);
}
