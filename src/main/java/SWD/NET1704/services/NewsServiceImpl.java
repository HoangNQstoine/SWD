package SWD.NET1704.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import SWD.NET1704.dtos.request.NewsPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.NewsDTO;
import SWD.NET1704.entities.NewsEntity;
import SWD.NET1704.repositories.NewsRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;

    public ResponseEntity<ApiResponse> getAllNews() {
        try {
            List<NewsEntity> newsEntityList = newsRepository.getAllByStatusTrue();
            if (newsEntityList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            List<NewsDTO> newsDTOS = newsEntityList.stream().map(entityToDTOMapper::mapNewsEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, newsDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse> getNewsById(int newsId) {
        try {
            if (newsRepository.findById(newsId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            NewsEntity newsEntity = newsRepository.findById(newsId).get();
            NewsDTO newsDTO = entityToDTOMapper.mapNewsEntityToDTO(newsEntity);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, newsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createNews(NewsPayload newsPayload) {
        try {
            NewsEntity newsEntity = dtoToEntity.mapPayloadToNewsEntity(newsPayload);
            newsEntity.setStatus(STATUS_TRUE);
            newsRepository.save(newsEntity);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, newsPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateNews(int newsId, NewsPayload newsPayload) {
        try {
            if (newsRepository.findById(newsId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            NewsEntity newsEntity = newsRepository.findById(newsId).get();
            newsEntity.setContent(newsPayload.getContent());
            newsEntity.setTitle(newsPayload.getTitle());
            newsEntity.setImage(newsPayload.getImage());
            newsEntity.setCreatedDate(newsPayload.getDateCreated());
            newsEntity.setNewsType(newsPayload.getNewsType());
            newsRepository.save(newsEntity);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, newsPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteNews(int newsId) {
        try {
            if (newsRepository.findById(newsId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            NewsEntity newsEntity = newsRepository.findById(newsId).get();
            newsEntity.setStatus(STATUS_FALSE);
            newsRepository.save(newsEntity);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
