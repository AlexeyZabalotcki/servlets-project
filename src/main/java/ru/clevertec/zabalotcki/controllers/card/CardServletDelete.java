package ru.clevertec.zabalotcki.controllers.card;

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

@WebServlet("/card/delete")
public class CardServletDelete extends HttpServlet {

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        cardService.deleteById(id);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

