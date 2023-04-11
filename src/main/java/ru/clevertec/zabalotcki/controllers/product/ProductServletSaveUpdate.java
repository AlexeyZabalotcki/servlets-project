package ru.clevertec.zabalotcki.controllers.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.zabalotcki.model.Product;
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

@WebServlet("/product/add")
public class ProductServletSaveUpdate extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(request.getReader(), Product.class);

        String idParam = request.getParameter("id");
        if (idParam != null) {
            product.setId(Long.parseLong(idParam));
            try {
                product.setId(Long.parseLong(idParam));
                productService.update(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                productService.add(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
