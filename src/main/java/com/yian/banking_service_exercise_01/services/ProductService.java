package com.yian.banking_service_exercise_01.services;

import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductResponseDTO productResponseDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(String id);
    ProductResponseDTO updateProduct(String id, ProductDTO productDTO);
    String deleteProduct(String id);
    PageResponseDTO getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir, String searchKeyword);

    ProductResponseDTO createProduct(ProductDTO productDTO);
}
