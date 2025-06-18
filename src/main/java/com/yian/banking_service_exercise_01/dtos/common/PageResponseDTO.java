package com.yian.banking_service_exercise_01.dtos.common;

import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO {
    private List<UserResponseDTO> body;
    private long totalElements;
    private int totalPages;
    private int pageNo;
    private int pageSize;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
}
