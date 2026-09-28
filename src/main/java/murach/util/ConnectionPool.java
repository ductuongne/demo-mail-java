package murach.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

    private static ConnectionPool pool;
    private DataSource dataSource;


    private ConnectionPool() {
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/murach");
        } catch (NamingException e) {
            throw new RuntimeException(
                    "Không tìm thấy DataSource 'jdbc/murach'. "
                            + "Kiểm tra lại META-INF/context.xml.", e);
        }
    }

    //đảm bảo chỉ có một đối tượng ConnectionPool duy nhất trong toàn bộ ứng dụng.
    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //Đóng kết nối sau khi dùng xong
    public void freeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close(); // trả kết nối về pool, không thực sự đóng
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
