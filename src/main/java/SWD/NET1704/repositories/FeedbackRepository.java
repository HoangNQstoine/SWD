package SWD.NET1704.repositories;

import SWD.NET1704.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {
    @Query(value = "SELECT f from FeedbackEntity f where f.status = true")
    List<FeedbackEntity> findAllFeedback();

    @Query(value = "SELECT f from FeedbackEntity f where f.userEntity.userId=:userId and f.status = true")
    List<FeedbackEntity> getFeedbackByUser(int userId);

    @Query(value = "SELECT f from FeedbackEntity f where f.productEntity.productId=:productId and f.status = true")
    List<FeedbackEntity> getFeedbackByProduct(int productId);
}
