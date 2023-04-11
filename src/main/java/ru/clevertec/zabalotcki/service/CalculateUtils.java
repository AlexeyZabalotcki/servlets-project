package ru.clevertec.zabalotcki.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.zabalotcki.dto.ProductQuantityDto;
import ru.clevertec.zabalotcki.model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequiredArgsConstructor
public class CalculateUtils {

    public BigDecimal calculatePrice(List<ProductQuantityDto> dtos, Receipt receipt) {
        BigDecimal discountedPrice = getDiscountedPrice(dtos);
        BigDecimal nonDiscountedPrice = getNonDiscountedPrice(dtos);
        BigDecimal totalDiscountedPrice = discountedPrice.add(nonDiscountedPrice);
        BigDecimal fullPriceTotal = getFullPriceTotal(dtos);
        BigDecimal taxes = getTax(totalDiscountedPrice);

        return Objects.nonNull(receipt.getCard())
                ? totalDiscountedPrice.add(taxes)
                : fullPriceTotal.add(taxes);
    }

    private BigDecimal getTax(BigDecimal priceWithoutDiscCard) {
        return priceWithoutDiscCard.multiply(BigDecimal.valueOf(17))
                .divide(BigDecimal.valueOf(100), RoundingMode.valueOf(2));
    }

    private BigDecimal getFullPriceTotal(List<ProductQuantityDto> dtos) {
        return dtos.stream().map(ProductQuantityDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getNonDiscountedPrice(List<ProductQuantityDto> dtos) {
        List<ProductQuantityDto> allProducts = new ArrayList<>(dtos);
        List<ProductQuantityDto> discountedProducts = dtos.stream()
                .filter(ProductQuantityDto::isDiscount)
                .collect(Collectors.toList());

        allProducts.removeAll(discountedProducts);

        return dtos.stream()
                .map(ProductQuantityDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getDiscountedPrice(List<ProductQuantityDto> dtos) {
        List<BigDecimal> discounts = dtos.stream()
                .filter(ProductQuantityDto::isDiscount)
                .map(this::calculateDiscount)
                .collect(Collectors.toList());

        List<BigDecimal> prices = dtos.stream()
                .filter(ProductQuantityDto::isDiscount)
                .map(ProductQuantityDto::getPrice)
                .collect(Collectors.toList());

        return IntStream.range(0, discounts.size())
                .mapToObj(i -> prices.get(i).subtract(discounts.get(i)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateDiscount(ProductQuantityDto product) {
        BigDecimal price = product.getPrice();
        return product.isDiscount() ? price.multiply(BigDecimal.valueOf(10))
                .divide(BigDecimal.valueOf(100), RoundingMode.valueOf(2)) : price;
    }
}
