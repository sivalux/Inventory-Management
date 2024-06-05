package inventory_management.module.inventory.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory_master")
public class InventoryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Setter(AccessLevel.NONE)
    @Column(name = "product_id")
    private Integer productId;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @OneToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category categoryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brandId;

    @Basic(optional = false)
    @Column(name = "stock_in_hand")
    private Integer stockInHand;

    @Basic(optional = false)
    @Column(name = "unit_price")
    private Double unitPrice;

    @OneToOne(optional = false)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplierId;

    @Column(name = "remarks")
    private String remarks;

    @Basic(optional = false)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
