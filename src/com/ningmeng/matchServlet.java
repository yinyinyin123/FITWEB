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

public class matchServlet extends HttpServlet {
     private static final long serial = 1L;
     public matchServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
//         String user="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String year = "" , month = "" , day = "";
    	 Calendar now = Calendar.getInstance();
    	 year ="" + now.get(Calendar.YEAR);
    	 if(now.get(Calendar.MONTH)+1 < 10){
    		 month = "0" + (now.get(Calendar.MONTH)+1);
    	 }
    	 else{
    		 month = "" + (now.get(Calendar.MONTH)+1);
    	 }
    	 if(now.get(Calendar.DAY_OF_MONTH) < 10){
    		 day = "0" + (now.get(Calendar.DAY_OF_MONTH));
    	 }
    	 else{
    		 day = "" + (now.get(Calendar.DAY_OF_MONTH));
    	 }
    	 String date = year + "-" + month + "-" + day;
    	 ArrayList<String> matchID_ing = new ArrayList<String>();
    	 ArrayList<String> matchname_ing = new ArrayList<String>();
    	 ArrayList<String> matchID_ed = new ArrayList<String>();
    	 ArrayList<String> matchname_ed = new ArrayList<String>();   	 
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
             ResultSet rs = stmt.executeQuery("select * from mtable");
             //PrintWriter out = response.getWriter();
             //out = response.getWriter();
             while(rs.next()){
                 String time = rs.getString("matchtime");
                 if(time.compareTo(date) < 0){
                	 matchID_ed.add(rs.getString("matchID"));
                	 matchname_ed.add(rs.getString("matchname"));
                 }
                 else{
                	 matchID_ing.add(rs.getString("matchID"));
                	 matchname_ing.add(rs.getString("matchname"));
                 }   
             }
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
    	 
    	 JSONObject js = new JSONObject();
    	 JSONArray temp_ing = new JSONArray();
//         js.put("ing", matchname_ing.get(0));
//         js.put("end", matchname_ed.get(0));
    	 js.element("matchid_ing", matchID_ing);
    	 js.element("matchid_ed", matchID_ed);
         js.element("matchname_ing", matchname_ing);
         js.element("matchname_ed",matchname_ed);
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         //doPost(request,response);
         doPost(request,response);
     }
}
