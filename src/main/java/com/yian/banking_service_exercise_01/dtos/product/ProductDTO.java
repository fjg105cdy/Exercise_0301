package com.yian.banking_service_exercise_01.dtos.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message="name is required")
    @Min(value=1,message="name should be at least 3 characters")
    @Schema(description = "상품명", example = "iphone 16 pro")
    private String name;

    @NotBlank(message="description is required")
    @Max(value=100, message = "description must be less than 100 char")
    @Schema(description = "상품설명", example = "good")
    private String description;

    @NotBlank(message="price is required")
    @Schema(description = "상품가격", example = "140만원")
    private String price;

    private String productImg;
}
