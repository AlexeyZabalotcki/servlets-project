package ru.clevertec.zabalotcki.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {
    private Long id;
    private Integer number;
    private boolean discount;
}
