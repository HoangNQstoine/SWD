package SWD.NET1704.repositories;

import java.util.List;

import SWD.NET1704.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.news n where n.status = true", nativeQuery = true)
    List<NewsEntity> getAllByStatusTrue();
}
