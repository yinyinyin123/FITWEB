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
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.*;

public class registerServlet extends HttpServlet {
     private static final long serial = 1L;
     public registerServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
         String user="";
         String password="";
         String student="";
         String name="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "" ;
    	 boolean username_exist = false;
    	 JSONObject js = new JSONObject();
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
    			 user = jo.get("username").toString();
    			 password = jo.get("password").toString();
                 student = jo.get("student").toString();
    			 name = jo.get("name").toString();
    		 }else{
    			 System.out.println("faild");
    		 }
    		 System.out.println(user+" "+name);
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 try {  
          	Class.forName("com.mysql.jdbc.Driver"); 
            	System.out.println("Success loading Mysql Driver!");  
       	 }catch (Exception e) { 
       		System.out.print("Error loading Mysql Driver!");  
     			e.printStackTrace();  
      	 }
    	 try{
        	 Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true","root","yinyin");
        	 Statement stmt = connect.createStatement();  
        	 ResultSet rs = stmt.executeQuery("select * from users_app where username='"+user+"'");
        	 if(rs.next()){
        		 Object o = "user has existed";
        		 js.put("exist", o);
        	 }
        	 else{       	 
                 String sql="insert into users_app(username,password,student,name) value(?,?,?,?)";
                 try{
                	 PreparedStatement pst = (PreparedStatement) connect.prepareStatement(sql);
                	 pst.setString(1, user);
                	 pst.setString(2,password);
                	 pst.setString(3, student);
                	 pst.setString(4,name);
                	 pst.executeUpdate();
       	      	 }catch(SQLException e){
       	      		 e.printStackTrace();
       	      	 }
                 Object o = "ok";
                 js.put("exist", o);
        	 }

         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }	 
    	 response.getWriter().print(js);
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         doPost(request,response);
     }
}
