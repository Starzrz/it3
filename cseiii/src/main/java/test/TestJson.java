package test;

import java.io.IOException;         
import java.io.PrintWriter;         
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;         
import javax.servlet.http.HttpServlet;         
import javax.servlet.http.HttpServletRequest;         
import javax.servlet.http.HttpServletResponse;         
        
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;         
        
public class TestJson extends HttpServlet {         
        
    public void doGet(HttpServletRequest request, HttpServletResponse response)        
            throws ServletException, IOException {         
           doPost(request,response);         
    }         
    public void doPost(HttpServletRequest request, HttpServletResponse response)         
            throws ServletException, IOException {         
        
        response.setContentType("text/html");            
    String str= request.getParameter("name");//得到ajax传递过来的paramater         
        System.out.print(str);         
        PrintWriter out = response.getWriter();         
        List list = new ArrayList();//传递List         
                  Map m=new HashMap();//传递Map             
        User u1=new User();         
        u1.setUsername("zah");         
        u1.setPassword("123");         
        User u2=new User();         
        u2.setUsername("ztf");         
        u2.setPassword("456");           
        list.add(u1); //添加User对象               
         list.add(u2);     //添加User对象           
               
          m.put("u1", u1);         
         m.put("u2", u2);            
        
   JSONArray jsonArray2 = JSONArray.fromObject( list );         
       //把java数组转化成转化成json对象          
   JSONObject jo=JSONObject.fromObject(m);//转化Map对象         
        out.print(jsonArray2);//返给ajax请求         
       out.print(jo);//返给ajax请求         
    }         
}  