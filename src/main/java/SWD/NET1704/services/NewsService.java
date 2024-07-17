package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.NewsPayload;
import SWD.NET1704.dtos.response.ApiResponse;

public interface NewsService {
    ResponseEntity<ApiResponse> getAllNews();

    ResponseEntity<ApiResponse> getNewsById(int newsId) throws Exception;

    ResponseEntity<ApiResponse> createNews(NewsPayload newsPayload);

    ResponseEntity<ApiResponse> updateNews(int newsId, NewsPayload newsPayload);

    ResponseEntity<ApiResponse> deleteNews(int newsId);
}
