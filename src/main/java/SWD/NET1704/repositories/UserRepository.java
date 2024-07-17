package SWD.NET1704.repositories;

import SWD.NET1704.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.user u where u.status = true",nativeQuery = true)
    List<UserEntity> getAllByStatusIsTrue();

    @Query(value = "SELECT u from UserEntity u where u.email=:email and u.password=:password and u.status = true")
    UserEntity findByEmailAndPassword(String email, String password);
    @Query(value = "SELECT u from UserEntity u where u.roleEntity.roleName='ROLE_TRAINER' and u.status = true")
    List<UserEntity> findAllTrainers();
   @Query(value = "SELECT u from UserEntity u where u.roleEntity.roleName='ROLE_STAFF' and u.status = true")
    List<UserEntity> findAllStaffs();
    @Query(value = "SELECT u from UserEntity u where u.email=:email and u.status = true")
    UserEntity existsByEmail(String email);
}
