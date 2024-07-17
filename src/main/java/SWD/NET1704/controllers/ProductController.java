package SWD.NET1704.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SWD.NET1704.dtos.request.ProductPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.ProductService;

@RestController
@RequestMapping("/zoo-server/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAllProduct")
    public   ResponseEntity<ApiResponse> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") int productId) throws Exception {
        return productService.getProductById(productId);
    }

    @PostMapping("/createProduct")
    public   ResponseEntity<ApiResponse> createProduct(@RequestBody ProductPayload productPayload) {
        return productService.createProduct(productPayload);
    }

    @PutMapping("/updateProduct/{id}")
    public   ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") int productId, @RequestBody ProductPayload productPayload) {
        return productService.updateProduct(productId, productPayload);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public   ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") int productId) {
        return productService.deleteProduct(productId);
    }
}
