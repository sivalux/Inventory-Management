package inventory_management.module.inventory.service.impl;

import inventory_management.module.inventory.controller.request.InventoryRequest;
import inventory_management.module.inventory.controller.response.InventoryResponse;
import inventory_management.module.inventory.entity.Brand;
import inventory_management.module.inventory.entity.Category;
import inventory_management.module.inventory.entity.InventoryMaster;
import inventory_management.module.inventory.entity.Supplier;
import inventory_management.module.inventory.repository.*;
import inventory_management.module.inventory.service.InventoryService;
import inventory_management.util.PageResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMasterRepository inventoryMasterRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;

    public InventoryServiceImpl() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }


    /**
     * service method for create inventory
     * @return product Id
     */
    @Override
    public Integer addInventory(InventoryRequest request) {
        InventoryMaster inventoryMaster = modelMapper.map(request,InventoryMaster.class);
        inventoryMaster = validateInventoryFields(request, inventoryMaster);
        inventoryMaster.setCreatedAt(LocalDateTime.now());
        return inventoryMasterRepository.save(inventoryMaster).getProductId();
    }

    /**
     * service method for update inventory
     * @param productId
     * @param request
     * @return productId of updated inventory
     */
    @Override
    public Integer updateInventory(Integer productId, InventoryRequest request) {
        Optional<InventoryMaster> inventoryMaster = inventoryMasterRepository.findById(productId);
        if(inventoryMaster.isEmpty()){
            throw new IllegalArgumentException("Not found any product for given productId : "+productId);
        }
        modelMapper.map(request, inventoryMaster.get());
        validateInventoryFields(request,inventoryMaster.get());
        inventoryMaster.get().setUpdatedAt(LocalDateTime.now());
        return inventoryMasterRepository.save(inventoryMaster.get()).getProductId();
    }

    private InventoryMaster validateInventoryFields(InventoryRequest request, InventoryMaster inventoryMaster){
        Optional<Brand> brand = brandRepository.findById(request.getBrandId());
        if(brand.isEmpty()){
            throw new IllegalArgumentException("Not found any brand for given brandId : "+request.getBrandId());
        }
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if(category.isEmpty()){
            throw new IllegalArgumentException("Not found any category for given categoryId : "+request.getCategoryId());
        }
        Optional<Supplier> supplier = supplierRepository.findById(request.getSupplierId());
        if(supplier.isEmpty()){
            throw new IllegalArgumentException("Not found any supplier for given supplierId : "+request.getSupplierId());
        }
        inventoryMaster.setBrandId(brand.get());
        inventoryMaster.setCategoryId(category.get());
        inventoryMaster.setSupplierId(supplier.get());

        return inventoryMaster;
    }

    /**
     * service method for delete inventory
     * @param productId
     * @return productId of deleted inventory
     */
    @Override
    public Integer deleteInventory(Integer productId) {
        Optional<InventoryMaster> inventoryMaster = inventoryMasterRepository.findById(productId);
        if(!inventoryMaster.isPresent()){
            throw new IllegalArgumentException("Not found any product for given productId : "+productId);
        }
        inventoryMasterRepository.delete(inventoryMaster.get());
        return productId;
    }

    /**
     * service method for get inventory by id
     * @param productId
     * @return inventory response
     */
    @Override
    public InventoryResponse getInventoryById(Integer productId) {
        Optional<InventoryMaster> inventoryMaster = inventoryMasterRepository.findById(productId);
        if(!inventoryMaster.isPresent()){
            throw new IllegalArgumentException("Not found any product for given productId : "+productId);
        }
        InventoryResponse inventoryResponse = new InventoryResponse(inventoryMaster.get());
        return inventoryResponse;
    }

    /**
     * service method for get all inventories
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    @Override
    public PageResponse getAllInventories(Integer pageNo, Integer pageSize, String productName) {
        Page<InventoryMaster> inventoryResponsePage;
        if(productName != null){
            inventoryResponsePage = inventoryMasterRepository.findByNameContaining(productName,PageRequest.of(pageNo-1,pageSize));
        }else {
            inventoryResponsePage = inventoryMasterRepository.findAll(PageRequest.of(pageNo-1,pageSize));
        }
        List<InventoryResponse> inventoryResponseList = inventoryResponsePage.getContent().stream()
                .map(inventoryMaster -> new InventoryResponse(inventoryMaster))
                .collect(Collectors.toList());
        return new PageResponse(inventoryResponseList,inventoryResponsePage);
    }
}
