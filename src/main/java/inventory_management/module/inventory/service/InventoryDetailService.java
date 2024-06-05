package inventory_management.module.inventory.service;

import inventory_management.module.inventory.controller.request.InventoryDetailRequest;
import inventory_management.module.inventory.controller.response.InventoryDetailResponse;
import inventory_management.util.PageResponse;

public interface InventoryDetailService {

    /**
     * service method for create inventory detail
     * @return transaction Id
     */
    Integer addInventoryDetail(final InventoryDetailRequest request);

    /**
     * service method for delete inventory detail
     * @param transactionId
     * @return transactionId of deleted inventory detail
     */
    Integer deleteInventoryDetail(final Integer transactionId);

    /**
     * service method for get inventory detail by id
     * @param transactionId
     * @return inventory detail response
     */
    InventoryDetailResponse getInventoryDetailById(final Integer transactionId);

    /**
     * service method for get all inventory details
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    PageResponse getAllInventoryDetails(final Integer pageNo, final Integer pageSize);

    /**
     * service method for update inventory detail
     * @param transactionId
     * @param request
     * @return transactionId of updated inventory detail
     */
    Integer updateInventoryDetail(final Integer transactionId, final InventoryDetailRequest request);

}
