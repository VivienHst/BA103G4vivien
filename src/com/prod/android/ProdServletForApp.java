package com.prod.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beanlife.android.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.review.model.ReviewService;

/**
 * Servlet implementation class ProdServletForApp
 * 連結app用的servlet
 */
@WebServlet("/ProdServletForApp")
public class ProdServletForApp extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private ProdService prodSvc;
	private List<ProdVO> list;
	private ProdVO prodVO ;
	private List<ProdVO> prodList;
	private List<byte[]> prodImage;
	private ReviewService revSvc;
	private Double prodScore;
    
    
	@Override
	public void init() throws ServletException {
		super.init();	
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
		
		prodSvc = new ProdService();
		
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
		if(action.equals("getAll")){
			list = prodSvc.getAllNoImg();
			prodList = new ArrayList<ProdVO>();
			for(ProdVO list : list){
				prodList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getOneProd")){
			String prod_no = jsonObject.get("prod_no").getAsString();
			System.out.println(prod_no);
			prodVO = prodSvc.getOneProdNoImg(prod_no);
			outStr = gson.toJson(prodVO);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String prod_no = jsonObject.get("prod_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			prodImage = prodSvc.getImageByPK(prod_no);
			byte[] prod_pic1 = prodImage.get(0);
			if(prod_pic1 != null){	
				//把壓縮圖片寫在DAO測試速度
				prod_pic1 = ImageUtil.shrink(prod_pic1, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(prod_pic1.length);
			}
			os.write(prod_pic1);
			os.flush();
			os.close();
			
		}else if(action.equals("getQueryResult")){
			String bean_contry = jsonObject.get("bean_contry").getAsString();
			String proc = jsonObject.get("proc").getAsString();
			String roast = jsonObject.get("roast").getAsString();
			String prod_name = jsonObject.get("prod_name").getAsString();
			
			list = prodSvc.getQueryResult(bean_contry, proc, roast, prod_name);
			
			prodList = new ArrayList<ProdVO>();
			for(ProdVO list : list){
				prodList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getScore")){
			revSvc = new ReviewService();
			String prod_no = jsonObject.get("prod_no").getAsString();
			System.out.println(prod_no);
			
			prodScore = revSvc.getScoreByProd(prod_no) ;
			outStr = gson.toJson(prodScore);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getHotProd")){
			Set<ProdVO> prodVO1 = new HashSet<ProdVO>();
			List<ProdVO> hotProdList = new ArrayList<ProdVO>();
			prodVO1 = (Set<ProdVO>) getServletContext().getAttribute("hotProdVOs");
			
			System.out.println(prodVO1.toString());
			
			for(ProdVO prodVO : prodVO1){
				prodVO.setProd_pic1(null);
				prodVO.setProd_pic2(null);
				prodVO.setProd_pic3(null);
				hotProdList.add(prodVO);
			}
			
			outStr = gson.toJson(hotProdList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getProdAtr")){
			
			/*
			 * Set<String> countrys = setCountrys();
				ServletContext context = getServletContext();
		    	context.setAttribute("countrys", countrys);
			 */
			Set<String> prodCon = new HashSet<String>();
			List<ProdVO> hotProdList = new ArrayList<ProdVO>();
			prodCon = (Set<String>) getServletContext().getAttribute("countrys");
			
			System.out.println(prodCon);

			outStr = gson.toJson(prodCon);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
		} else if(action.equals("getNewProd")){
			list = prodSvc.getNewNoImg();
			prodList = new ArrayList<ProdVO>();
			for(ProdVO list : list){
				prodList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		}
		//			Set<ProdVO> prodVO = request.getSession().getAttribute("hotProds");
		// 請求session中的熱門商品
		else{
			doGet(request, response);
		}

	}

}
