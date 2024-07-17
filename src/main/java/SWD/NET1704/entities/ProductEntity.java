package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "swp_zoo_management", catalog = "")
public class ProductEntity {
    @Id
    @Column(name = "productId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(name = "productName")
    private String productName;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;


    @Transient
    private double averageRating;
    @OneToMany(mappedBy = "productEntity")
    private List<FeedbackEntity> feedbackEntityList;
    @OneToMany(mappedBy = "productEntity")
    private List<TransactionEntity> transactionEntities;

    @PostLoad
    private void calcAverageRating() {
        double sum = 0;
        if (!this.feedbackEntityList.isEmpty()) {
            for (FeedbackEntity feedbackEntity : this.feedbackEntityList) {
                sum = sum + feedbackEntity.getRating();
            }
            this.averageRating = sum / this.feedbackEntityList.size();
        } else {
            this.averageRating = 5;
        }
    }
}
