package SWD.NET1704.utils;

import SWD.NET1704.dtos.response.*;
import SWD.NET1704.entities.*;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.response.*;
import SWD.NET1704.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use for mapper Entity to DTO
 */
@Service
public class EntityToDTOMapper {


    /**
     * @param animalCageEntity - AnimalCageEntity
     * @return AnimalCageDTO
     * <p>
     * mapAnimalCageEntityToDTO
     */
    public AnimalCageDTO mapAnimalCageEntityToDTO(AnimalCageEntity animalCageEntity) {
        AnimalCageDTO animalCageDTO = new AnimalCageDTO();
        animalCageDTO.setAnimalCageId(animalCageEntity.getAnimalCageId());
        animalCageDTO.setAnimalCageName(animalCageEntity.getAnimalCageName());
        animalCageDTO.setDescription(animalCageEntity.getDescription());
        animalCageDTO.setMaxQuantity(animalCageEntity.getMaxQuantity());
        animalCageDTO.setUse(animalCageEntity.getIsUse());
        animalCageDTO.setStatus(animalCageEntity.getStatus());
        animalCageDTO.setAreaName(animalCageEntity.getAreaEntity().getAreaName());
        animalCageDTO.setAreaDescription(animalCageEntity.getAreaEntity().getDescription());
        return animalCageDTO;

    }

    /**
     * @param areaEntity - AreaEntity
     * @return AreaDTO
     * <p>
     * mapAreaEntityToDTO
     */
    public AreaDTO mapAreaEntityToDTO(AreaEntity areaEntity) {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setAreaId(areaEntity.getAreaId());
        areaDTO.setAreaName(areaEntity.getAreaName());
        areaDTO.setDescription(areaEntity.getDescription());
        areaDTO.setStatus(areaEntity.getStatus());
        if (!areaEntity.getAnimalCageEntityList().isEmpty()) {
            List<AnimalCageResponse> animalCageResponseList = new ArrayList<>();

            areaEntity.getAnimalCageEntityList().forEach(animalCageEntity -> {
                AnimalCageResponse animalCageResponse = new AnimalCageResponse();
                animalCageResponse.setAnimalCageId(animalCageEntity.getAnimalCageId());
                animalCageResponse.setAnimalCageName(animalCageEntity.getAnimalCageName());
                animalCageResponse.setDescription(animalCageEntity.getDescription());
                animalCageResponse.setUse(animalCageEntity.getIsUse());
                animalCageResponseList.add(animalCageResponse);
            });
            areaDTO.setAnimalCageResponseList(animalCageResponseList);
        }


        return areaDTO;

    }

    /**
     * @param animalEntity -AnimalEntity
     * @return AnimalDTO
     * mapAnimalEntityToDTO
     */
    public AnimalDTO mapAnimalEntityToDTO(AnimalEntity animalEntity) {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setAnimalId(animalEntity.getAnimalId());
        animalDTO.setAnimalName(animalEntity.getAnimalName());
        animalDTO.setStatus(animalEntity.getStatus());
        animalDTO.setImage(animalEntity.getImage());
        animalDTO.setGender(animalEntity.getGender());
        animalDTO.setCountry(animalEntity.getCountry());
        animalDTO.setRare(animalEntity.getIsRare());
        if (animalEntity.getCatalogueEntity() != null) {
            CatalogueDTO catalogueDTO = new CatalogueDTO(animalEntity.getCatalogueEntity().getCatalogueId(),
                    animalEntity.getCatalogueEntity().getCatalogueName(),
                    animalEntity.getCatalogueEntity().getStatus());
            animalDTO.setCatalogueDTO(catalogueDTO);
        }
        return animalDTO;
    }

