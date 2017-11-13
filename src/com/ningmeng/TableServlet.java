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

public class TableServlet extends HttpServlet {
     private static final long serial = 1L;
     public TableServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
         String user="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "";
    	 String mon="",tues="",wednes="",thur="",fri="",sau="",sun="";
    	 boolean username_exist = false;
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
    		 }else{
    			 System.out.println("faild");
    		 }
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 System.out.println(user);
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
             ResultSet rs = stmt.executeQuery("select * from fittable where username='"+user+"'");
             //PrintWriter out = response.getWriter();
             //out = response.getWriter();
             while(rs.next()){
                  mon = rs.getString("mon");
                  tues = rs.getString("tues");
                  wednes = rs.getString("wednes");
                  thur = rs.getString("thur");
                  fri = rs.getString("fri");
                  sau = rs.getString("sau");
                  sun = rs.getString("sun");
                  username_exist = true;
                  System.out.println(mon+tues);
             }
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
    	 System.out.print(mon);
    	  JSONObject js = new JSONObject();
    	  Object lmon = mon;
    	  js.put("Monday",lmon);
    	 Object ltues= tues;
    	 js.put("Tuseday", ltues);
    	 Object lwednes= wednes;
    	 js.put("Wednesday", lwednes);
    	 Object lthur= thur;
    	 js.put("Thurday", lthur);
    	 Object lfri= fri;
    	 js.put("Friday", lfri);
    	 Object lsar= sau;
    	 js.put("Sartuday", lsar);
    	 Object lsun= sun;
    	 js.put("Sunday", lsun);
    	 if(username_exist){
    		 Object l = "true";
    		 js.put("exist", l);
    	 }else{
    		 Object l = "false";
    		 js.put("exist", l);
    	 }
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         doPost(request,response);
     }
}
