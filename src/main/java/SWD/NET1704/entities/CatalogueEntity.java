package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "catalogue", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueEntity {

    @Id
    @Column(name = "catalogueId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catalogueId;
    @Basic
    @Column(name = "catalogueName", nullable = false, length = 255)
    private String catalogueName;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;
    @OneToMany(mappedBy = "catalogueEntity")
    private List<AnimalEntity> animalEntityList;

    public CatalogueEntity(int catalogueId, String catalogueName, Boolean status) {
        this.catalogueId = catalogueId;
        this.catalogueName = catalogueName;
        this.status = status;
    }
}
