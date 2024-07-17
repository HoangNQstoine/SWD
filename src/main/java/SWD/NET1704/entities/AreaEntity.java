package SWD.NET1704.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "area", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaEntity {
    @Id
    @Column(name = "areaId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;
    @Basic
    @Column(name = "areaName", nullable = false, length = 255)
    private String areaName;
    @Basic
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;

    @OneToMany(mappedBy = "areaEntity")
    private List<AnimalCageEntity> animalCageEntityList;


    @OneToMany(mappedBy = "areaEntity")
    private List<AreaManagementEntity> areaManagementEntityList;

    public AreaEntity(int areaId, String areaName, Boolean status) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.status = status;
    }
}
