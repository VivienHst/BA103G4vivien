package com.store.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.review.model.ReviewVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;

/**
 * Servlet implementation class StoreServletForApp
 */
@WebServlet("/StoreServletForApp")
public class StoreServletForApp extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private StoreService storeSvc;
	private List<StoreVO> list;
	private StoreVO storeVO ;
	private List<StoreVO> StoreList;
	private List<byte[]> StoreImage;
	private Set<ProdVO> prodSet;
	private List<ProdVO> prodList; 
	private OrdService ordSvc;
	private ProdService prodSvc;

    
    
	@Override
	public void init() throws ServletException {
		super.init();	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Gson gson = new Gson();
//		String StoreList_gson = gson.toJson(StoreList);
//		response.setContentType(CONTENT_TYPE);
//		PrintWriter out = response.getWriter();
//		out.println("<H3>Store JSON</H3>");
//		out.println(StoreList_gson);
//		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		request.setCharacterEncoding("UTF-8");
		
		storeSvc = new StoreService();
		
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
			list = storeSvc.getAllNoImg();
			StoreList = new ArrayList<StoreVO>();
			for(StoreVO list : list){
				StoreList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		} else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String Store_no = jsonObject.get("store_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			StoreImage = storeSvc.getImageByPK(Store_no);
			byte[] Store_pic1 = StoreImage.get(0);
			if(Store_pic1 != null){
				Store_pic1 = ImageUtil.shrink(Store_pic1, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(Store_pic1.length);
			}
			os.write(Store_pic1);
			os.flush();
			os.close();			
		} else if(action.equals("getOneStore")){
			String storeNo = jsonObject.get("store_no").getAsString();
			storeVO = storeSvc.getOneStoreNoImg(storeNo);
			System.out.println(storeVO.getStore_no());
			outStr = gson.toJson(storeVO);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getProdByStore")){
			prodSet = new HashSet<ProdVO>();
			prodList = new ArrayList<ProdVO>();
			String storeNo = jsonObject.get("store_no").getAsString();
			prodSet = storeSvc.getProdsByStoreNoImg(storeNo);
			
			for(ProdVO prodVO : prodSet){
				if(prodVO.getProd_stat().equals("上架")){
					prodList.add(prodVO);
				}
			}
			outStr = gson.toJson(prodList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getStoreByOrd")){
			ordSvc = new OrdService();
			prodSvc= new ProdService();
			storeSvc = new StoreService();
			String ord_no = jsonObject.get("ord_no").getAsString();
			
			Set<Ord_listVO> set =new HashSet<Ord_listVO>();
			set = ordSvc.getOrd_listByOrd(ord_no);
			List<Ord_listVO> ordList = new ArrayList<Ord_listVO>();
			Iterator<Ord_listVO> it = set.iterator();
			while (it.hasNext()){
				ordList.add(it.next());
			}

			StoreVO storeVO = storeSvc.getOneStoreNoImg(prodSvc.getOneProd(ordList.get(0).getProd_no()).getStore_no());

			outStr = gson.toJson(storeVO);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else{
			doGet(request, response);
		}
	}

}
