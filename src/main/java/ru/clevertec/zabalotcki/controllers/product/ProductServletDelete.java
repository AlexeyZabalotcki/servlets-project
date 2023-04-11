package ru.clevertec.zabalotcki.controllers.product;

import ru.clevertec.zabalotcki.repository.DatabaseUtil;
import ru.clevertec.zabalotcki.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/product/delete")
public class ProductServletDelete extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseUtil.getConnection();
            this.productService = new ProductService(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error initializing product repository", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.deleteById(id);

        response.setStatus(HttpServletResponse.SC_OK);
    }

}

