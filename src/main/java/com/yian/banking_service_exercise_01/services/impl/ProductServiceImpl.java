package com.yian.banking_service_exercise_01.services.impl;

import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductDTO;
import com.yian.banking_service_exercise_01.dtos.product.ProductResponseDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import com.yian.banking_service_exercise_01.entities.Product;
import com.yian.banking_service_exercise_01.exceptions.ResourceNotFoundException;
import com.yian.banking_service_exercise_01.mappers.ProductMapper;
import com.yian.banking_service_exercise_01.repositories.ProductRepository;
import com.yian.banking_service_exercise_01.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    public final ProductRepository productRepository;
    private List<UserResponseDTO> ProductResponseDTO;

    @Override
    public ProductResponseDTO createProduct(ProductDTO productDTO){
        if (productRepository.existsByName(productDTO.getName())) {
            throw new RuntimeException("Product with name"+productDTO.getName()+" already exists");

        }
        Product product = ProductMapper.mapToProductEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductResponseDTO(savedProduct);
    }


    @Override
    public ProductResponseDTO createProduct(ProductResponseDTO productResponseDTO) {
        return null;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
        return ProductMapper.mapToProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductDTO productDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id));

        if (!product.getName().equals(productDTO.getName())&& productRepository.existsByName(productDTO.getName())) {
            throw new RuntimeException("Product with name"+productDTO.getName()+" already exists");
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setProductimg(productDTO.getProductImg());

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.mapToProductResponseDTO(updatedProduct);
    }

    @Override
    public String deleteProduct(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
        productRepository.delete(product);

        return "deleted";

    }

    @Override
    public PageResponseDTO getProductsWithPagination(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir,
            String searchKeyword
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);


        if (searchKeyword == null||searchKeyword.trim().isEmpty()){
            products = productRepository.findAll(pageable);
        } else{
            products=productRepository.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<ProductResponseDTO> productResponseDTOS = products
                .getContent()
                .stream()
                .map(ProductMapper::mapToProductResponseDTO)
                .collect(Collectors.toList());

        PageResponseDTO build = PageResponseDTO.builder()
                .body(ProductResponseDTO)
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .hasPreviousPage(products.hasPrevious())
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .build();
        return build;

    }
}
