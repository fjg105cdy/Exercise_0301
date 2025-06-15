package com.yian.banking_service_exercise_01.entities;

import com.yian.banking_service_exercise_01.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString
@Table(name="ROLES")
public class Role extends AbstractEntity {
    private String name;

}
