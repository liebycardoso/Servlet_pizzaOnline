import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PrintResultSet {

	public static void PrintPlainTable(ResultSet rs){
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
			    }
			    System.out.println("");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static PrintWriter printHTML(ResultSet rs)  throws Exception {
		
		PrintWriter out = null;
		int rowCount = 0;
		
		out.println("<P ALIGN='center'><TABLE BORDER=1>");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		 // table header
		out.println("<TR>");
		for (int i = 0; i < columnCount; i++) {
			out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
		}
		out.println("</TR>");
		// the data
		while (rs.next()) {
			rowCount++;
			out.println("<TR>");
			for (int i = 0; i < columnCount; i++) {
			out.println("<TD>" + rs.getString(i + 1) + "</TD>");
			}
			out.println("</TR>");
		}
		out.println("</TABLE></P>");
		return out;
		}
		}
