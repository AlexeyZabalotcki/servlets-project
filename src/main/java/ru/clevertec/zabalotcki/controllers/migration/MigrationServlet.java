package ru.clevertec.zabalotcki.controllers.migration;

import org.flywaydb.core.Flyway;
import ru.clevertec.zabalotcki.config.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/migration")
public class MigrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AppConfig config = AppConfig.getInstance();

        Flyway flyway = Flyway.configure()
                .dataSource(config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPassword())
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();

        response.getWriter().println("Migration have finished successfully");
    }
}
