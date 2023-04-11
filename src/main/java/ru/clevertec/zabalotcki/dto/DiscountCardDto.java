package ru.clevertec.zabalotcki.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountCardDto {
    private Long id;
    private Integer number;
    private boolean discount;
}
