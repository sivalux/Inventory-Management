package inventory_management.module.inventory.service.impl;

import inventory_management.module.inventory.controller.request.InventoryDetailRequest;
import inventory_management.module.inventory.entity.InventoryDetail;
import inventory_management.module.inventory.entity.InventoryMaster;
import inventory_management.module.inventory.enums.TransactionStatus;
import inventory_management.module.inventory.enums.TransactionType;
import inventory_management.module.inventory.repository.InventoryDetailRepository;
import inventory_management.module.inventory.repository.InventoryMasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryDetailServiceImplTest {

    @Mock
    private InventoryDetailRepository inventoryDetailRepository;

    @Mock
    private InventoryMasterRepository inventoryMasterRepository;

    @InjectMocks
    private InventoryDetailServiceImpl inventoryDetailService;

    @Mock
    private ModelMapper modelMapper;

    public static InventoryDetail createMockInventoryDetail() {
        InventoryDetail inventoryDetail = new InventoryDetail();
        inventoryDetail.setTransactionId(1); // Assuming transactionId assigned by the database
        inventoryDetail.setProductId(createMockInventoryMaster()); // Create a mock InventoryMaster object
        inventoryDetail.setTransactionType(TransactionType.PURCHASE); // Assuming transaction type is purchase
        inventoryDetail.setQuantity(10); // Assuming quantity
        inventoryDetail.setTransactionDate(LocalDate.now()); // Current date
        inventoryDetail.setDescription("Description"); // Assuming description
        inventoryDetail.setAmount(BigDecimal.valueOf(500)); // Assuming amount
        inventoryDetail.setTransactionStatus(TransactionStatus.PENDING); // Assuming transaction status is pending
        inventoryDetail.setRemarks("Remarks"); // Assuming remarks
        inventoryDetail.setCreatedAt(LocalDateTime.now()); // Current date
        inventoryDetail.setUpdatedAt(null); // Assuming updated at is null
        return inventoryDetail;
    }

    private static InventoryMaster createMockInventoryMaster() {
        InventoryMaster inventoryMaster = new InventoryMaster();
        inventoryMaster.setProductId(1); // Assuming productId assigned by the database
        inventoryMaster.setName("Product"); // Assuming product name
        // Assuming category, brand, and supplier are also mocked similarly with their respective data
        inventoryMaster.setStockInHand(100); // Assuming stock in hand
        inventoryMaster.setUnitPrice(50.0); // Assuming unit price
        inventoryMaster.setRemarks("Remarks"); // Assuming remarks
        inventoryMaster.setCreatedAt(LocalDateTime.now()); // Current date
        inventoryMaster.setUpdatedAt(null); // Assuming updated at is null
        return inventoryMaster;
    }
//    @Test
//    void addInventoryDetail() {
//        // Create a mock InventoryDetailRequest
//        InventoryDetailRequest request = new InventoryDetailRequest();
//        request.setProductId(1); // Assuming productId exists in the database
//        request.setTransactionStatus(TransactionStatus.PENDING); // Assuming this is a valid transaction status
//        request.setQuantity(10); // Assuming a valid quantity
//
//        // Create a mock InventoryMaster
//        InventoryMaster inventoryMaster = createMockInventoryMaster();
//        when(inventoryMasterRepository.findById(1)).thenReturn(Optional.of(inventoryMaster));
//        when(Optional.of(inventoryMaster).get()).thenReturn(inventoryMaster);
//
//        // Call the service method
//        Integer newTransactionId = inventoryDetailService.addInventoryDetail(request);
//
//        // Assertions
//        assertThat(newTransactionId).isNotNull();
//    }

    @Test
    public void testAddInventoryDetailInvalidStatus() {
        // Mock request with invalid status
        InventoryDetailRequest request = new InventoryDetailRequest();
        request.setTransactionStatus(TransactionStatus.COMPLETED); // Assuming invalid status

        // Call the service method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryDetailService.addInventoryDetail(request);
        });

        // Assertion
        assertThat(exception.getMessage()).isEqualTo("Invalid transaction status");
    }

    @Test
    public void testAddInventoryDetailInvalidProduct() {
        // Mock request with invalid product
        InventoryDetailRequest request = new InventoryDetailRequest();
        request.setProductId(100); // Assuming productId does not exist in the database
        request.setTransactionStatus(TransactionStatus.PENDING); // Assuming this is a valid transaction status
        request.setQuantity(10); // Assuming a valid quantity

        // Mock InventoryMaster repository to return empty Optional
        when(inventoryMasterRepository.findById(100)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryDetailService.addInventoryDetail(request);
        });

        // Assertion
        assertThat(exception.getMessage()).isEqualTo("Not found any product for given productId : 100");
    }

    @Test
    void deleteInventoryDetail() {
    }

    @Test
    void getInventoryDetailById() {
    }

    @Test
    void getAllInventoryDetails() {
    }

    @Test
    void updateInventoryDetail() {
        InventoryDetailRequest request = new InventoryDetailRequest();
        request.setProductId(1); // Assuming productId exists in the database
        request.setTransactionStatus(TransactionStatus.COMPLETED); // Assuming this is a valid transaction status
        request.setQuantity(10); // Assuming a valid quantity
        request.setTransactionType(TransactionType.PURCHASE);
        InventoryDetail existingInventoryDetail = createMockInventoryDetail();
        existingInventoryDetail.setTransactionType(TransactionType.PURCHASE);

        InventoryMaster inventoryMaster = createMockInventoryMaster();

        when(inventoryDetailRepository.findById(1)).thenReturn(Optional.of(existingInventoryDetail));
        Mockito.lenient().when(inventoryMasterRepository.findById(1)).thenReturn(Optional.of(inventoryMaster));

        Mockito.lenient().when(inventoryDetailRepository.save(any(InventoryDetail.class))).thenAnswer(invocation -> {
            InventoryDetail inventoryDetail = invocation.getArgument(0);
            inventoryDetail.setTransactionId(1); // Assuming transactionId assigned by the database
            return inventoryDetail;
        });

        // Call the service method
        Integer updatedTransactionId = inventoryDetailService.updateInventoryDetail(1, request);

        // Assertions
        assertThat(updatedTransactionId).isEqualTo(existingInventoryDetail.getTransactionId()); // Assuming transactionId remains the same
        assertThat(existingInventoryDetail.getTransactionStatus()).isEqualTo(TransactionStatus.COMPLETED); // Assuming transaction status is updated
        assertThat(existingInventoryDetail.getAmount()).isEqualTo(BigDecimal.valueOf(500.0)); // Assuming amount is updated correctly
        assertThat(existingInventoryDetail.getUpdatedAt()).isAfter(LocalDateTime.now().minusSeconds(1));
    }

    @Test
    public void testUpdateInventoryDetailInvalidStatus() {
        // Mock request with invalid status
        InventoryDetailRequest request = new InventoryDetailRequest();
        request.setProductId(1);
        request.setTransactionStatus(TransactionStatus.PENDING); // Assuming invalid status

        // Mock existing inventory detail
        InventoryDetail existingInventoryDetail =createMockInventoryDetail();
        existingInventoryDetail.setTransactionStatus(TransactionStatus.COMPLETED); // Assuming existing status
        // Mock InventoryMaster
        InventoryMaster inventoryMaster = createMockInventoryMaster();
        inventoryMaster.setStockInHand(100); // Assuming existing stock in hand
        when(inventoryDetailRepository.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryDetailService.updateInventoryDetail(1, request);
        });

        // Assertion
        assertThat(exception.getMessage()).isEqualTo("Not found any transaction for given transactionId : 1");
    }

    @Test
    public void testUpdateInventoryDetailInvalidProduct() {
        InventoryDetailRequest request = new InventoryDetailRequest();
        request.setProductId(100); // Assuming productId does not exist in the database
        request.setTransactionStatus(TransactionStatus.COMPLETED); // Assuming this is a valid transaction status
        request.setQuantity(10); // Assuming a valid quantity

        // Mock existing inventory detail
        InventoryDetail existingInventoryDetail = createMockInventoryDetail();
        // Mock InventoryMaster repository to return empty Optional
        when(inventoryDetailRepository.findById(1)).thenReturn(Optional.of(existingInventoryDetail));

        // Call the service method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryDetailService.updateInventoryDetail(1, request);
        });

        // Assertion
        assertThat(exception.getMessage()).isEqualTo("invalid productId");
    }
}