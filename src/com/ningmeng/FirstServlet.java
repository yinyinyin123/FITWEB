package com.ningmeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Driver;
import net.sf.json.*;

public class FirstServlet extends HttpServlet {
     private static final long serial = 1L;
     public FirstServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
//    	 response.setContentType("text/html;charset=utf-8");
//    	 PrintWriter out = response.getWriter();
//    	 out.print("µÇÂ¼³É¹¦");
//    	 out.flush();
//    	 out.close();
//    	 response.getWriter().append("Served at: ").append(request.getContextPath());
    	 response.setContentType("text/json;charset=utf-8");
    	 String accept = "";
    	 try{
    		 BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), "utf-8"));
    		 StringBuffer sb = new StringBuffer("");
    		 String temp;
    		 while((temp = br.readLine()) != null ){
    			 sb.append(temp);
    		 }
    		 br.close();
    		 accept = sb.toString();
    		 if(!accept.equals("")){
    			 JSONObject jo = JSONObject.fromObject(accept);
    			 System.out.println(jo.get("username"));
    		 }else{
    			 System.out.println("faild");
    		 }
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 JSONObject js = new JSONObject();
    	 Object l = "yin";
    	 js.put("login", l);
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
    	 boolean login_ok = false;
    	 boolean username_exist = false;
    	 response.setContentType("text/html;charset=utf-8");
    	 try {  
       	    Class.forName("com.mysql.jdbc.Driver"); 
      		System.out.println("Success loading Mysql Driver!");  
      	 }catch (Exception e) { 
      		System.out.print("Error loading Mysql Driver!");  
    			e.printStackTrace();  
     	 }
    	 String username = request.getParameter("username");
    	 String password = request.getParameter("password");
         try{
        	 Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true","root","yinyin");
        	 Statement stmt = connect.createStatement();  
             ResultSet rs = stmt.executeQuery("select * from users_app where username='"+username+"'");
             PrintWriter out = response.getWriter();
             out = response.getWriter();
             while(rs.next()){
            	 username_exist = true;
            	 if(rs.getString("password").equals(password)){
            		 login_ok = true;
            		 out.print("success"+rs.getString("student")+username+" "+rs.getString("name"));
            		 break;
            	 }
             }
             if(!username_exist){
            	 out.println("usename not exist");
             }
             else if(!login_ok){
            	 out.println("password not correct");
             }
        	 out.flush();
        	 out.close();
        	 response.getWriter().append("Served at: ").append(request.getContextPath());
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
     }
}
