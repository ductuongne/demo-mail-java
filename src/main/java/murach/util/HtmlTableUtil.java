package murach.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class HtmlTableUtil {

    public static String getHtmlTable(ResultSet results) throws SQLException {
        StringBuilder htmlTable = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlTable.append("<table>");

        // dòng tiêu đề
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            htmlTable.append("<th>").append(metaData.getColumnName(i)).append("</th>");
        }
        htmlTable.append("</tr>");

        // các dòng dữ liệu
        while (results.next()) {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlTable.append("<td>").append(results.getString(i)).append("</td>");
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        return htmlTable.toString();
    }
}
