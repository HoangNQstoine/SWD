package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "diet", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietEntity {

    @Id
    @Column(name = "dietId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dietId;
    @Basic
    @Column(name = "dietName", nullable = false, length = 255)
    private String dietName;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;

    @OneToMany(mappedBy = "dietEntity")
    private List<AnimalDietManagementEntity> animalDietManagementEntityList;


    @OneToMany(mappedBy = "dietEntity")
    private List<DietManagementEntity> dietManagementEntityList;


    @OneToMany(mappedBy = "dietEntity")
    private List<DietFoodDetailEntity> dietFoodDetailEntities;


    public DietEntity(int dietId, String dietName, Boolean status) {
        this.dietId = dietId;
        this.dietName = dietName;
        this.status = status;
    }
}
