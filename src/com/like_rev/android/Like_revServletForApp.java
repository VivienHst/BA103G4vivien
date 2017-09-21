package com.like_rev.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.like_rev.model.Like_revService;
/**
 * Servlet implementation class Like_reviewServletForApp
 */
@WebServlet("/Like_revServletForApp")
public class Like_revServletForApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

    private int likeCont;
    private Like_revService like_revSvc;
    

    public Like_revServletForApp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		like_revSvc = new Like_revService();
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line =  null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		br.close();
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		String outStr = "";
		System.out.println("action : " + action);
		if(action.equals("getCount")){
			
			String rev_no = jsonObject.get("rev_no").getAsString();
			System.out.println(rev_no);
			likeCont = like_revSvc.getCountByRev("R1000000003");
			outStr = gson.toJson(likeCont);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
		} else{
			doGet(request, response);
		}
		
	}

}
