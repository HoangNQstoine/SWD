package SWD.NET1704.repositories;

import SWD.NET1704.entities.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Integer> {

    @Query(value = "SELECT * FROM swp_zoo_management.food f where f.status = true order by food_id desc",nativeQuery = true)
    List<FoodEntity> getAllByStatusTrue();
    @Query(value = "SELECT * FROM swp_zoo_management.food where date_end > now() and status = true",nativeQuery = true)
    List<FoodEntity> getAllFoodNotExpired();
}
