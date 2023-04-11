package ru.clevertec.zabalotcki.controllers.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.zabalotcki.model.DiscountCard;
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

@WebServlet("/card/add")
public class CardServletSaveUpdate extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DiscountCard card = mapper.readValue(request.getReader(), DiscountCard.class);

        String idParam = request.getParameter("id");
        if (idParam != null) {
            card.setId(Long.parseLong(idParam));
            try {
                card.setId(Long.parseLong(idParam));
                cardService.update(card);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                cardService.add(card);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
