package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.DiscountCardDto;
import ru.clevertec.zabalotcki.model.DiscountCard;
import ru.clevertec.zabalotcki.repository.Repository;
import ru.clevertec.zabalotcki.repository.card.DiscountCardRepository;
import ru.clevertec.zabalotcki.service.interfce.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class DiscountCardService implements Service<DiscountCardDto, DiscountCard> {

    private final Repository<DiscountCard> cardRepository;

    public DiscountCardService(Connection connection) {
        cardRepository = new DiscountCardRepository(connection);
    }

    public List<DiscountCardDto> getAll(int limit, int offset) {
        List<DiscountCard> products = cardRepository.findAll(limit, offset);
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DiscountCardDto findById(Long id) throws SQLException {
        DiscountCard product = cardRepository.findById(id);
        return toDto(product);
    }

    public long add(DiscountCard entity) throws SQLException {
        return cardRepository.save(entity);
    }

    public long update(DiscountCard entity) throws SQLException {
        return cardRepository.update(entity);
    }

    public void deleteById(Long id) {
        cardRepository.delete(id);
    }

    private DiscountCard toEntity(DiscountCardDto dto) {
        return DiscountCard.builder()
                .id(dto.getId())
                .number(dto.getNumber())
                .discount(dto.isDiscount())
                .build();
    }

    private DiscountCardDto toDto(DiscountCard card) {
        return DiscountCardDto.builder()
                .id(card.getId())
                .number(card.getNumber())
                .discount(card.isDiscount())
                .build();
    }
}
