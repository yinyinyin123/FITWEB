package com.ningmeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

public class insdevServlet extends HttpServlet {
     private static final long serial = 1L;
     public insdevServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
         String user="";
         String devicename="";
         String time = "";
         String devicesum = "";
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "" ;
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
    			 devicename = jo.get("devicename").toString();
    			 devicesum = jo.get("devicesum").toString();
    			 time = jo.getString("time").toString();
    		 }else{
    			 System.out.println("faild");
    		 }
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
//             ResultSet rs = stmt.executeQuery("select * from agenatable where username='"+user+"'");
//             //PrintWriter out = response.getWriter();
//             //out = response.getWriter();
//             while(rs.next()){
//                  ti = rs.getString("time");
//                  ag = rs.getString("agena");
//                  agena.add(ag);
//                  time.add(ti);
//                 
//             }
        	 String sql="insert into insdevtable(user,time,devicename,devicesum,deviceleft) value(?,?,?,?,?)";
       	     try{
       		    PreparedStatement pst = (PreparedStatement) connect.prepareStatement(sql);
       		    pst.setString(1, user);
       		    pst.setString(2,time);
       		    pst.setString(3, devicename);
       		    pst.setString(4, devicesum);
       		    pst.setString(5, devicesum);
       		    pst.executeUpdate();
       	      }catch(SQLException e){
       		    e.printStackTrace();
       	      }
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
//    	 JSONObject js = new JSONObject();
//         for(int i = 0 ; i < agena.size() ; i++){
//        	 js.put(""+i+""+"agena", agena.get(i));
//        	 js.put(""+i+""+"time", time.get(i));
//         }
//         js.put("number", time.size());
//    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
         //doPost(request,response);
    	 String user = request.getParameter("username");
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "" , ti = "" , ag = "";
    	 ArrayList<String> agena = new ArrayList<String>();
    	 ArrayList<String> time = new ArrayList<String>();
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
              ResultSet rs = stmt.executeQuery("select * from agenatable where username='"+user+"'");
              //PrintWriter out = response.getWriter();
              //out = response.getWriter();
              while(rs.next()){
                   ti = rs.getString("time");
                   ag = rs.getString("agena");
                   System.out.println(ag);
                   agena.add(ag);
                   time.add(ti);
              }
          }catch(SQLException e){
         	 e.printStackTrace();
          }catch(Exception e){
         	 e.printStackTrace();
          }
     	 JSONObject js = new JSONObject();
          for(int i = 0 ; i < agena.size() ; i++){
         	 js.put(""+i+""+"agena", agena.get(i));
         	 System.out.println(agena.get(i));
         	 js.put(""+i+""+"time", time.get(i));
          }
          js.put("number", time.size());
     	 response.getWriter().print(js);
     }
}
