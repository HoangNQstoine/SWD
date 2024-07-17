package SWD.NET1704.repositories;

import SWD.NET1704.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository   extends JpaRepository<RoleEntity, Integer> {

    @Query(value = "SELECT r from RoleEntity r where r.roleName='ROLE_CUSTOMER'")
    RoleEntity getRoleCustomer();
}
