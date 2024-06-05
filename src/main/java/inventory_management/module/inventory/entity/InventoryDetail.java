package inventory_management.module.inventory.entity;

import inventory_management.module.inventory.enums.TransactionStatus;
import inventory_management.module.inventory.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory_detail")
public class InventoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Setter(AccessLevel.NONE)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private InventoryMaster productId;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Basic(optional = false)
    @Column(name = "quantity")
    private Integer quantity;

    @Basic(optional = false)
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name = "remarks")
    private String remarks;

    @Basic(optional = false)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
