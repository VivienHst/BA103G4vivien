package com.act.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.act.model.ActService;
import com.act.model.ActVO;
import com.act_pair.model.Act_pairService;
import com.act_pair.model.Act_pairVO;
import com.beanlife.android.tool.ImageUtil;
import com.fo_act.model.Fo_actService;
import com.fo_act.model.Fo_actVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ord_list.model.Ord_listVO;

/**
 * Servlet implementation class ActServletForApp
 */

@WebServlet("/ActServletForApp")
public class ActServletForApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8"; 
	
	private ActService actSvc;
	private Fo_actService fo_actSvc;
	private Act_pairService act_pairSvc;
	private List<ActVO> list, actList, actHostList;
	private List<byte[]> actImage;
	private List<Fo_actVO> getFo_actVO;
	private List<Act_pairVO> getPair_actVO;
	private Set<Act_pairVO> getMem_pair_actVO;

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
		//取得所有活動
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
		//取得活動照片
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
		//取得單一活動
			ActVO getOneActVO = new ActVO();
			String act_no = jsonObject.get("act_no").getAsString();
			getOneActVO = actSvc.getOneNoImg(act_no);

			outStr = gson.toJson(getOneActVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getAllAct")){
		//取得可以參加的活動
			ActVO getAllActVO = new ActVO();
			String act_stat = jsonObject.get("act_stat").getAsString();
			Map<String, String[]> getActPara = new HashMap<String, String[]>();
			System.out.println("-----------------------"+act_stat);

			getActPara.put("act_stat", new String[] { act_stat });
			
			//getAllActVO = actSvc.getAll(getActPara);
			list = actSvc.getAll(getActPara);
			actList = new ArrayList<ActVO>();
			for(ActVO list : list){
				System.out.println(list.getAct_op_date());
				list.setAct_pic1(null);
				list.setAct_pic2(null);
				list.setAct_pic3(null);
				
				actList.add(list);
			}
			outStr = gson.toJson(list);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getFoAct")){
		//取得收藏的活動
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
		//取得參加的活動
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
		//取得舉辦的活動
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
		//取得活動收藏數
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
		//確認活動是否收藏
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
		//加入收藏活動
			Fo_actVO addFoAct = new Fo_actVO();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();

			System.out.println(act_no);
			addFoAct = fo_actSvc.addFo_act(mem_ac, act_no);

		} else if(action.equals("deleteFoAct")){
		//取消收藏活動
			Fo_actVO addFoAct = new Fo_actVO();
			fo_actSvc = new Fo_actService();
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();

			System.out.println(act_no);
			fo_actSvc.deleteFo_act(mem_ac, act_no);

		} else if(action.equals("updateActPair")){
		//修改活動狀態
			act_pairSvc = new Act_pairService();
			ActVO actVO = new ActVO();
			Act_pairVO act_pairVO = new Act_pairVO();
			String checkState = "false";
			String act_no = jsonObject.get("act_no").getAsString();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			actVO = actSvc.getOneAct(act_no);
			
			act_pairVO = act_pairSvc.getOneAct_pair(act_no, mem_ac);
			if (actVO != null){
				checkState = act_pairVO.getChk_state();
			}
			if (checkState.equals("未報到")){
				checkState = "已報到";
				act_pairVO.setChk_state(checkState);
				act_pairSvc.update(act_pairVO);
			}
			
			System.out.println(act_pairVO);
			
			outStr = gson.toJson(act_pairVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			//public Set<Act_pairVO> getAct_pairByAct_no1(String ACT_NO){
		
		} else if(action.equals("getMemPair")){
		//取得所有參加會員
			act_pairSvc = new Act_pairService();
			ActVO actVO = new ActVO();
			getPair_actVO = new ArrayList<Act_pairVO>();
			getMem_pair_actVO = new HashSet<Act_pairVO>();
			Act_pairVO act_pairVO = new Act_pairVO();
			String act_no = jsonObject.get("act_no").getAsString();
			getMem_pair_actVO = actSvc.getAct_pairByAct_no1(act_no);
			
			Iterator<Act_pairVO> pairListIt = getMem_pair_actVO.iterator();
			
			while (pairListIt.hasNext()){
				getPair_actVO.add((Act_pairVO) pairListIt.next());	
			}
	
			outStr = gson.toJson(getPair_actVO);
			System.out.println(outStr);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			//public Set<Act_pairVO> getAct_pairByAct_no1(String ACT_NO){
		
		}
		
		
		else{
			doGet(request, response);
		}
		
	}

}
