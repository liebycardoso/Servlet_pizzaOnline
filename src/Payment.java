

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

/**
 * Servlet implementation class Payment
 */
@WebServlet("/Payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Payment() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		ResultSet rs;
		response.setContentType("text/html");
		Connection conn = null;
		String sql = ("SELECT CT.CUSTOMER_ID customerID, CT.FIRST_NAME firstName, CT.LAST_NAME lastName, "+
		              "CT.EMAIL email, CT.CREDIT_CARD_NUMBER creditCardNumber, "+
					  "CT.SECURITY_CODE securityCode, CT.EXPIRATION_DATE expirationDate, CT.SESSION_ID sessionID "+
					  "FROM ACTIVE_USER CT " +
					  "WHERE CT.SESSION_ID = 1");

		try {
			conn = DbConnection.getDbConnection("mysql");
			response.setContentType("text/html");
		    //PrintWriter out = response.getWriter();
		    ArrayList<String> params = new ArrayList<String>();
		    List<CustomerView> results = new ArrayList<CustomerView>();
			rs = SQLHandler.selectRecord(sql, params, conn);
			
			while( rs.next() ) { 
				CustomerView paymentInfo = new CustomerView();
				paymentInfo.setFirstName(rs.getString("firstName"));
				paymentInfo.setLastName(rs.getString("lastName"));
				paymentInfo.setCreditCardNumber(rs.getInt("creditCardNumber"));
				paymentInfo.setSecurityCode(rs.getInt("securityCode"));
				paymentInfo.setExpirationDate(rs.getInt("expirationDate"));
		        results.add(paymentInfo);
		    }
			
            request.setAttribute("paymentInfo", results); // Will be available as ${products} in JSP
            request.getRequestDispatcher("listPayment.jsp").include(request, response);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
