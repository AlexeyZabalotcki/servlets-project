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
import java.util.List;

@WebServlet("/products")
public class ProductServletFindAll extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int offset = Integer.parseInt(request.getParameter("offset"));

        List<ProductDto> products = productService.getAll(limit, offset);

        ObjectMapper mapper = new ObjectMapper();
        String productsJson = mapper.writeValueAsString(products);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(productsJson);
    }
}
