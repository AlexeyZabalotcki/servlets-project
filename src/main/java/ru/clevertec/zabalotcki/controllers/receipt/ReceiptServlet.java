package ru.clevertec.zabalotcki.controllers.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.zabalotcki.model.Receipt;
import ru.clevertec.zabalotcki.repository.DatabaseUtil;
import ru.clevertec.zabalotcki.service.ReceiptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {

    private ReceiptServiceImpl receiptServiceImpl;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseUtil.getConnection();
            this.receiptServiceImpl = new ReceiptServiceImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error initializing product repository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Long id = Long.parseLong(idParam);
        byte[] receipt = receiptServiceImpl.getReceipt(id);
        if (receipt == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"receipt.pdf\"");
        response.getOutputStream().write(receipt);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Receipt receipt = new ObjectMapper().readValue(body, Receipt.class);
        try {
            receipt = receiptServiceImpl.addReceipt(receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(receipt));
    }
}
