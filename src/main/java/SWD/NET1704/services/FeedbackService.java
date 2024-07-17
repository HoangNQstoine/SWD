package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.FeedbackPayload;
import SWD.NET1704.dtos.response.ApiResponse;


public interface FeedbackService {
    ResponseEntity<ApiResponse> createNewFeedback(FeedbackPayload feedbackPayload);

    ResponseEntity<ApiResponse> getAllFeedback();

    ResponseEntity<ApiResponse> getFeedbackByProduct(int productId);

    ResponseEntity<ApiResponse> getFeedbackByUser(int userId);

    ResponseEntity<ApiResponse> updateFeedback(int feedbackId, FeedbackPayload feedbackPayload);

    ResponseEntity<ApiResponse> getFeedbackById(int feedbackId);

    ResponseEntity<ApiResponse> deleteFeedback(int feedbackId);
}
