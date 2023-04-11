package ru.clevertec.zabalotcki.service.interfce;

import ru.clevertec.zabalotcki.model.Receipt;

public interface ReceiptService {
    byte[] getReceipt(Long id);

    Receipt addReceipt(Receipt receipt) throws Exception;
}
