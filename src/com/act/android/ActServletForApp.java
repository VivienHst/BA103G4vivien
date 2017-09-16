package com.act.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.act.model.ActService;
import com.act.model.ActVO;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.prod.android.ImageUtil;

/**
 * Servlet implementation class ActServletForApp
 */

@WebServlet("/ActServletForApp")
public class ActServletForApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8"; 
	
	private ActService actSvc;
	private List<ActVO> list;
	private List<ActVO> actList;
	private List<byte[]> actImage;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		actSvc = new ActService();
		
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
		System.out.println("act action : " + action);
		String outStr = "";
		if(action.equals("getAll")){
			System.out.println("Get all Img");
			list = actSvc.getAllNoImg();
			actList = new ArrayList<ActVO>();
			for(ActVO list : list){
				actList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);			
		} else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String act_no = jsonObject.get("act_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			actImage = actSvc.getImageByPK(act_no);
			byte[] act_pic1 = actImage.get(0);
			if(act_pic1 != null){
				act_pic1 = ImageUtil.shrink(act_pic1, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(act_pic1.length);
			}
			os.write(act_pic1);
			os.flush();
			os.close();
		}
		else{
			doGet(request, response);
		}
		
	}

}
