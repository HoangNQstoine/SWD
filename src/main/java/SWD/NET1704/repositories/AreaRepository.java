package SWD.NET1704.repositories;

import SWD.NET1704.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity,Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.area a where a.status = true",nativeQuery = true)
    List<AreaEntity> getAllByStatusIsTrue();
}
