package inventory_management.module.inventory.service.impl;

import inventory_management.module.inventory.controller.request.InventoryDetailRequest;
import inventory_management.module.inventory.controller.response.InventoryDetailResponse;
import inventory_management.module.inventory.entity.InventoryDetail;
import inventory_management.module.inventory.entity.InventoryMaster;
import inventory_management.module.inventory.enums.TransactionStatus;
import inventory_management.module.inventory.enums.TransactionType;
import inventory_management.module.inventory.repository.InventoryDetailRepository;
import inventory_management.module.inventory.repository.InventoryMasterRepository;
import inventory_management.module.inventory.service.InventoryDetailService;
import inventory_management.util.PageResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryDetailServiceImpl implements InventoryDetailService {

    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    private InventoryMasterRepository inventoryMasterRepository;

    private ModelMapper modelMapper;

    public InventoryDetailServiceImpl() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }


    /**
     * service method for create inventory detail
     * @return transaction Id
     */
    @Override
    public Integer addInventoryDetail(InventoryDetailRequest request) {
        if(!TransactionStatus.PENDING.equals(request.getTransactionStatus())){
            throw new IllegalArgumentException("Invalid transaction status");
        }
        Optional<InventoryMaster> inventoryMaster = inventoryMasterRepository.findById(request.getProductId());
        if(!inventoryMaster.isPresent()){
            throw new IllegalArgumentException("Not found any product for given productId : "+request.getProductId());
        }
        InventoryDetail inventoryDetail = modelMapper.map(request, InventoryDetail.class);
        inventoryDetail.setProductId(inventoryMaster.get());
        inventoryDetail.setTransactionDate(LocalDate.now());
        inventoryDetail.setAmount(BigDecimal.valueOf(inventoryMaster.get().getUnitPrice()).multiply(BigDecimal.valueOf(request.getQuantity())));
        inventoryDetail.setCreatedAt(LocalDateTime.now());

        return inventoryDetailRepository.save(inventoryDetail).getTransactionId();
    }

    /**
     * service method for delete inventory detail
     * @param transactionId
     * @return transactionId of deleted inventory detail
     */
    @Override
    public Integer deleteInventoryDetail(Integer transactionId) {
        Optional<InventoryDetail> inventoryDetail = inventoryDetailRepository.findById(transactionId);
        if(!inventoryDetail.isPresent()){
            throw new IllegalArgumentException("Not found any transaction for given transactionId : "+transactionId);
        }
        inventoryDetailRepository.delete(inventoryDetail.get());
        return transactionId;
    }

    /**
     * service method for get inventory detail by id
     * @param transactionId
     * @return inventory detail response
     */
    @Override
    public InventoryDetailResponse getInventoryDetailById(Integer transactionId) {
        Optional<InventoryDetail> inventoryDetail = inventoryDetailRepository.findById(transactionId);
        if(!inventoryDetail.isPresent()){
            throw new IllegalArgumentException("Not found any transaction for given transactionId : "+transactionId);
        }
        InventoryDetailResponse inventoryDetailResponse = new InventoryDetailResponse(inventoryDetail.get());
        return inventoryDetailResponse;
    }

    /**
     * service method for get all inventory details
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    @Override
    public PageResponse getAllInventoryDetails(Integer pageNo, Integer pageSize) {
        Page<InventoryDetail> inventoryDetailPage = inventoryDetailRepository.findAll(PageRequest.of(pageNo-1,pageSize));
        List<InventoryDetailResponse> inventoryDetailResponseList = inventoryDetailPage.getContent().stream()
                .map(inventoryDetail -> new InventoryDetailResponse(inventoryDetail))
                .collect(Collectors.toList());
        return new PageResponse(inventoryDetailResponseList,inventoryDetailPage);
    }

    /**
     * service method for update inventory detail
     * @param transactionId
     * @param request
     * @return transactionId of updated inventory detail
     */
    @Override
    @Transactional
    public Integer updateInventoryDetail(Integer transactionId, InventoryDetailRequest request) {
        Optional<InventoryDetail> inventoryDetail = inventoryDetailRepository.findById(transactionId);
        if(!inventoryDetail.isPresent()){
            throw new IllegalArgumentException("Not found any transaction for given transactionId : "+transactionId);
        }
        InventoryMaster inventoryMaster = inventoryDetail.get().getProductId();
        if(!inventoryMaster.getProductId().equals(request.getProductId())){
            throw new IllegalArgumentException("invalid productId");
        }

        if(TransactionStatus.PENDING.equals(inventoryDetail.get().getTransactionStatus()) && TransactionStatus.COMPLETED.equals(request.getTransactionStatus())){
            if(TransactionType.PURCHASE.equals(inventoryDetail.get().getTransactionType()) || TransactionType.RETURN.equals(inventoryDetail.get().getTransactionType())){
                inventoryMaster.setStockInHand(inventoryMaster.getStockInHand() + request.getQuantity());
            }else {
                inventoryMaster.setStockInHand(inventoryMaster.getStockInHand() - request.getQuantity());
            }
        } else if (TransactionStatus.COMPLETED.equals(inventoryDetail.get().getTransactionStatus()) && TransactionStatus.CANCELLED.equals(request.getTransactionStatus())) {
            if(TransactionType.PURCHASE.equals(inventoryDetail.get().getTransactionType()) || TransactionType.RETURN.equals(inventoryDetail.get().getTransactionType())){
                inventoryMaster.setStockInHand(inventoryMaster.getStockInHand() - request.getQuantity());
            }else {
                inventoryMaster.setStockInHand(inventoryMaster.getStockInHand() + request.getQuantity());
            }
        }

        inventoryMasterRepository.save(inventoryMaster);

        modelMapper.map(request, inventoryDetail);
        inventoryDetail.get().setProductId(inventoryMaster);
        inventoryDetail.get().setAmount(BigDecimal.valueOf(inventoryMaster.getUnitPrice()).multiply(BigDecimal.valueOf(request.getQuantity())));
        inventoryDetail.get().setTransactionStatus(TransactionStatus.COMPLETED);
        inventoryDetail.get().setUpdatedAt(LocalDateTime.now());

        return inventoryDetailRepository.save(inventoryDetail.get()).getTransactionId();

    }

}
