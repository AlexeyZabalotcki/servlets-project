package ru.clevertec.zabalotcki.repository.receipt;

import ru.clevertec.zabalotcki.model.Receipt;

public interface ReceiptRepository {
    Receipt save(Receipt receipt);

    byte[] findById(Long id);
}
