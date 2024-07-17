package SWD.NET1704.utils;

import SWD.NET1704.dtos.request.*;
import SWD.NET1704.entities.*;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.*;
import SWD.NET1704.dtos.response.FoodDTO;
import SWD.NET1704.entities.*;

/**
 * Use for mapper DTO to Entity
 */
@Service
public class DTOToEntity {


    /**
     * @param animalCagePayload - AnimalCagePayload
     * @return AnimalCageEntity
     */
    public AnimalCageEntity mapPayloadToAnimalCageEntity(AnimalCagePayload animalCagePayload) {
        AnimalCageEntity animalCageEntity = new AnimalCageEntity();
        animalCageEntity.setAnimalCageName(animalCagePayload.getAnimalCageName());
        animalCageEntity.setDescription(animalCagePayload.getDescription());
        animalCageEntity.setMaxQuantity(animalCagePayload.getMaxQuantity());
        return animalCageEntity;
    }

    /**
     * @param foodPayload - FoodPayload
     * @return FoodEntity
     */
    public FoodEntity mapPayloadToFoodEntity(FoodPayload foodPayload) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodName(foodPayload.getFoodName());
        foodEntity.setNutriment(foodPayload.getNutriment());
        foodEntity.setUnit(foodPayload.getUnit());
        foodEntity.setImage(foodPayload.getImage());
        foodEntity.setDateStart(foodPayload.getDateStart());
        foodEntity.setDateEnd(foodPayload.getDateEnd());
        foodEntity.setQuantity(foodPayload.getQuantity());
        return foodEntity;
    }

    /**
     * @param foodDTO - FoodDTO
     * @return FoodEntity
     */
    public FoodEntity mapDTOtoFoodEntity(FoodDTO foodDTO) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodName(foodDTO.getFoodName());
        foodEntity.setFoodId(foodDTO.getFoodId());
        foodEntity.setNutriment(foodDTO.getNutriment());
        foodEntity.setUnit(foodDTO.getUnit());
        foodEntity.setDateStart(foodDTO.getDateStart());
        foodEntity.setDateEnd(foodDTO.getDateEnd());
        foodEntity.setQuantity(foodDTO.getQuantity());
        foodEntity.setStatus(foodDTO.getStatus());
        return foodEntity;
    }

    /**
     * @param dietPayload - DietPayload
     * @return DietEntity
     */
//    public DietEntity mapPayloadToDietEntity(DietPayload dietPayload) {
//        DietEntity dietEntity = new DietEntity();
//        dietEntity.setDietName(dietPayload.getDietName());
//        List<FoodEntity> foodEntityList = new ArrayList<>();
//        if (dietPayload.getFoodDTOS().size() > 0) {
//            for (FoodDTO foodDTO : dietPayload.getFoodDTOS()
//            ) {
//                foodEntityList.add(mapDTOtoFoodEntity(foodDTO));
//            }
//        }
////        dietEntity.set(new HashSet<>(foodEntityList));
//        return dietEntity;
//    }


    /**
     * @param payload - AnimalDietManagementPayload
     * @return AnimalDietManagementEntity
     */
    public AnimalDietManagementEntity mapPayloadToAnimalDietManagementEntity(AnimalDietManagementPayload payload){
        AnimalDietManagementEntity animalDietManagementEntity = new AnimalDietManagementEntity();
        animalDietManagementEntity.setAnimalDietManagementName(payload.getAnimalDietManagementName());
        return animalDietManagementEntity;
    }

    /**
     * @param payload  AnimalManagementPayload
     * @return AnimalManagementEntity
     */
    public AnimalManagementEntity mapPayloadToAnimalManagementEntity(AnimalManagementPayload payload){
        AnimalManagementEntity animalManagementEntity = new AnimalManagementEntity();
        animalManagementEntity.setDateStart(payload.getDateStart());
        animalManagementEntity.setDateEnd(payload.getDateEnd());
        return animalManagementEntity;
    }

    /**
     * @param animalCageDetailPayload  AnimalCageDetailPayload
     * @return AnimalCageDetail
     */
    public AnimalCageDetail mapPayloadToAnimalCageDetail(AnimalCageDetailPayload animalCageDetailPayload){
        AnimalCageDetail animalCageDetail = new AnimalCageDetail();
        animalCageDetail.setDateStart(animalCageDetailPayload.getDateStart());

        animalCageDetail.setAnimalCageDetailName(animalCageDetailPayload.getAnimalCageDetailName());
        return animalCageDetail;
    }

    /**
     * @param productPayload  ProductPayload
     * @return ProductEntity
     */
    public  ProductEntity mapPayloadToProductEntity(ProductPayload productPayload){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPrice(productPayload.getPrice());
        productEntity.setQuantity(productPayload.getQuantity());
        productEntity.setDescription(productPayload.getDescription());
        productEntity.setProductName(productPayload.getProductName());
        productEntity.setImage(productPayload.getImage());
        return  productEntity;
    }

    public NewsEntity mapPayloadToNewsEntity(NewsPayload newsPayload){
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle(newsPayload.getTitle());
        newsEntity.setImage(newsPayload.getImage());
        newsEntity.setContent(newsPayload.getContent());
        newsEntity.setCreatedDate(newsPayload.getDateCreated());
        newsEntity.setNewsType(newsPayload.getNewsType());
        return newsEntity;
    }
    public FeedbackEntity mapPayloadToFeedbackEntity(FeedbackPayload feedbackPayload){
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setTitle(feedbackPayload.getTitle());
        feedbackEntity.setComment(feedbackPayload.getContent());
        feedbackEntity.setRating(feedbackPayload.getRating());
        return feedbackEntity;
    }
}
