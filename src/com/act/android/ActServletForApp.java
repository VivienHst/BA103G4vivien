package com.act.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.act.model.ActService;
import com.act.model.ActVO;
import com.act_pair.model.Act_pairVO;
import com.beanlife.android.tool.ImageUtil;
import com.fo_act.model.Fo_actService;
import com.fo_act.model.Fo_actVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ActServletForApp
 */

@WebServlet("/ActServletForApp")
public class ActServletForApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8"; 
	
	private ActService actSvc;
	private Fo_actService fo_actSvc;
	private List<ActVO> list, actList, actHostList;
	private List<byte[]> actImage;
	private List<Fo_actVO> getFo_actVO;
	private List<Act_pairVO> getPair_actVO;
	private int foCount;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		actSvc = new ActService();
		
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
		System.out.println("act action : " + action);
		String outStr = "";
		
		if(action.equals("getAll")){
			System.out.println("get all No Img");
			list = actSvc.getAllNoImg();
			actList = new ArrayList<ActVO>();
			for(ActVO list : list){
				System.out.println(list.getAct_op_date());
				
				actList.add(list);
			}
			outStr = gson.toJson(list);
			System.out.println(outStr);
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
			
		} else if(action.equals("getOne")){
			ActVO getOneActVO = new ActVO();
			String act_no = jsonObject.get("act_no").getAsString();
			getOneActVO = actSvc.getOneNoImg(act_no);

			outStr = gson.toJson(getOneActVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		}else if(action.equals("getFoAct")){
			
			getFo_actVO = new ArrayList<Fo_actVO>();
			fo_actSvc = new Fo_actService();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println(mem_ac);
			getFo_actVO = fo_actSvc.getFo_actByMem_ac(mem_ac);
			System.out.println(getFo_actVO.toString());

			outStr = gson.toJson(getFo_actVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getPartiAct")){
			
			getPair_actVO = new ArrayList<Act_pairVO>();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println(mem_ac);
			getPair_actVO = actSvc.getAct_pairByMem_ac(mem_ac);
			System.out.println(getPair_actVO.toString());

			outStr = gson.toJson(getPair_actVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getHostAct")){
			actHostList = new ArrayList<ActVO>();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println(mem_ac);
			actHostList = actSvc.getAllActByMem_acNoImg(mem_ac);
			System.out.println(getPair_actVO.toString());

			outStr = gson.toJson(actHostList);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getFoCount")){
			actHostList = new ArrayList<ActVO>();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			System.out.println(act_no);
			foCount = fo_actSvc.countByAct(act_no);
			System.out.println(foCount);

			outStr = gson.toJson(foCount);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("isFollowed")){
			
			Fo_actVO isFollowed = new Fo_actVO();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();

			System.out.println(act_no);
			isFollowed = fo_actSvc.getFo_act(mem_ac, act_no);

			outStr = gson.toJson(isFollowed);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("addFoAct")){
			
			Fo_actVO addFoAct = new Fo_actVO();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();

			System.out.println(act_no);
			addFoAct = fo_actSvc.addFo_act(mem_ac, act_no);

		} else if(action.equals("deleteFoAct")){
			
			Fo_actVO addFoAct = new Fo_actVO();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();

			System.out.println(act_no);
			fo_actSvc.deleteFo_act(mem_ac, act_no);

		}
		
		else{
			doGet(request, response);
		}
		
	}

}
