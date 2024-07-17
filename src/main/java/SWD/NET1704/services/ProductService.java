package SWD.NET1704.services;


import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.ProductPayload;
import SWD.NET1704.dtos.response.ApiResponse;
public interface ProductService {
   ResponseEntity<ApiResponse> getAllProduct();

    ResponseEntity<ApiResponse> getProductById(int productId) throws Exception;

    ResponseEntity<ApiResponse> createProduct(ProductPayload productPayload);

    ResponseEntity<ApiResponse> updateProduct(int productId, ProductPayload productPayload);

    ResponseEntity<ApiResponse> deleteProduct(int productId);
}