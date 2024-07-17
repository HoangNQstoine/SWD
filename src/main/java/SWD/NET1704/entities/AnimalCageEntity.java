package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "animal_cage", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCageEntity {
    @Id
    @Column(name = "animalCageId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalCageId;
    @Basic
    @Column(name = "animalCageName", nullable = false, length = 255)
    private String animalCageName;
    @Basic
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;

    @Basic
    @Column(name = "isUse", nullable = false, length = 255)
    private Boolean isUse;

    @Basic
    @Column(name = "maxQuantity", nullable = false, length = 255)
    private int maxQuantity;

    @OneToMany(mappedBy = "animalCageEntity")
    private List<AnimalCageDetail> animalCageDetailList;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "areaId", referencedColumnName = "areaId")
    @JsonIgnoreProperties(value = "animalCageEntityList")
    private AreaEntity areaEntity;
}
