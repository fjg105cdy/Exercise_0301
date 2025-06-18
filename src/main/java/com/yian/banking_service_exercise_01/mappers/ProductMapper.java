package com.yian.banking_service_exercise_01.mappers;

import com.yian.banking_service_exercise_01.dtos.product.ProductDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductResponseDTO;
import com.yian.banking_service_exercise_01.entities.Product;

public class ProductMapper {
    //DTO -> Entity
    public static Product mapToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setProductimg(productDTO.getProductImg());
        return product;
    }
    //entity -> DTO
    public static ProductResponseDTO mapToProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setProductImg(product.getProductimg());
        return productResponseDTO;
    }

}
