package SWD.NET1704.repositories;

import SWD.NET1704.entities.DietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DietRepository  extends JpaRepository<DietEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.diet d where d.status = true order by diet_id desc",nativeQuery = true)
    List<DietEntity> getAllByStatusTrue();
}
