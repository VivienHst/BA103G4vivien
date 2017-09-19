package com.review.android;

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

import com.beanlife.android.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.review.model.ReviewService;
import com.review.model.ReviewVO;
import com.review.model.ReviewService;
import com.review.model.ReviewVO;

/**
 * Servlet implementation class ReviewServletForAndroid
 */
@WebServlet("/ReviewServletForApp")
public class ReviewServletForApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private ReviewService reviewSvc;
	private List<ReviewVO> list;
	private ReviewVO reviewVO ;
	private List<ReviewVO> reviewList;
	private List<byte[]> reviewImage;

    
    
	@Override
	public void init() throws ServletException {
		super.init();	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Gson gson = new Gson();
//		String reviewList_gson = gson.toJson(reviewList);
//		response.setContentType(CONTENT_TYPE);
//		PrintWriter out = response.getWriter();
//		out.println("<H3>review JSON</H3>");
//		out.println(reviewList_gson);
//		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		request.setCharacterEncoding("UTF-8");
		
		reviewSvc = new ReviewService();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
		if(action.equals("getProdReview")){//getByProd(String prod_no)
			String prod_no = jsonObject.get("prod_no").getAsString();
			list = reviewSvc.getVOByProd(prod_no);
			reviewList = new ArrayList<ReviewVO>();
			for(ReviewVO list : list){
				reviewList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		} else{
			doGet(request, response);
		}
	}


}