import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


	public class SQLHandler {
		
		public static String alterRecord(String sql, ArrayList<String> params, Connection conn){
			PreparedStatement stmt = null;
			String msg;
			try {
				stmt = conn.prepareStatement(sql);
				
				int i = 1;
				for (String item: params){
					stmt.setString(i, item);
					i++;
				}
				
							
				stmt.executeUpdate();
				msg=" Successfully. ";

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				msg=e.getMessage();
			} finally{
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return msg;
		}

	
		public static ResultSet selectRecord(String sql, ArrayList<String> params, Connection conn){
						
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt  = conn.prepareStatement(sql);
								
				int i = 1;
				for (String item: params){
					stmt.setString(i, item);
					i++;
				}
				
				rs = stmt.executeQuery();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//return rslt;
			return rs;
			
		}

	}
