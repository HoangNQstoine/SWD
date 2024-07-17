package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "animal", schema = "swp_zoo_management", catalog = "")
public class AnimalEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animalId")
    private int animalId;
    @Column(name = "animalName")
    private String animalName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "catalogueId", referencedColumnName = "catalogueId")
    @JsonIgnoreProperties(value = "animalEntityList")
    private CatalogueEntity catalogueEntity;



    @OneToMany(mappedBy = "animalEntity")
    private List<AnimalCageDetail> animalCageDetailList;
    @OneToMany(mappedBy = "animalEntity")
    private List<AnimalManagementEntity> animalManagementEntityList;
    @OneToMany(mappedBy = "animalEntity")
    private List<AnimalDietManagementEntity> animalDietManagementEntityList;

    @Column(name = "status")
    private Boolean status;
    @Column(name = "image")
    private String image;
    @Column(name = "country")
    private String country;
    @Column(name = "isRare")
    private Boolean isRare;
    @Column(name = "gender")
    private String gender;

}
