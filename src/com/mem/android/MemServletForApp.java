package com.mem.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beanlife.android.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mem.model.MemService;
import com.mem.model.MemVO;

/**
 * Servlet implementation class MemServletForApp
 */

@WebServlet("/MemServletForApp")
public class MemServletForApp extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

    private MemService memSvc;
    private List<MemVO> list;
    private MemVO memVO;
    private byte[] mem_pic;
    private boolean logInPrem;
    

    public MemServletForApp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		memSvc = new MemService();
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line =  null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		br.close();
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		String outStr = "";
		System.out.println("action : " + action);
		if(action.equals("getOneMem")){
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			memVO = memSvc.findByPrimaryKeyNoImg(mem_ac);
			outStr = gson.toJson(mem_ac);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(outStr);
		}else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			mem_pic = memSvc.getImageByPK(mem_ac);
			if(mem_pic != null){
				mem_pic = ImageUtil.shrink(mem_pic, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(mem_pic.length);
			}
			os.write(mem_pic);
			os.flush();
			os.close();
			
		}
		else if(action.equals("logIn")){
			logInPrem = false;
			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println(mem_ac);
			String mem_psw = jsonObject.get("mem_psw").getAsString();
			System.out.println(mem_psw);
			memVO = memSvc.findByPrimaryKeyNoImg(mem_ac);
			System.out.println(memVO.toString());
			
			if(mem_psw.equals(memVO.getMem_pwd())){
				logInPrem = true;
			}
			outStr = gson.toJson(logInPrem);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out=response.getWriter();
			out.println(logInPrem);
		}
		
		else{
			doGet(request, response);
		}
		
	}

}