package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.FeedbackPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/feedback")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/getAllFeedback")
    public ResponseEntity<ApiResponse> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/getFeedbackById{id}")
    public ResponseEntity<ApiResponse> getFeedbackById(@PathVariable("id") int feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @GetMapping("/getFeedbackByProduct{id}")
    public ResponseEntity<ApiResponse> getFeedbackByProduct(@PathVariable("id") int productId) {
        return feedbackService.getFeedbackByProduct(productId);
    }

    @GetMapping("/getFeedbackByUser{id}")
    public ResponseEntity<ApiResponse> getFeedbackByUser(@PathVariable("id") int userId) {
        return feedbackService.getFeedbackByUser(userId);
    }

    @PostMapping("/createNewFeedback")
    public ResponseEntity<ApiResponse> createNewFeedback(@RequestBody FeedbackPayload feedbackPayload) {
        return feedbackService.createNewFeedback(feedbackPayload);
    }

    @PutMapping("/updateFeedback/{id}")
    public ResponseEntity<ApiResponse> updateFeedback(@PathVariable("id") int feedbackId, @RequestBody FeedbackPayload feedbackPayload) {
        return feedbackService.updateFeedback(feedbackId, feedbackPayload);
    }

    @DeleteMapping("/deleteFeedback/{id}")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable("id") int feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }
}
