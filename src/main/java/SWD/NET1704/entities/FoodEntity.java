package SWD.NET1704.entities;


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
@Table(name = "food", schema = "swp_zoo_management", catalog = "")
public class FoodEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodId")
    private int foodId;
    @Column(name = "foodName")
    private String foodName;
    @Column(name = "unit")
    private String unit;
    @Column(name = "nutriment")
    private String nutriment;
    @Column(name = "image")
    private String image;


    @Basic
    @Column(name = "dateStart", nullable = false, length = 255)
    private Date dateStart;
    @Basic
    @Column(name = "dateEnd", nullable = false, length = 255)
    private Date  dateEnd;

    @Basic
    @Column(name = "quantity", nullable = false, length = 255)
    private int  quantity;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;

    @OneToMany(mappedBy = "foodEntity")
    private List<DietFoodDetailEntity> dietFoodDetailEntities;
}
