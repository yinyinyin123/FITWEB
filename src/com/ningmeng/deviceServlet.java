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

public class deviceServlet extends HttpServlet {
     private static final long serial = 1L;
     public deviceServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
         String user="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "",today = "" ,nday = "";
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
    			 today = jo.get("today").toString();
    			 nday = jo.get("nday").toString();
    		 }else{
    			 System.out.println("faild");
    		 }
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 ArrayList<String> n_devicename = new ArrayList<String>();
    	 ArrayList<String> nn_devicename = new ArrayList<String>();
    	 ArrayList<String> n_devicesum = new ArrayList<String>();
    	 ArrayList<String> nn_devicesum = new ArrayList<String>();
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
             ResultSet rs = stmt.executeQuery("select * from insdevtable where user='"+user+"' and time='"+today+"'");
             while(rs.next()){
                    n_devicename.add(rs.getString("devicename"));
                    n_devicesum.add(rs.getString("devicesum"));
             }
             rs = stmt.executeQuery("select * from insdevtable where user='"+user+"' and time='"+nday+"'");
             while(rs.next()){
                    nn_devicename.add(rs.getString("devicename"));
                    nn_devicesum.add(rs.getString("devicesum"));
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
    	 js.element("n_devicename", n_devicename);
    	 js.element("nn_devicename", nn_devicename);
         js.element("n_devicesum", n_devicesum);
         js.element("nn_devicesum",nn_devicesum);
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         //doPost(request,response);
         doPost(request,response);
     }
}