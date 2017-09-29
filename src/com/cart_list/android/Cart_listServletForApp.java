package com.cart_list.android;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.cart_list.model.Cart_listService;
import com.cart_list.model.Cart_listVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

/**
 * Servlet implementation class Cart_listServletForApp
 */
@WebServlet("/Cart_listServletForApp")
public class Cart_listServletForApp extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private Cart_listService cart_listSvc;
	private Set<Cart_listVO> cartList;

       
    public Cart_listServletForApp() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		cart_listSvc = new Cart_listService();
			
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
		
		if(action.equals("getVOsByMem")){
			
//			String prod_no = jsonObject.get("prod_no").getAsString();
//			System.out.println("prod_no : " + prod_no );
			cartList = new HashSet<Cart_listVO>();
			
			List<Cart_listVO> cartDetailList = new ArrayList<Cart_listVO>();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("mem_ac : " + mem_ac );

			cartList = cart_listSvc.getVOsByMem(mem_ac);
			System.out.println(cartList.toString());
			Iterator<Cart_listVO> cartListIt = cartList.iterator();

			while (cartListIt.hasNext()){
				System.out.println("cartListIt : " + cartListIt);
				cartDetailList.add((Cart_listVO) cartListIt.next());	
			}
			
			outStr = gson.toJson(cartDetailList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		 	
		} else if(action.equals("addCart_list")){
			boolean isAddToCar = false;
			ProdVO prodVO;
			ProdService prodSvc;
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("mem_ac : " + mem_ac );
			String prod_no = jsonObject.get("prod_no").getAsString();
			System.out.println("prod_no : " + prod_no );
			int prod_amount = Integer.parseInt(jsonObject.get("prod_amount").getAsString());
			System.out.println("prod_amount : " + prod_amount );

			Cart_listVO oldCart_listVO = null;
			Cart_listVO cartList = new Cart_listVO();

			if((oldCart_listVO = cart_listSvc.getCart_list(prod_no, mem_ac)) == null){
				cartList = cart_listSvc.addCart_list(prod_no,mem_ac,prod_amount);
				isAddToCar = true;
			} else {
				prodSvc = new ProdService();
				prodVO = prodSvc.getOneProd(prod_no);
				
				prod_amount = prod_amount + oldCart_listVO.getProd_amount();
				if(prod_amount <= prodVO.getProd_sup()){
					cartList = cart_listSvc.updateCart_list(prod_no, mem_ac, prod_amount);
					isAddToCar = true;
				}
			}
			System.out.println("isAddToCar : " + isAddToCar );
			outStr = gson.toJson(isAddToCar);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		 	
		} else if(action.equals("updateCart_list")){
			boolean isAddToCar = false;
			ProdVO prodVO;
			ProdService prodSvc;
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("mem_ac : " + mem_ac );
			String prod_no = jsonObject.get("prod_no").getAsString();
			System.out.println("prod_no : " + prod_no );
			int prod_amount = Integer.parseInt(jsonObject.get("prod_amount").getAsString());
			System.out.println("prod_amount : " + prod_amount );

			Cart_listVO cartList = new Cart_listVO();
	
			prodSvc = new ProdService();
			prodVO = prodSvc.getOneProd(prod_no);
				
			if(prod_amount <= prodVO.getProd_sup()){
				cartList = cart_listSvc.updateCart_list(prod_no, mem_ac, prod_amount);
				isAddToCar = true;
			}
							
			System.out.println("isAddToCar : " + isAddToCar );
			outStr = gson.toJson(isAddToCar);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
		 	out.println(outStr);
		} else if(action.equals("deleteCart_list")){

			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("mem_ac : " + mem_ac );
			String prod_no = jsonObject.get("prod_no").getAsString();
			System.out.println("prod_no : " + prod_no );	
			
			cart_listSvc.deleteCart_list(prod_no, mem_ac);					
		} else{
			doGet(request, response);
		}
	}


}
