package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.FeedbackPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.FeedbackDTO;
import SWD.NET1704.entities.FeedbackEntity;
import SWD.NET1704.entities.ProductEntity;
import SWD.NET1704.entities.UserEntity;
import SWD.NET1704.repositories.FeedbackRepository;
import SWD.NET1704.repositories.ProductRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;

import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;
    private final HelperUtil helperUtil;

    @Override
    public ResponseEntity<ApiResponse> createNewFeedback(FeedbackPayload feedbackPayload) {
        try {
            if (helperUtil.validateFeedbackPayload(feedbackPayload) != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, helperUtil.validateFeedbackPayload(feedbackPayload)));
            FeedbackEntity feedbackEntity = dtoToEntity.mapPayloadToFeedbackEntity(feedbackPayload);
            ProductEntity productEntity = productRepository.getOne(feedbackPayload.getProductId());
            UserEntity userEntity = userRepository.getOne(feedbackPayload.getUserId());
            feedbackEntity.setProductEntity(productEntity);
            feedbackEntity.setUserEntity(userEntity);
            feedbackEntity.setStatus(STATUS_TRUE);
            feedbackRepository.save(feedbackEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getAllFeedback() {
        try {
            List<FeedbackEntity> feedbackEntityList = feedbackRepository.findAllFeedback();
            if (feedbackEntityList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            List<FeedbackDTO> feedbackDTOList = feedbackEntityList.stream().map(entityToDTOMapper::mapFeedbackEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getFeedbackByProduct(int productId) {
        try {
            List<FeedbackEntity> feedbackEntityList = feedbackRepository.getFeedbackByProduct(productId);
            if (feedbackEntityList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            List<FeedbackDTO> feedbackDTOList = feedbackEntityList.stream().map(entityToDTOMapper::mapFeedbackEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getFeedbackByUser(int userId) {
        try {
            List<FeedbackEntity> feedbackEntityList = feedbackRepository.getFeedbackByUser(userId);
            if (feedbackEntityList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            List<FeedbackDTO> feedbackDTOList = feedbackEntityList.stream().map(entityToDTOMapper::mapFeedbackEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateFeedback(int feedbackId, FeedbackPayload feedbackPayload) {
        try {
            if (feedbackRepository.findById(feedbackId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            if (helperUtil.validateFeedbackPayload(feedbackPayload) != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, helperUtil.validateFeedbackPayload(feedbackPayload)));
            FeedbackEntity feedbackEntity = feedbackRepository.getOne(feedbackId);
            feedbackEntity.setTitle(feedbackPayload.getTitle());
            feedbackEntity.setComment(feedbackPayload.getContent());
            feedbackEntity.setRating(feedbackPayload.getRating());
            ProductEntity productEntity = productRepository.getOne(feedbackPayload.getProductId());
            UserEntity userEntity = userRepository.getOne(feedbackPayload.getUserId());
            feedbackEntity.setProductEntity(productEntity);
            feedbackEntity.setUserEntity(userEntity);
            feedbackRepository.save(feedbackEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getFeedbackById(int feedbackId) {
        try {
            if (feedbackRepository.findById(feedbackId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            FeedbackEntity feedbackEntity = feedbackRepository.getOne(feedbackId);
            FeedbackDTO feedbackDTO = entityToDTOMapper.mapFeedbackEntityToDTO(feedbackEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, feedbackDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteFeedback(int feedbackId) {
        try {
            if (feedbackRepository.findById(feedbackId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            FeedbackEntity feedbackEntity = feedbackRepository.getOne(feedbackId);
            feedbackEntity.setStatus(STATUS_FALSE);
            feedbackRepository.save(feedbackEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
