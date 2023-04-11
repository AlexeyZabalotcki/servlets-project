package ru.clevertec.zabalotcki.controllers.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.zabalotcki.dto.ProductDto;
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

@WebServlet("/product")
public class ProductServletFindById extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            long id = Long.parseLong(idStr);
            ProductDto product = productService.findById(id);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            throw new ServletException("Error getting product", e);
        }
    }
}
