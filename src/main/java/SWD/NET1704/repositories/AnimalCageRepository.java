package SWD.NET1704.repositories;

import SWD.NET1704.entities.AnimalCageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalCageRepository  extends JpaRepository<AnimalCageEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.animal_cage a where a.status = true order by animal_cage_id desc",nativeQuery = true)
    List<AnimalCageEntity> getAllByStatusIsTrue();
    @Query(value = "SELECT * FROM swp_zoo_management.animal_cage a where a.is_use=false and a.status = true",nativeQuery = true)
    List<AnimalCageEntity> getAllAnimalCageIsNotUse();

}
