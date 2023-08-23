package com.cb.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class servlet extends HttpServlet{
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	public void init() throws ServletException {
		String url="jdbc:mysql://localhost:3306/placement";
		String usn="root";
		String pwd="sjY@2003";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,usn,pwd);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String query="Select *from student_details where username=? and password=?";
		PrintWriter pen=resp.getWriter();
		try {
			ps=con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			rs=ps.executeQuery();
			if(rs.next()) {
				pen.println("<!DOCTYPE html>\r\n"
				        + "<html>\r\n"
						+ "<head>\r\n"
						+ "<meta charset=\"UTF-8\">\r\n"
						+ "<title>Profile</title>\r\n"
						+ "<style>\r\n"
						+ "table {\r\n"
						+ "  border-collapse: collapse;\r\n"
						+ "  width: 50%;\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "th, td {\r\n"
						+ "  text-align: left;\r\n"
						+ "  padding: 8px;\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "tr:nth-child(even){background-color: #f2f2f2}\r\n"
						+ "\r\n"
						+ "th {\r\n"
						+ "  background-color: #04AA6D;\r\n"
						+ "  color: white;\r\n"
						+ "}\r\n"
						+ "table, th, td {\r\n"
						+ "  border: 1px solid black;\r\n"
						+ "  border-collapse: collapse;\r\n"
						+ "}"
						+ "</style>"
						+ "</head>\r\n"
						+ "<body>"
						+ "<table>"
						+ "<tr>"
						+ "<th>S.No</th>"
						+ "<th>Name</th>"
						+ "<th>Username</th>"
						+ "<th>Password</th>"
						+ "<th>10th_Mark</th>"
						+ "<th>12th_Mark</th>"
						+ "<th>CGPA</th>"
						+ "</tr>");
					int s=rs.getInt(1);
					String name=rs.getString(2);
					String user_name=rs.getString(3);
					int pass_word=rs.getInt(4);
					int tenth_mark=rs.getInt(5);
					int twelveth_mark=rs.getInt(6);
					int cgpa=rs.getInt(7);
					pen.println("<tr>"
							+ "<td>"+s+"</td>"
							+ "<td>"+name+"</td>"
							+ "<td>"+user_name+"</td>"
							+ "<td>"+pass_word+"</td>"
							+ "<td>"+tenth_mark+"</td>"
							+ "<td>"+twelveth_mark+"</td>"
							+ "<td>"+cgpa+"</td>"
							+"</tr>");
				pen.println("</table></body></html>");
				req.getRequestDispatcher("/l").include(req, resp);
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("/Demo.html");
				rd.forward(req,resp);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		
	}
}