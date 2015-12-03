
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//<%=session.getAttribute("currentSessionUser") %>

public class LoginServlett extends HttpServlet {

	String Sname;
	String Spass;
	RequestDispatcher dispatch;
	RequestDispatcher dis;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		
		ServletContext context= getServletContext();
		System.out.println(context.getInitParameter("jdbc.driver"));
		//context.getInitParameter("jdbc.driver");
		
		
		
	/*	context.setAttribute("sheroz", "feroz");
		System.out.println(context.getAttribute("sheroz"));                           */
		
		String uname=request.getParameter("name");
		String upass=request.getParameter("password");
		//System.out.println("checking" + upass);

		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost/database","root","12345");
			System.out.println("Connected");
			System.out.println();
			Statement stmt=con.createStatement();
			String sql= "SELECT * FROM users where name='"+uname+"'and password='"+upass+"'";
			//"select * from users where id=2";
			//
			ResultSet rs=stmt.executeQuery(sql);
//System.out.println("i am:"+rs.getString("id"));
			if(rs.next()){

				HttpSession session= request.getSession();
				session.setAttribute("currentSessionUser", uname);
				
				
				//System.out.println(session.getId()+" ");
				String aHeader=request.getHeader("User-Agent");
				System.out.println();
				//System.out.println("Header Value "+aHeader);
				
				String sessionId = session.getId();
				String cookieFromRequestHeader = request.getHeader("cookie"); 
				
				System.out.println();
				//System.out.println("Session id="+session.getId());
				//System.out.println("Cookie="+request.getHeader("cookie"));
				
				Sname=rs.getString("name");
				Spass=rs.getString("password");
				//System.out.println(rs.getInt("id"));
				//System.out.println(rs.getString("name"));
				//System.out.println(rs.getString("password"));


				//	System.out.println("((((rs wala: "+rs.getString("name")+" and uname: "+uname+"))))");
				//			(uname.equals(rs.getString("name")))
				if((uname.equalsIgnoreCase(rs.getString("name")) &&  upass.equalsIgnoreCase(rs.getString("password")))){
					dispatch=request.getRequestDispatcher("home.jsp");
					dispatch.forward(request,response);
					System.out.println("if block comparing: "+Sname+" with "+uname+" and "+Spass+" with "+upass);
				}
				
				
			}else {	//this else was not working (fixed) 
				dis=request.getRequestDispatcher("error.jsp");
				dis.forward(request,response);
				System.out.println("Else block comparing: "+Sname+" with "+uname+" and "+Spass+" with "+upass);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Out of block comparing: "+Sname+" with "+uname+" and "+Spass+" with "+upass);
		System.out.println();
		System.out.println("This is the last line");
	}
}














/*if(uname.equals(Sname)&&upass.equals(Spass)){
dispatch=request.getRequestDispatcher("home.jsp");
dispatch.include(request,response);
}else{
dispatch=request.getRequestDispatcher("error.jsp");
dispatch.include(request,response);
}*/




/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("pass"));
		PrintWriter out=response.getWriter();
		out.println(request.getParameter("name"));
		out.println(request.getParameter("pass"));


		}*/
/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println(request.getParameter("name"));
			System.out.println(request.getParameter("pass"));
			PrintWriter out=response.getWriter();
			out.println(request.getParameter("name"));
			out.println(request.getParameter("pass"));
		}*/


