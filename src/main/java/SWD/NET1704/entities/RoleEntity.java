package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role", schema = "swp_zoo_management", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @Column(name = "roleId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Basic
    @Column(name = "roleName", nullable = false, length = 255)
    private String  roleName;


    @OneToMany(mappedBy = "roleEntity")
    private List<UserEntity> userEntityList;


}
