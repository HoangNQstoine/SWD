package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile", schema = "swp_zoo_management", catalog = "")
public class ProfileEntity {
    @Id
    @Column(name = "profile_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;
    @Basic
    @Column(name = "firstName", nullable = true, length = 255)
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = true, length = 255)
    private String lastName;
    @Basic
    @Column(name = "address", nullable = true, length = 255)
    private String address;
    @Basic
    @Column(name = "gender", nullable = true)
    private String gender;
    @Basic
    @Column(name = "avatar", nullable = true, length = 255)
    private String avatar;
    @Basic
    @Column(name = "phoneNumber", nullable = true, length = 255)
    private String phoneNumber;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;

    public ProfileEntity(String firstName, String lastName, String address, String phoneNumber, String gender, String avatar) {
    }
}
