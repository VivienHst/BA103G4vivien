package com.ord.android;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;

/**
 * Servlet implementation class OrdServletForApp
 */
@WebServlet("/OrdServletForApp")
public class OrdServletForApp extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private OrdService ordSvc;
	private List<OrdVO> list;
	private OrdVO ordVO;
	private List<OrdVO> ordList;
	private Set<Ord_listVO>  detailList;
	private List<Ord_listVO>  ordDetailList;
	

	
       
    public OrdServletForApp() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ordSvc = new OrdService();
		
		
		
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
		
		if(action.equals("getOrdByMem_ac")){
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("mem_ac : " + mem_ac );
			list = new ArrayList<OrdVO>();
			list = ordSvc.getOrdByMem_ac(mem_ac);
			ordList = new ArrayList<OrdVO>();
			System.out.println(list.toString());
			
			for(OrdVO list : list){
				ordList.add(list);
			}
			outStr = gson.toJson(list);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);

		} else if(action.equals("getOrd_listByOrd")){
			
			String ord_no = jsonObject.get("ord_no").getAsString();
			System.out.println("ord_no : " + ord_no);
			ordDetailList = new ArrayList<Ord_listVO>();
			detailList = new HashSet<Ord_listVO>();
			detailList = ordSvc.getOrd_listByOrd(ord_no);
			Iterator<Ord_listVO> detailListIt = detailList.iterator();
			System.out.println(detailList.toString());
			
			while (detailListIt.hasNext()){
				System.out.println("detailListIt : " + detailListIt);
				ordDetailList.add((Ord_listVO) detailListIt.next());	
			}
			System.out.println("ordDetailList : " + ordDetailList.toString());
			
			outStr = gson.toJson(ordDetailList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		 	
		}else if(action.equals("getOrd_listByOrd")){
			
			String ord_no = jsonObject.get("ord_no").getAsString();
			System.out.println("ord_no : " + ord_no);
			ordDetailList = new ArrayList<Ord_listVO>();
			detailList = new HashSet<Ord_listVO>();
			detailList = ordSvc.getOrd_listByOrd(ord_no);
			Iterator<Ord_listVO> detailListIt = detailList.iterator();
			System.out.println(detailList.toString());
			
			while (detailListIt.hasNext()){
				System.out.println("detailListIt : " + detailListIt);
				ordDetailList.add((Ord_listVO) detailListIt.next());	
			}
			System.out.println("ordDetailList : " + ordDetailList.toString());
			
			outStr = gson.toJson(ordDetailList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		 	
		}else if(action.equals("newAnOrder")){
			
//			public String newAnOrder(OrdVO ordVO, Set<Ord_listVO> ord_listVOs){
//				return dao.insertWithOrd_list(ordVO, ord_listVOs);
//			}
////			
//			String ord_no = jsonObject.get("ordVO").getAsString();
//			String Ord_listVO = jsonObject.get("Ord_listVO").getAsString();
//
//			ordDetailList = new ArrayList<Ord_listVO>();
//			detailList = new HashSet<Ord_listVO>();
//			detailList = ordSvc.getOrd_listByOrd(ord_no);
//			Iterator<Ord_listVO> detailListIt = detailList.iterator();
//			System.out.println(detailList.toString());
//			
//			while (detailListIt.hasNext()){
//				System.out.println("detailListIt : " + detailListIt);
//				ordDetailList.add((Ord_listVO) detailListIt.next());	
//			}
//			System.out.println("ordDetailList : " + ordDetailList.toString());
//			
//			outStr = gson.toJson(ordDetailList);
//			response.setContentType(CONTENT_TYPE);
//			PrintWriter out = response.getWriter();
//		 	out.println(outStr);
		}
		
		else{
			doGet(request, response);
		}
	
	}

}