    /**
     * @param userEntity - UserEntity
     * @return UserDTO
     */
    public UserDTO mapUserEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        if (userEntity.getRoleEntity() != null) {
            RoleDTO roleDTO = new RoleDTO(userEntity.getRoleEntity().getRoleId(), userEntity.getRoleEntity().getRoleName());
            userDTO.setRoleDTO(roleDTO);
        }
        if (userEntity.getProfileEntity() != null) {
            ProfileDTO profileDTO = new ProfileDTO(
                    userEntity.getProfileEntity().getProfileId(),
                    userEntity.getProfileEntity().getFirstName(),
                    userEntity.getProfileEntity().getLastName(),
                    userEntity.getProfileEntity().getAddress(),
                    userEntity.getProfileEntity().getPhoneNumber(),
                    userEntity.getProfileEntity().getGender(),
                    userEntity.getProfileEntity().getAvatar(),
                    userEntity.getProfileEntity().getIsDeleted());
            userDTO.setFullName(profileDTO.getFirstName() + " " + profileDTO.getLastName());
            userDTO.setProfileDTO(profileDTO);
        }

        userDTO.setStatus(userEntity.getStatus());
        return userDTO;
    }

    /**
     * @param foodEntity - FoodEntity
     * @return FoodDTO
     */
    public FoodDTO mapFoodEntityToDTO(FoodEntity foodEntity) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodId(foodEntity.getFoodId());
        foodDTO.setFoodName(foodEntity.getFoodName());
        foodDTO.setNutriment(foodEntity.getNutriment());
        foodDTO.setUnit(foodEntity.getUnit());
        foodDTO.setImage(foodEntity.getImage());
        foodDTO.setDateStart(foodEntity.getDateStart());
        foodDTO.setDateEnd(foodEntity.getDateEnd());
        foodDTO.setQuantity(foodEntity.getQuantity());
        foodDTO.setStatus(foodEntity.getStatus());
        return foodDTO;
    }

    /**
     * @param dietEntity -DietEntity
     * @return DietDTO
     */
    public DietDTO mapDietEntityToDTO(DietEntity dietEntity) {
        DietDTO dietDTO = new DietDTO();
        dietDTO.setDietId(dietEntity.getDietId());
        dietDTO.setDietName(dietEntity.getDietName());
        dietDTO.setStatus(dietEntity.getStatus());
        dietDTO.setDietFoodResponses(dietEntity.getDietFoodDetailEntities().stream().map(detailEntity -> {
            DietFoodResponse dietFoodResponse = new DietFoodResponse();
            dietFoodResponse.setFoodId(detailEntity.getFoodEntity().getFoodId());
            dietFoodResponse.setFoodName(detailEntity.getFoodEntity().getFoodName());
            dietFoodResponse.setDietFoodQuantity(detailEntity.getQuantity());
            dietFoodResponse.setFoodImage(detailEntity.getFoodEntity().getFoodName());
            dietFoodResponse.setNutriment(detailEntity.getFoodEntity().getNutriment());
            dietFoodResponse.setUnit(detailEntity.getFoodEntity().getUnit());
            dietFoodResponse.setDateStart(detailEntity.getFoodEntity().getDateStart());
            dietFoodResponse.setDateEnd(detailEntity.getFoodEntity().getDateEnd());
            return dietFoodResponse;
        }).collect(Collectors.toList()));
        return dietDTO;
    }

    /**
     * @param entity AnimalDietManagementEntity
     * @return AnimalDietManagementResponse
     */
    public AnimalDietManagementResponse mapEntityToAnimalDietManagementResponse(AnimalDietManagementEntity entity) {
        AnimalDietManagementResponse animalDietManagementResponse = new AnimalDietManagementResponse();
        animalDietManagementResponse.setAnimalDietManagementId(entity.getAnimalDietManagementId());
        animalDietManagementResponse.setAnimalDietManagementName(entity.getAnimalDietManagementName());
        animalDietManagementResponse.setAnimalId(entity.getAnimalEntity().getAnimalId());
        animalDietManagementResponse.setAnimalName(entity.getAnimalEntity().getAnimalName());
        animalDietManagementResponse.setDietId(entity.getDietEntity().getDietId());
        animalDietManagementResponse.setDietName(entity.getDietEntity().getDietName());
        animalDietManagementResponse.setDateStart(entity.getDateStart());
        animalDietManagementResponse.setDateEnd(entity.getDateEnd());
        animalDietManagementResponse.setStatus(entity.getStatus());
        animalDietManagementResponse.setUseStatus(entity.getUseStatus());
        return animalDietManagementResponse;
    }

    /**
     * @param animalManagementEntity AnimalManagementEntity
     * @return AnimalManagementResponse
     */
    public AnimalManagementResponse mapEntityToAnimalManagementResponse(AnimalManagementEntity animalManagementEntity) {
        AnimalManagementResponse response = new AnimalManagementResponse();
        response.setAnimalTrainingId(animalManagementEntity.getAnimalManagementId());
        response.setAnimalId(animalManagementEntity.getAnimalEntity().getAnimalId());
        response.setAnimalName(animalManagementEntity.getAnimalEntity().getAnimalName());
        response.setUserId(animalManagementEntity.getUserEntity().getUserId());
        if (animalManagementEntity.getUserEntity().getProfileEntity() != null) {
            response.setFullName(
                    animalManagementEntity.getUserEntity().getProfileEntity().getFirstName() + "  " + animalManagementEntity.getUserEntity().getProfileEntity().getLastName());
        }
        response.setDateStart(animalManagementEntity.getDateStart());
        response.setDateEnd(animalManagementEntity.getDateEnd());
        response.setDescription(animalManagementEntity.getTrainingDescription());
        response.setAssignStatus(animalManagementEntity.getAssignStatus());
        response.setStatus(animalManagementEntity.getStatus());
        return response;
    }

    /**
     * @param areaManagementEntity AreaManagementEntity
     * @return AnimalManagementResponse
     */
    public AreaManagementResponse mapEntityToAreaManagementResponse(AreaManagementEntity areaManagementEntity) {
        AreaManagementResponse response = new AreaManagementResponse();
        response.setAreaManagementId(areaManagementEntity.getAreaManagementId());
        response.setAreaId(areaManagementEntity.getAreaEntity().getAreaId());
        response.setAreaName(areaManagementEntity.getAreaEntity().getAreaName());
        response.setUserId(areaManagementEntity.getUserEntity().getUserId());
        if (areaManagementEntity.getUserEntity().getProfileEntity() != null) {
            response.setFullName(
                    areaManagementEntity.getUserEntity().getProfileEntity().getFirstName() + "  " + areaManagementEntity.getUserEntity().getProfileEntity().getLastName());
        }
        response.setDateStart(areaManagementEntity.getDateStart());
        response.setDateEnd(areaManagementEntity.getDateEnd());
        response.setManaging(areaManagementEntity.isManaging());
        response.setStatus(areaManagementEntity.getStatus());
        return response;
    }

    /**
     * @param animalCageDetail AnimalCageDetail
     * @return AnimalCageDetailResponse
     */
    public AnimalCageDetailResponse mapEntityToAnimalCageDetailResponse(AnimalCageDetail animalCageDetail) {
        AnimalCageDetailResponse animalCageDetailResponse = new AnimalCageDetailResponse();
        animalCageDetailResponse.setAnimalCageDetailId(animalCageDetail.getAnimalCageDetailId());
        animalCageDetailResponse.setAnimalCageDetailName(animalCageDetail.getAnimalCageDetailName());
        animalCageDetailResponse.setAnimalCageId(animalCageDetail.getAnimalCageEntity().getAnimalCageId());
        animalCageDetailResponse.setAnimalCageName(animalCageDetail.getAnimalCageEntity().getAnimalCageName());
        animalCageDetailResponse.setAnimalId(animalCageDetail.getAnimalEntity().getAnimalId());
        animalCageDetailResponse.setAnimalName(animalCageDetail.getAnimalEntity().getAnimalName());
        animalCageDetailResponse.setDateStart(animalCageDetail.getDateStart());
        animalCageDetailResponse.setDateEnd(animalCageDetail.getDateEnd());
        animalCageDetailResponse.setStatus(animalCageDetail.getStatus());
        animalCageDetailResponse.setUseStatus(animalCageDetail.getUseStatus());
        return animalCageDetailResponse;
    }

    /**
     * @param feedbackEntity FeedbackEntity
     * @return FeedbackDTO
     */
    public FeedbackDTO mapFeedbackEntityToDTO(FeedbackEntity feedbackEntity) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedbackEntity.getFeedbackId());
        feedbackDTO.setTitle(feedbackEntity.getTitle());
        feedbackDTO.setRating(feedbackEntity.getRating());
        feedbackDTO.setContent(feedbackEntity.getComment());
        feedbackDTO.setFullName(feedbackEntity.getUserEntity().getProfileEntity().getFirstName() + Constants.SPACE + feedbackEntity.getUserEntity().getProfileEntity().getLastName());
        feedbackDTO.setProductName(feedbackEntity.getProductEntity().getProductName());
        feedbackDTO.setStatus(feedbackEntity.isStatus());
        return feedbackDTO;
    }

    /**
     * @param productEntity ProductEntity
     * @return ProductDTO
     */
    public ProductDTO mapProductEntityToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setQuantity(productEntity.getQuantity());
        productDTO.setImage(productEntity.getImage());
        productDTO.setRating(productEntity.getAverageRating());
        if (!productEntity.getFeedbackEntityList().isEmpty()) {
            List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
            productEntity.getFeedbackEntityList().forEach(feedbackEntity -> {
                FeedbackDTO feedbackDTO = mapFeedbackEntityToDTO(feedbackEntity);
                feedbackDTOS.add(feedbackDTO);
            });
            productDTO.setFeedbackDTOList(feedbackDTOS);
        }
        return productDTO;
    }

    /**
     * @param newsEntity NewsEntity
     * @return NewsDTO
     */
    public NewsDTO mapNewsEntityToDTO(NewsEntity newsEntity) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsId(newsEntity.getNewsId());
        newsDTO.setTitle(newsEntity.getTitle());
        newsDTO.setImage(newsEntity.getImage());
        newsDTO.setContent(newsEntity.getContent());
        newsDTO.setNewsType(newsEntity.getNewsType());
        newsDTO.setCreatedDate(newsEntity.getCreatedDate());
        newsDTO.setStatus(newsEntity.isStatus());
        return newsDTO;
    }

    /**
     * @param bookingEntity BookingEntity
     * @return BookingDTO
     */
    public BookingDTO mapBookingEntityToDTO(BookingEntity bookingEntity) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setFullName(bookingEntity.getUserEntity().getProfileEntity().getFirstName() + Constants.SPACE + bookingEntity.getUserEntity().getProfileEntity().getLastName());
        bookingDTO.setUserId(bookingEntity.getUserEntity().getUserId());
        bookingDTO.setBookingDate(bookingEntity.getOrderDate());
        bookingDTO.setStatus(bookingEntity.isStatus());
        bookingDTO.setProductDTOS(bookingEntity.getTransactionEntities().stream().map(transactionEntity -> {
            BookingProductResponse bookingProductResponse = new BookingProductResponse();
            bookingProductResponse.setProductId(transactionEntity.getProductEntity().getProductId());
            bookingProductResponse.setBookingProductQuantity(transactionEntity.getQuantity());
            bookingProductResponse.setProductName(transactionEntity.getProductEntity().getProductName());
            bookingProductResponse.setProductPrice(transactionEntity.getProductEntity().getPrice());
            return bookingProductResponse;
        }).collect(Collectors.toList()));
        bookingDTO.setTotalPrice(bookingEntity.getTotalPrice());
        return bookingDTO;
    }
}
