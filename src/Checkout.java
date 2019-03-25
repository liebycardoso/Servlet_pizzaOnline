import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {

 	private static final long serialVersionUID = 288313080903387188L;

 	public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResultSet rs;
		response.setContentType("text/html");
		Connection conn = null;
		String sql = ("SELECT CI.TOTAL_QUANTITY as quantity, "+
				"PR.PRODUCT_NAME AS product,  "+
				"PR.PRICE as price  "+
				"FROM   "+
				"CART_ITEM AS CI,  "+
				"PRODUCT AS PR  "+
				"WHERE  "+
				"PR.PRODUCT_ID = CI.PRODUCT_ID");

		try {
			conn = DbConnection.getDbConnection("mysql");
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    ArrayList<String> pa = new ArrayList<String>();
		    List<Cart> results = new ArrayList<Cart>();
			rs = SQLHandler.selectRecord(sql, pa, conn);
			
			while( rs.next() ) { 
				Cart cart = new Cart();
				cart.setProduct(rs.getString("product"));
				cart.setQuantity(rs.getInt("quantity"));
				cart.setprice(rs.getDouble("price"));
		        results.add(cart);
		    }
			
            request.setAttribute("carts", results); // Will be available as ${products} in JSP
            request.getRequestDispatcher("listCart.jsp").include(request, response);

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
