package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.common.ApiResponseDTO;
import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductResponseDTO;
import com.yian.banking_service_exercise_01.entities.Product;
import com.yian.banking_service_exercise_01.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> create(@RequestBody ProductDTO productDTO) {
        ProductResponseDTO savedProduct = productService.createProduct(productDTO);
//        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        ApiResponseDTO<ProductResponseDTO> response = ApiResponseDTO.<ProductResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create product")
                .data(savedProduct)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();

        ApiResponseDTO<List<ProductResponseDTO>> response = ApiResponseDTO.<List<ProductResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully got all products")
                .data(products)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProduct(@PathVariable String id) {
        ProductResponseDTO product = productService.getProductById(id);

        ApiResponseDTO<ProductResponseDTO> response = ApiResponseDTO.<ProductResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully derived product")
                .data(product)
                .build();
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);
        ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("deleted product")
                .data("deleted")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> updateProduct(
            @PathVariable String id,
            @RequestBody ProductDTO productDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productDTO);

        ApiResponseDTO<ProductResponseDTO> response = ApiResponseDTO.<ProductResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("updated product")
                .data(updatedProduct)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagination")
    public ResponseEntity<ApiResponseDTO<PageResponseDTO>> getProductsWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String searchKeyword


    ){
        PageResponseDTO pageResponseDTO = productService.getProductsWithPagination(pageNo, pageSize, sortBy, sortDir,searchKeyword);
        ApiResponseDTO<PageResponseDTO> response = ApiResponseDTO.<PageResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("successful products data")
                .data(pageResponseDTO)
                .build();

        return ResponseEntity.ok(response);
    }
}
