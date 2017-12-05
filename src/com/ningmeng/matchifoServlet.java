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

public class matchifoServlet extends HttpServlet {
     private static final long serial = 1L;
	private String matchjoin_endtime;
	private String matchhost;
	private String matchposi;
	private String matchtime;
	private String matchifo;
	private String matchname;
	private String joinway;
	private String type;
     public matchifoServlet(){
    	 super();
     }
     protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
         String matchid="";
    	 response.setContentType("text/html;charset=utf-8");
    	 String accept = "" , ti = "" , ag = "";
    	 //ArrayList<String> agena = new ArrayList<String>();
    	 //ArrayList<String> time = new ArrayList<String>();
    	 //String mon="",tues="",wednes="",thur="",fri="",sau="",sun="";
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
    			 matchid = jo.get("matchID").toString();
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
             ResultSet rs = stmt.executeQuery("select * from matchtable where matchID='"+matchid+"'");
//             //PrintWriter out = response.getWriter();
             //out = response.getWriter();
             while(rs.next()){
                  matchname = rs.getString("matchname");
                  matchjoin_endtime = rs.getString("joinendtime");
                  matchhost = rs.getString("matchhost");
                  matchposi = rs.getString("matchposi");
                  matchtime = rs.getString("matchtime");
                  matchifo = rs.getString("matchifo");
                  joinway = rs.getString("joinway");
                  type = rs.getString("type");
                 
             }
//        	 String sql="insert into agenatable(username,time,agena) value(?,?,?)";
//       	     try{
//       		    PreparedStatement pst = (PreparedStatement) connect.prepareStatement(sql);
//       		    pst.setString(1, user);
//       		    pst.setString(2,time);
//       		    pst.setString(3, agena);
//       		    pst.executeUpdate();
//       	      }catch(SQLException e){
//       		    e.printStackTrace();
//       	      }
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
    	 JSONObject js = new JSONObject();
         js.put("matchtime", matchtime);
         js.put("matchname",matchname);
		 js.put("matchifo",matchifo);
		 js.put("join_endtime",matchjoin_endtime);
		 js.put("matchposi",matchposi);
		 js.put("matchhost",matchhost);
		 js.put("joinway", joinway);
		 js.put("type",type);
    	 response.getWriter().print(js);
    	 
     }
     protected void doGet(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException{
          doPost(request, response);
     }
}