package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "area_management", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaManagementEntity {
    @Id
    @Column(name = "areaManagementId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaManagementId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnoreProperties(value = "areaManagementEntityList")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "areaId", referencedColumnName = "areaId")
    @JsonIgnoreProperties(value = "areaManagementEntityList")
    private AreaEntity areaEntity;

    @Basic
    @Column(name = "dateStart", nullable = false, length = 255)
    private Date dateStart;
    @Basic
    @Column(name = "dateEnd", nullable = false, length = 255)
    private Date dateEnd;

    @Basic
    @Column(name = "isManaging", nullable = false, length = 255)
    private boolean isManaging;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;


}
