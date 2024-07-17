package SWD.NET1704.utils;

import SWD.NET1704.dtos.request.*;
import SWD.NET1704.entities.AnimalCageDetail;
import SWD.NET1704.entities.AnimalDietManagementEntity;
import SWD.NET1704.entities.AnimalManagementEntity;
import SWD.NET1704.entities.AreaManagementEntity;
import SWD.NET1704.repositories.*;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.*;
import SWD.NET1704.repositories.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class HelperUtil {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AnimalManagementRepository animalManagementRepository;
    private final AnimalRepository animalRepository;
    private final AnimalCageRepository animalCageRepository;
    private final DietRepository dietRepository;
    private final AreaRepository areaRepository;
    private final AreaManagementRepository areaManagementRepository;
    private final AnimalCageDetailRepository animalCageDetailRepository;
    public boolean validateEmail(String email) {
        //Regular Expression
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String validateFeedbackPayload(FeedbackPayload feedbackPayload) {
        if (Strings.isNullOrEmpty(feedbackPayload.getContent()))
            return Constants.CONTENT_NULL_OR_EMPTY_ERROR;
        if (Strings.isNullOrEmpty(feedbackPayload.getTitle()))
            return Constants.TITLE_NULL_OR_EMPTY_ERROR;
        if (productRepository.findById(feedbackPayload.getProductId()).isEmpty())
            return Constants.PRODUCT_NULL_ERROR;
        if (userRepository.findById(feedbackPayload.getUserId()).isEmpty())
            return Constants.USER_NULL_ERROR;
        return null;
    }

    public String validateAnimalManagementPayload(AnimalManagementPayload animalManagementPayload, AnimalManagementEntity oldAnimalManagementEntity) {
        if (animalRepository.findById(animalManagementPayload.getAnimalId()).isEmpty())
            return Constants.NOT_FOUND;

        if (userRepository.findById(animalManagementPayload.getUserId()).isEmpty())
            return Constants.NOT_FOUND;

        if (animalManagementPayload.getDateEnd() != null) {
                if (!animalManagementRepository.checkAnimalOverlapTime(animalManagementPayload.getAnimalId(), animalManagementPayload.getDateStart()).isEmpty())
                    return Constants.ANIMAL_OVERLAP_TRAINING_ERROR;

            if (animalManagementPayload.getDateStart().getTime() >= animalManagementPayload.getDateEnd().getTime())
                return Constants.DATE_START_GREATER_THAN_DATE_END;
            oldAnimalManagementEntity.setDateEnd(animalManagementPayload.getDateEnd());
        }
        if (animalManagementRepository.getAssignByTrainer(animalManagementPayload.getUserId()).size() >= 3)
            return Constants.TRAINER_OVERLAP_TRAINING_ERROR;
        return null;
    }
    public String validateUpdateAnimalManagementPayload(AnimalManagementPayload animalManagementPayload, AnimalManagementEntity oldAnimalManagementEntity) {
        if (animalRepository.findById(animalManagementPayload.getAnimalId()).isEmpty())
            return Constants.NOT_FOUND;

        if (userRepository.findById(animalManagementPayload.getUserId()).isEmpty())
            return Constants.NOT_FOUND;

            if (animalManagementPayload.getDateEnd() != null) {
                if  (oldAnimalManagementEntity.getAnimalEntity().getAnimalId() != animalManagementPayload.getAnimalId()){
                    if (!animalManagementRepository.checkAnimalOverlapTime(animalManagementPayload.getAnimalId(), animalManagementPayload.getDateStart()).isEmpty())
                        return Constants.ANIMAL_OVERLAP_TRAINING_ERROR;
                }
                if (animalManagementPayload.getDateStart().getTime() >= animalManagementPayload.getDateEnd().getTime())
                    return Constants.DATE_START_GREATER_THAN_DATE_END;
                oldAnimalManagementEntity.setDateEnd(animalManagementPayload.getDateEnd());
            }

        if (animalManagementRepository.getAssignByTrainer(animalManagementPayload.getUserId()).size() >= 3
                && animalManagementPayload.getUserId() != oldAnimalManagementEntity.getUserEntity().getUserId())
            return Constants.TRAINER_OVERLAP_TRAINING_ERROR;
        return null;
    }

    /**
     * @param animalCageDetailPayload  AnimalCageDetailPayload
     * @param animalCageDetail AnimalCageDetail
     * @return errorMessage
     */
    public String validateAnimalCageDetailPayload(AnimalCageDetailPayload animalCageDetailPayload, AnimalCageDetail animalCageDetail) {
        if (animalRepository.findById(animalCageDetailPayload.getAnimalId()).isEmpty())
            return Constants.NOT_FOUND;

        if (animalCageRepository.findById(animalCageDetailPayload.getAnimalCageId()).isEmpty())
            return Constants.NOT_FOUND;


        if (!animalCageDetailRepository.getAllByCage(animalCageDetailPayload.getAnimalCageId()).isEmpty()){
            if ((animalRepository.getOne(animalCageDetailPayload.getAnimalId()).getCatalogueEntity().getCatalogueId()
                    != animalCageDetailRepository.getAllByCage(animalCageDetailPayload.getAnimalCageId()).get(0).getAnimalEntity().getCatalogueEntity().getCatalogueId())
                    && animalCageRepository.getOne(animalCageDetailPayload.getAnimalCageId()).getIsUse())
                return Constants.PREDATOR_CAGE_ERROR;
        }

        if (animalCageDetailPayload.getDateEnd() != null) {
            if (animalCageDetailPayload.getDateStart().getTime() >= animalCageDetailPayload.getDateEnd().getTime())
                return Constants.DATE_START_GREATER_THAN_DATE_END;
            animalCageDetail.setDateEnd(animalCageDetailPayload.getDateEnd());
        }

        return null;
    }

    public String validateAnimalDietPayload(AnimalDietManagementPayload animalDietManagementPayload, AnimalDietManagementEntity animalDietManagementEntity) {
        if (animalRepository.findById(animalDietManagementPayload.getAnimalId()).isEmpty())
            return "Not found Animal!!!";

        if (dietRepository.findById(animalDietManagementPayload.getDietId()).isEmpty())
            return "Not found Diet!!!";
        animalDietManagementEntity.setDateStart(animalDietManagementPayload.getDateStart());
        if (animalDietManagementPayload.getDateEnd() != null) {
            if (animalDietManagementPayload.getDateStart().getTime() >= animalDietManagementPayload.getDateEnd().getTime())
                return Constants.DATE_START_GREATER_THAN_DATE_END;
            animalDietManagementEntity.setDateEnd(animalDietManagementPayload.getDateEnd());
        }
        return null;
    }


        public String validateAreaManagementPayload(AreaManagementPayload areaManagementPayload, AreaManagementEntity entity) {
            if (areaRepository.findById(areaManagementPayload.getAreaId()).isEmpty())
                return Constants.NOT_FOUND;

            if (userRepository.findById(areaManagementPayload.getUserId()).isEmpty())
                return Constants.NOT_FOUND;
            if (areaManagementPayload.getDateEnd() != null) {
                if (areaManagementRepository.getAreaManagementEntityByAreaEntity(areaManagementPayload.getAreaId()) != null) {
                    if (areaManagementRepository.getAreaManagementEntityByAreaEntity(areaManagementPayload.getAreaId()).getDateEnd().getTime() > areaManagementPayload.getDateStart().getTime())
                        return Constants.AREA_OVERLAP_MANAGING_ERROR;
                }
                if (areaManagementPayload.getDateStart().getTime() >= areaManagementPayload.getDateEnd().getTime())
                    return Constants.DATE_START_GREATER_THAN_DATE_END;
                entity.setDateEnd(areaManagementPayload.getDateEnd());
            }

            return null;
        }
    public String validateUpdateAreaManagementPayload(AreaManagementPayload areaManagementPayload, AreaManagementEntity entity) {
        if (areaRepository.findById(areaManagementPayload.getAreaId()).isEmpty())
            return Constants.NOT_FOUND;

        if (userRepository.findById(areaManagementPayload.getUserId()).isEmpty())
            return Constants.NOT_FOUND;
        if (areaManagementPayload.getDateEnd() != null) {
            if (areaManagementRepository.getAreaManagementEntityByAreaEntity(areaManagementPayload.getAreaId()) != null) {
                if (entity.getAreaEntity().getAreaId() != areaManagementPayload.getAreaId()){
                    if (areaManagementRepository.getAreaManagementEntityByAreaEntity(areaManagementPayload.getAreaId()).getDateEnd().getTime() > areaManagementPayload.getDateStart().getTime())
                        return Constants.AREA_OVERLAP_MANAGING_ERROR;
                }
            }
            if (areaManagementPayload.getDateStart().getTime() >= areaManagementPayload.getDateEnd().getTime())
                return Constants.DATE_START_GREATER_THAN_DATE_END;
            entity.setDateEnd(areaManagementPayload.getDateEnd());
        }
        return null;
    }
}
