

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlaceOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		ResultSet rs;
		String errorMessage = null;
		response.setContentType("text/html");
		Connection conn = null;
				
		String insertOrder = ("INSERT INTO ORDERS ( "+
								"customer_id, payment_id, order_date, total_discount, "+
								"total_value, tax_rate, tax_value) "+
								"SELECT AU.CUSTOMER_ID, 'VISA', CURDATE(), "+
								"0, 0, 1.0, 0 "+
								"FROM ACTIVE_USER AU "+
								"WHERE "+
								"AU.SESSION_ID = 1;");
		
		String insertOrderItem = ("INSERT INTO ORDER_ITEM ( "+
								    "PRODUCT_ID, ORDER_ID, TOTAL_QUANTITY, TOTAL_VALUE) "+ 
									"SELECT CI.PRODUCT_ID, OD.ORDER_ID, CI.TOTAL_QUANTITY, 	0 "+
								    "FROM  CART CA, CART_ITEM CI, ORDERS OD "+
								    "WHERE "+
								    "CA.CART_ID = CI.CART_ID AND "+
								    "CA.SESSION_ID = OD.SESSION_ID AND "+
								    "CA.SESSION_ID = 1; ");


		

		try {
			conn = DbConnection.getDbConnection("mysql");
			response.setContentType("text/html");
		    //PrintWriter out = response.getWriter();
		    ArrayList<String> pa = new ArrayList<String>();
		    //List<Cart> results = new ArrayList<Cart>();
		    errorMessage = "Order Created " + SQLHandler.alterRecord(insertOrder, pa, conn);
		    
		    errorMessage += "Payment processed " + SQLHandler.alterRecord(insertOrderItem, pa, conn);
		    
		    errorMessage += " Your pizza will be delivered soon.";

            
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			errorMessage = e.getMessage();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("errorMessage", errorMessage); // Will be available as ${products} in JSP
            request.getRequestDispatcher("OrderConfirmation.jsp").include(request, response);
		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
