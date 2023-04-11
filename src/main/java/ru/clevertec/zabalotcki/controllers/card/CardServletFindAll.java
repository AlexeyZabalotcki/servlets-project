package ru.clevertec.zabalotcki.controllers.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.zabalotcki.dto.DiscountCardDto;
import ru.clevertec.zabalotcki.repository.DatabaseUtil;
import ru.clevertec.zabalotcki.service.DiscountCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cards")
public class CardServletFindAll extends HttpServlet {

    private DiscountCardService cardService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseUtil.getConnection();
            this.cardService = new DiscountCardService(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error initializing card repository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int offset = Integer.parseInt(request.getParameter("offset"));

        List<DiscountCardDto> cards = cardService.getAll(limit, offset);

        ObjectMapper mapper = new ObjectMapper();
        String productsJson = mapper.writeValueAsString(cards);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(productsJson);
    }
}
