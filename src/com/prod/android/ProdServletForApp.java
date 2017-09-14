package com.prod.android;

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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

/**
 * Servlet implementation class ProdServletForApp
 * 連結app用的servlet
 */
@WebServlet("/ProdServletForApp")
public class ProdServletForApp extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private ProdService prodSvc = new ProdService();
	private List<ProdVO> list = prodSvc.getAllToJSON();
	private ProdVO prodVO;
	private List<ProdVO> prodList;
	private List<byte[]> prodImage;
    
    
	@Override
	public void init() throws ServletException {
		super.init();
		prodList = new ArrayList<ProdVO>();
		for(ProdVO list : list){
			prodList.add(list);
		}	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Gson gson = new Gson();
//		String prodList_gson = gson.toJson(prodList);
//		response.setContentType(CONTENT_TYPE);
//		PrintWriter out = response.getWriter();
//		out.println("<H3>Product JSON</H3>");
//		out.println(prodList_gson);
//		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn= new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		System.out.println("action : " + action );
		String outStr= "";
		if(action.equals("getAll")){
//			outStr = gson.toJson(prodList);
			outStr = gson.toJson(list);
		}else if( action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String prod_no = jsonObject.get("prod_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			prodImage = prodSvc.getImage(prod_no);
			byte[] prod_pic1 = prodImage.get(0);
			if(prod_pic1 != null){
				prod_pic1 = ImageUtil.shrink(prod_pic1, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(prod_pic1.length);
			}
			os.write(prod_pic1);
		}else{
			doGet(request, response);
		}
		
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println(outStr);
	}

}