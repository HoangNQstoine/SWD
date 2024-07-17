package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction", schema = "swp_zoo_management", catalog = "")
public class TransactionEntity {
    @Id
    @Column(name = "transactionId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @JsonIgnoreProperties(value = "transactionEntities")
    private ProductEntity productEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bookingId", referencedColumnName = "bookingId")
    @JsonIgnoreProperties(value = "transactionEntities")
    private BookingEntity bookingEntity;

    @Basic
    @Column(name = "quantity", nullable = false, length = 255)
    private int quantity;

}
