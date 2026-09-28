package murach.controller;

import java.io.IOException;

import murach.dao.SqlDAO;
import murach.dao.SqlDAOImpl;
import murach.exception.DAOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sqlGateway")
public class SqlGatewayServlet extends HttpServlet {

    private final SqlDAO sqlDAO = new SqlDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult;

        try {
            sqlResult = sqlDAO.executeSql(sqlStatement);
        } catch (DAOException e) {
            sqlResult = "<p>Lỗi khi thực thi câu lệnh SQL: "
                    + e.getMessage() + "</p>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/sqlGateway.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/sqlGateway.jsp");
        dispatcher.forward(request, response);
    }
}
