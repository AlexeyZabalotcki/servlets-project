package ru.clevertec.zabalotcki.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private Long id;
    private List<Product> products;
    private byte[] receipt;
    private DiscountCard card;
    private BigDecimal totalPrice;
}
