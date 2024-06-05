package inventory_management.module.inventory.controller;

import inventory_management.module.inventory.controller.request.InventoryDetailRequest;
import inventory_management.module.inventory.controller.response.InventoryDetailResponse;
import inventory_management.module.inventory.service.InventoryDetailService;
import inventory_management.util.ErrorResponseModel;
import inventory_management.util.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/inventory-detail")
public class InventoryDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryDetailController.class);

    @Autowired
    private InventoryDetailService inventoryDetailService;

    @Operation(summary = "Create inventory detail")
    @ApiResponse(responseCode = "201", description = "Inventory detail created successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PostMapping
    public ResponseEntity<?> createInventoryDetail(@Valid @RequestBody InventoryDetailRequest request) {
        try {
            Integer response = inventoryDetailService.addInventoryDetail(request);
            LOGGER.info("Inventory detail created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Delete inventory detail")
    @ApiResponse(responseCode = "200", description = "Inventory detail deleted successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity<?> deleteInventoryDetail(@PathVariable Integer transactionId) {
        try {
            Integer response = inventoryDetailService.deleteInventoryDetail(transactionId);
            LOGGER.info("Inventory detail deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Get inventory detail by id")
    @ApiResponse(responseCode = "200", description = "Inventory detail retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InventoryDetailResponse.class))})
    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<?> getInventoryDetailById(@PathVariable Integer transactionId) {
        try {
            InventoryDetailResponse response = inventoryDetailService.getInventoryDetailById(transactionId);
            LOGGER.info("Inventory detail retrieved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Get all inventory details")
    @ApiResponse(responseCode = "200", description = "Inventory details retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class))})
    @GetMapping
    public ResponseEntity<?> getAllInventoryDetails( @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        try {
            PageResponse response = inventoryDetailService.getAllInventoryDetails(pageNo,pageSize);
            LOGGER.info("Inventory details retrieved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Update inventory detail")
    @ApiResponse(responseCode = "200", description = "Inventory detail updated successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PutMapping(path = "/{transactionId}")
    public ResponseEntity<?> updateInventoryDetail(@PathVariable Integer transactionId,@Valid @RequestBody InventoryDetailRequest request) {
        try {
            Integer response = inventoryDetailService.updateInventoryDetail(transactionId, request);
            LOGGER.info("Inventory detail updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }
}
