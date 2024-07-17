package SWD.NET1704.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import SWD.NET1704.dtos.request.ProductPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.ProductDTO;
import SWD.NET1704.entities.ProductEntity;
import SWD.NET1704.repositories.ProductRepository;
import SWD.NET1704.utils.Constants;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;

import static SWD.NET1704.utils.Constants.STATUS_FALSE;
import static SWD.NET1704.utils.Constants.STATUS_TRUE;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;

    @Override
    public ResponseEntity<ApiResponse> getAllProduct() {
        try {
            List<ProductEntity> productEntityList = productRepository.getAllProductWithStatusIsTrue();
            if (productEntityList == null)
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));

            List<ProductDTO> productDTOS =  productEntityList.stream().map(entityToDTOMapper::mapProductEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, productDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getProductById(int productId)  {
        try {
            if (productRepository.findById(productId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));

            ProductEntity productEntity = productRepository.findById(productId).get();
            ProductDTO productDTO = entityToDTOMapper.mapProductEntityToDTO(productEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createProduct(ProductPayload productPayload) {
        try {
            ProductEntity productEntity = dtoToEntity.mapPayloadToProductEntity(productPayload);
            productEntity.setStatus(STATUS_TRUE);
            productRepository.save(productEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, productPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateProduct(int productId, ProductPayload productPayload) {
        try {
            if (productRepository.findById(productId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            ProductEntity productEntity = productRepository.findById(productId).get();
            productEntity.setProductName(productPayload.getProductName());
            productEntity.setPrice(productPayload.getPrice());
            productEntity.setQuantity(productPayload.getQuantity());
            productEntity.setDescription(productPayload.getDescription());
            productEntity.setImage(productPayload.getImage());
            productRepository.save(productEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, productPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteProduct(int productId) {
        try {
            if (productRepository.findById(productId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            ProductEntity productEntity = productRepository.getOne(productId);
            productEntity.setStatus(STATUS_FALSE);
            productRepository.save(productEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}