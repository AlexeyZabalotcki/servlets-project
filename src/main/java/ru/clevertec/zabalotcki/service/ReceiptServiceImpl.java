package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.ProductQuantityDto;
import ru.clevertec.zabalotcki.model.Product;
import ru.clevertec.zabalotcki.model.Receipt;
import ru.clevertec.zabalotcki.repository.receipt.ReceiptRepository;
import ru.clevertec.zabalotcki.repository.receipt.ReceiptRepositoryImpl;
import ru.clevertec.zabalotcki.service.interfce.ReceiptService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final CalculateUtils calculateUtils;
    private final PdfUtils pdfUtils;

    public ReceiptServiceImpl(Connection connection) {
        this.receiptRepository = new ReceiptRepositoryImpl(connection);
        this.calculateUtils = new CalculateUtils();
        this.pdfUtils = new PdfUtils();
    }

    @Override
    public byte[] getReceipt(Long id) {
        return receiptRepository.findById(id);
    }

    @Override
    public Receipt addReceipt(Receipt receipt) throws Exception {
        Receipt receiptDto = fillReceipt(receipt);
        return receiptRepository.save(receiptDto);
    }

    private Receipt fillReceipt(Receipt receipt) throws Exception {
        List<Product> products = receipt.getProducts();

        Map<Product, Long> productsToQuantity = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<ProductQuantityDto> dtos = toQuantityDto(productsToQuantity);

        BigDecimal price = calculateUtils.calculatePrice(dtos, receipt);

        byte[] pdfReceipt = pdfUtils.getPdfDoc(productsToQuantity, price);

        return Receipt.builder()
                .receipt(pdfReceipt)
                .build();
    }

    private List<ProductQuantityDto> toQuantityDto(Map<Product, Long> productsToQuantity) {
        return productsToQuantity.entrySet().stream()
                .map(p -> ProductQuantityDto.builder()
                        .id(p.getKey().getId())
                        .title(p.getKey().getTitle())
                        .price(p.getKey().getPrice().multiply(BigDecimal.valueOf(p.getValue())))
                        .quantity(Math.toIntExact(p.getValue()))
                        .discount(p.getKey().isDiscount())
                        .build())
                .collect(Collectors.toList());
    }
}
