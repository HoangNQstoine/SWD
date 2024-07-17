package SWD.NET1704.repositories;

import SWD.NET1704.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.animal a where a.status = true order by animal_id desc",nativeQuery = true)
    List<AnimalEntity> getAllByStatusIsTrue();
}
