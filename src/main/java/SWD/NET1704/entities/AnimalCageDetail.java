package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "animal_cage_detail", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCageDetail {
    @Id
    @Column(name = "animalCageDetailId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalCageDetailId;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "animalId", referencedColumnName = "animalId")
    @JsonIgnoreProperties(value = "animalCageDetailList")
    private AnimalEntity animalEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "animalCageId", referencedColumnName = "animalCageId")
    @JsonIgnoreProperties(value = "animalCageDetailList")
    private AnimalCageEntity animalCageEntity;

    @Basic
    @Column(name = "animalCageDetailName", nullable = false, length = 255)
    private String animalCageDetailName;

    @Basic
    @Column(name = "dateStart", nullable = false, length = 255)
    private Date dateStart;
    @Basic
    @Column(name = "dateEnd", length = 255)
    private Date  dateEnd;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;

    @Basic
    @Column(name = "useStatus", nullable = false, length = 255)
    private String useStatus;
}
