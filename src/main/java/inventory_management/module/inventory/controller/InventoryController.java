package inventory_management.module.inventory.controller;

import inventory_management.module.inventory.controller.request.InventoryRequest;
import inventory_management.module.inventory.controller.response.InventoryResponse;
import inventory_management.module.inventory.service.InventoryService;
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
@RequestMapping(path = "/api/v1/inventory")
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @Operation(summary = "Create inventory")
    @ApiResponse(responseCode = "201", description = "Inventory created successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PostMapping
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest request) {
        try {
            Integer response = inventoryService.addInventory(request);
            LOGGER.info("Inventory created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Update inventory")
    @ApiResponse(responseCode = "200", description = "Inventory updated successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PutMapping(path = "/{productId}")
    public ResponseEntity<?> updateInventory(@PathVariable Integer productId,@Valid @RequestBody InventoryRequest request) {
        try {
            Integer response = inventoryService.updateInventory(productId, request);
            LOGGER.info("Inventory updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Delete inventory")
    @ApiResponse(responseCode = "200", description = "Inventory deleted successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<?> deleteInventory(@PathVariable Integer productId) {
        try {
            Integer response = inventoryService.deleteInventory(productId);
            LOGGER.info("Inventory deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Get inventory by id")
    @ApiResponse(responseCode = "200", description = "Inventory retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InventoryResponse.class))})
    @GetMapping(path = "/{productId}")
    public ResponseEntity<?> getInventoryById(@PathVariable Integer productId) {
        try {
            InventoryResponse response = inventoryService.getInventoryById(productId);
            LOGGER.info("Inventory retrieved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }

    @Operation(summary = "Get all inventories")
    @ApiResponse(responseCode = "200", description = "Inventories retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class))})
    @GetMapping
    public ResponseEntity<?> getAllInventories( @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        try {
            PageResponse response = inventoryService.getAllInventories(pageNo,pageSize);
            LOGGER.info("Inventories retrieved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseModel(new Date(), e.getMessage()));
        }
    }
}
