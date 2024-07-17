package SWD.NET1704.controllers;


import SWD.NET1704.dtos.request.NewsPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/zoo-server/api/v1/new")
@AllArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/getAllNews")
    public ResponseEntity<ApiResponse> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/getNewsById{id}")
    public  ResponseEntity<ApiResponse> getNewsById(@PathVariable("id") int newsId) throws Exception {
        return newsService.getNewsById(newsId);
    }

    @PostMapping("/createNew")
    public  ResponseEntity<ApiResponse> createNew(@RequestBody NewsPayload newsPayload) {
        return newsService.createNews(newsPayload);
    }

    @PutMapping("/updateNew/{id}")
    public  ResponseEntity<ApiResponse> updateNew(@PathVariable("id") int newsId, @RequestBody NewsPayload newsPayload) {
        return newsService.updateNews(newsId, newsPayload);
    }

    @DeleteMapping("/deleteNew/{id}")
    public  ResponseEntity<ApiResponse> deleteNew(@PathVariable("id") int newsId) {
        return newsService.deleteNews(newsId);
    }
}