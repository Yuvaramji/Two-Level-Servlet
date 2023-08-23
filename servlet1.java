package com.cb.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class servlet1 extends HttpServlet{
	Connection con=null;
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	ResultSet rs1=null;
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
		PrintWriter pen=resp.getWriter();
		String query="Select *from student_details where username=? and password=?";
		String query1="Select *from drivecompany_details where 10th_mark<=? and 12th_mark<=? and cgpa<=?";
		try {
			ps=con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			rs=ps.executeQuery();
			rs.next();
			String name=rs.getString(2);
			int tenth_mark=rs.getInt(5);
			int twelveth_mark=rs.getInt(6);
			int cgpa=rs.getInt(7);
			pen.println("<h2>"+name+" are eligible for the following Drives:</h2>");
			ps1=con.prepareStatement(query1);
			ps1.setInt(1, tenth_mark);
			ps1.setInt(2, twelveth_mark);
			ps1.setInt(3, cgpa);
			rs1=ps1.executeQuery();
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
						+ "<th>Company_Name</th>"
						+ "<th>10th_Mark</th>"
						+ "<th>12th_Mark</th>"
						+ "<th>CGPA</th>"
						+ "<th>Salary</th>"
						+ "</tr>");
				while(rs1.next()) {
					int s=rs1.getInt(1);
					String companyname=rs1.getString(2);
					int tenthmark=rs1.getInt(3);
					int twelvethmark=rs1.getInt(4);
					int cgpa1=rs1.getInt(5);
					int salary=rs1.getInt(6);
					pen.println("<tr>"
							+ "<td>"+s+"</td>"
							+ "<td>"+companyname+"</td>"
							+ "<td>"+tenthmark+"</td>"
							+ "<td>"+twelvethmark+"</td>"
							+ "<td>"+cgpa1+"</td>"
							+ "<td>"+salary+"</td>"
							+"</tr>");
				}
				pen.println("</table></body></html>");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		
	}
}