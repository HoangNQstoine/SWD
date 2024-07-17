package SWD.NET1704.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "userId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String  email;
    @Basic
    @Column(name = "password", nullable = false)
    private String  password;

    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    @JsonIgnoreProperties(value = "userEntityList")
    private RoleEntity roleEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    private ProfileEntity profileEntity;


    @OneToMany(mappedBy = "userEntity")
    private List<AreaManagementEntity> areaManagementEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<AnimalManagementEntity> animalManagementEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<DietManagementEntity> dietManagementEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<FeedbackEntity> feedbackEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<BookingEntity> bookingEntityList;
}
