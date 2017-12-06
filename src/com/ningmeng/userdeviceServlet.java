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
import net.sf.json.*;
import java.util.Calendar;  
import java.util.Date; 

public class userdeviceServlet extends HttpServlet {
     private static final long serial = 1L;
     public userdeviceServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
//         String user="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String user="",time="",devicename="",accept = "";
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
    			 time = jo.get("time").toString();
    			 devicename = jo.get("devicename").toString();
    		 }else{
    			 System.out.println("faild");
    		 }
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 ArrayList<String> users = new ArrayList<String>();
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
             ResultSet rs = stmt.executeQuery("select * from userdevicetable where user='"+user+"' and time='"+time+"' and devicename='"+devicename+"'");
             //PrintWriter out = response.getWriter();
             //out = response.getWriter();
             while(rs.next()){
                  users.add(rs.getString("person"));
             }
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
    	 
    	 JSONObject js = new JSONObject();
    	 js.element("users", users);
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         //doPost(request,response);
         doPost(request,response);
     }
}
