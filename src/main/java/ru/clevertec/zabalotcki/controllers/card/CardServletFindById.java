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

@WebServlet("/card")
public class CardServletFindById extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            long id = Long.parseLong(idStr);
            DiscountCardDto card = cardService.findById(id);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(card);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            throw new ServletException("Error getting card", e);
        }
    }
}
