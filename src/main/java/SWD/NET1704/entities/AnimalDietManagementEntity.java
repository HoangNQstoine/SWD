package SWD.NET1704.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "animal_diet_management", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDietManagementEntity {

    @Id
    @Column(name = "animalDietManagementId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalDietManagementId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dietId", referencedColumnName = "dietId")
    @JsonIgnoreProperties(value = "animalManagementEntityList")
    private DietEntity dietEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "animalId", referencedColumnName = "animalId")
    @JsonIgnoreProperties(value = "animalDietManagementEntityList")
    private AnimalEntity animalEntity;

    @Basic
    @Column(name = "animalDietManagementName", nullable = false, length = 255)
    private String animalDietManagementName;
    @Basic
    @Column(name = "dateStart", nullable = false, length = 255)
    private Date dateStart;
    @Basic
    @Column(name = "dateEnd", length = 255)
    private Date dateEnd;

    @Basic
    @Column(name = "useStatus", nullable = false, length = 255)
    private String useStatus;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;
}
