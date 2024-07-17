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
@Table(name = "dietFoodDetail", schema = "swp_zoo_management", catalog = "")
public class DietFoodDetailEntity {
    @Id
    @Column(name = "dietFoodDetailId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dietFoodDetailId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "foodId", referencedColumnName = "foodId")
    @JsonIgnoreProperties(value = "dietFoodDetailEntities")
    private FoodEntity foodEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dietId", referencedColumnName = "dietId")
    @JsonIgnoreProperties(value = "dietFoodDetailEntities")
    private DietEntity dietEntity;

    @Basic
    @Column(name = "quantity", nullable = false, length = 255)
    private int quantity;
}
