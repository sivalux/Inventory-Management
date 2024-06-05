package inventory_management.module.inventory.service.impl;

import inventory_management.module.inventory.controller.request.InventoryRequest;
import inventory_management.module.inventory.entity.Brand;
import inventory_management.module.inventory.entity.Category;
import inventory_management.module.inventory.entity.InventoryMaster;
import inventory_management.module.inventory.entity.Supplier;
import inventory_management.module.inventory.repository.BrandRepository;
import inventory_management.module.inventory.repository.CategoryRepository;
import inventory_management.module.inventory.repository.InventoryMasterRepository;
import inventory_management.module.inventory.repository.SupplierRepository;
import inventory_management.util.PageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {
    @Mock
    private InventoryMasterRepository inventoryMasterRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;
    @Test
    void addInventory() {
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);
        request.setName("Product Name");
        request.setStockInHand(10);
        request.setUnitPrice(100.0);
        request.setRemarks("Remarks");

        InventoryMaster inventoryMaster = new InventoryMaster();
        inventoryMaster.setProductId(1);
        inventoryMaster.setCreatedAt(LocalDateTime.now());

        Brand brand = new Brand();
        Category category = new Category();
        Supplier supplier = new Supplier();

        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.of(category));
        given(supplierRepository.findById(request.getSupplierId())).willReturn(Optional.of(supplier));
        given(inventoryMasterRepository.save(any(InventoryMaster.class))).willReturn(inventoryMaster);

        Integer productId = inventoryService.addInventory(request);
        assertThat(productId).isEqualTo(1);
    }

    @Test
    void addInventoryBrandNotFoundError() {
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);
        request.setName("Product Name");
        request.setStockInHand(10);
        request.setUnitPrice(100.0);
        request.setRemarks("Remarks");

        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.addInventory(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any brand for given brandId : " + request.getBrandId());
    }

    @Test
    void addInventoryCategoryNotFoundError() {
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);
        request.setName("Product Name");
        request.setStockInHand(10);
        request.setUnitPrice(100.0);
        request.setRemarks("Remarks");

        Brand brand = new Brand();

        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.addInventory(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any category for given categoryId : " + request.getCategoryId());
    }

    @Test
    void addInventorySupplierNotFoundError() {
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);
        request.setName("Product Name");
        request.setStockInHand(10);
        request.setUnitPrice(100.0);
        request.setRemarks("Remarks");

        Brand brand = new Brand();
        Category category = new Category();

        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.of(category));
        given(supplierRepository.findById(request.getSupplierId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.addInventory(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any supplier for given supplierId : " + request.getSupplierId());
    }

    @Test
    void updateInventory() {
        Integer productId = 1;
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);
        request.setName("Updated Product Name");
        request.setStockInHand(20);
        request.setUnitPrice(200.0);
        request.setRemarks("Updated Remarks");

        InventoryMaster existingInventory = new InventoryMaster();
        existingInventory.setProductId(productId);

        Brand brand = new Brand();
        Category category = new Category();
        Supplier supplier = new Supplier();

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.of(existingInventory));
        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.of(category));
        given(supplierRepository.findById(request.getSupplierId())).willReturn(Optional.of(supplier));
        given(inventoryMasterRepository.save(any(InventoryMaster.class))).willReturn(existingInventory);

        Integer updatedProductId = inventoryService.updateInventory(productId, request);

        assertThat(updatedProductId).isEqualTo(productId);
        assertThat(existingInventory.getName()).isEqualTo(request.getName());
        assertThat(existingInventory.getStockInHand()).isEqualTo(request.getStockInHand());
        assertThat(existingInventory.getUnitPrice()).isEqualTo(request.getUnitPrice());
        assertThat(existingInventory.getRemarks()).isEqualTo(request.getRemarks());
        assertThat(existingInventory.getBrandId()).isEqualTo(brand);
        assertThat(existingInventory.getCategoryId()).isEqualTo(category);
        assertThat(existingInventory.getSupplierId()).isEqualTo(supplier);
        assertThat(existingInventory.getUpdatedAt()).isNotNull();
    }

    @Test
    void updateInventoryProductNotFoundError() {
        Integer productId = 1;
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(productId, request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any product for given productId : " + productId);
    }

    @Test
    void updateInventoryBrandNotFoundError() {
        Integer productId = 1;
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);

        InventoryMaster existingInventory = new InventoryMaster();
        existingInventory.setProductId(productId);

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.of(existingInventory));
        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(productId, request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any brand for given brandId : " + request.getBrandId());
    }

    @Test
    void updateInventoryCategoryNotFoundError() {
        Integer productId = 1;
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);

        InventoryMaster existingInventory = new InventoryMaster();
        existingInventory.setProductId(productId);

        Brand brand = new Brand();

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.of(existingInventory));
        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(productId, request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any category for given categoryId : " + request.getCategoryId());
    }

    @Test
    void updateInventorySupplierNotFoundError() {
        Integer productId = 1;
        InventoryRequest request = new InventoryRequest();
        request.setBrandId(1);
        request.setCategoryId(1);
        request.setSupplierId(1);

        InventoryMaster existingInventory = new InventoryMaster();
        existingInventory.setProductId(productId);

        Brand brand = new Brand();
        Category category = new Category();

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.of(existingInventory));
        given(brandRepository.findById(request.getBrandId())).willReturn(Optional.of(brand));
        given(categoryRepository.findById(request.getCategoryId())).willReturn(Optional.of(category));
        given(supplierRepository.findById(request.getSupplierId())).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(productId, request);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any supplier for given supplierId : " + request.getSupplierId());
    }

    @Test
    void deleteInventory() {
        Integer productId = 1;
        InventoryMaster existingInventory = new InventoryMaster();
        existingInventory.setProductId(productId);

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.of(existingInventory));

        Integer deletedProductId = inventoryService.deleteInventory(productId);

        assertThat(deletedProductId).isEqualTo(productId);
        verify(inventoryMasterRepository, times(1)).delete(existingInventory);
    }
    @Test
    public void deleteInventoryNotFoundError() {
        Integer productId = 1;

        given(inventoryMasterRepository.findById(productId)).willReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.deleteInventory(productId);
        });

        assertThat(exception.getMessage()).isEqualTo("Not found any product for given productId : " + productId);
        verify(inventoryMasterRepository, never()).delete(any(InventoryMaster.class));
    }

}