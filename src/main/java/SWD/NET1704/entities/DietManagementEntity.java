package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "diet_management", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietManagementEntity {

    @Id
    @Column(name = "dietManagementId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dietManagementId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnoreProperties(value = "dietManagementEntityList")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dietId", referencedColumnName = "dietId")
    @JsonIgnoreProperties(value = "dietManagementEntityList")
    private DietEntity dietEntity;

    @Basic
    @Column(name = "dateStart", nullable = false, length = 255)
    private Date dateStart;
    @Basic
    @Column(name = "dateEnd", nullable = false, length = 255)
    private Date dateEnd;

    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;
}
