package com.msg.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beanlife.android.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.review.model.ReviewService;

/**
 * Servlet implementation class MsgServletForApp
 */

@WebServlet("/MsgServletForApp")
public class MsgServletForApp extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	private MsgService msgSvc;
	private Set<MsgVO> msgVOSet;
	private Set<String> msgStringSet;

    public MsgServletForApp() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		request.setCharacterEncoding("UTF-8");
		
		msgSvc = new MsgService();
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		br.close();
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		System.out.println("action : " + action );
		String outStr = "";
		
		if(action.equals("getAllByPair")){
			msgVOSet = new LinkedHashSet<MsgVO>();
			String mem_ac1 = jsonObject.get("mem_ac1").getAsString();
			String mem_ac2 = jsonObject.get("mem_ac2").getAsString();
			msgVOSet = msgSvc.getAllByPair(mem_ac1, mem_ac2);
			
			outStr = gson.toJson(msgVOSet);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		}
		if(action.equals("getAllPairByMem")){
			msgStringSet = new LinkedHashSet<String>();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			msgStringSet = msgSvc.getAllPairByMem(mem_ac);
			
			outStr = gson.toJson(msgStringSet);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);			
		}
		else{
			doGet(request, response);
		}

	}

}
