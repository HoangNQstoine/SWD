package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking", schema = "swp_zoo_management", catalog = "")
public class BookingEntity {
    @Id
    @Column(name = "bookingId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "status")
    private boolean status;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnoreProperties("bookingEntityList")
    private UserEntity userEntity;


    @OneToMany(mappedBy = "bookingEntity")
    private List<TransactionEntity> transactionEntities;

    @Transient
    private double totalPrice;

    @PostLoad
    private void calcTotalPrice(){
        this.getTransactionEntities().forEach(transactionEntity -> this.totalPrice =  this.totalPrice + (transactionEntity.getQuantity() * transactionEntity.getProductEntity().getPrice()));
    }
}
