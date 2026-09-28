package murach.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import murach.util.HtmlTableUtil;
import murach.exception.DAOException;
import murach.util.ConnectionPool;
import murach.util.DBUtil;

public class SqlDAOImpl implements SqlDAO {

    @Override
    public String executeSql(String sqlStatement) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        sqlStatement = sqlStatement.trim();
        String sqlType = "";
        if (sqlStatement.length() >= 6) {
            sqlType = sqlStatement.substring(0, 6);
        }

        try {
            connection = pool.getConnection();
            statement = connection.createStatement();

            if (sqlType.equalsIgnoreCase("select")) {
                resultSet = statement.executeQuery(sqlStatement);
                String htmlTable = HtmlTableUtil.getHtmlTable(resultSet);
                return htmlTable;
            } else {
                int rowsAffected = statement.executeUpdate(sqlStatement);
                if (rowsAffected == 0) {
                    return "<p>Câu lệnh đã thực thi thành công.</p>";
                } else {
                    return "<p>Câu lệnh đã thực thi thành công.<br>"
                            + rowsAffected + " dòng bị ảnh hưởng.</p>";
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Lỗi khi thực thi câu lệnh SQL: " + e.getMessage(), e);
        } finally {
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            pool.freeConnection(connection);
        }
    }
}
