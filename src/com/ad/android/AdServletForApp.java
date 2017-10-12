package com.ad.android;

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

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.beanlife.android.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.review.model.ReviewService;

/**
 * Servlet implementation class AdServletForApp
 */
@WebServlet("/AdServletForApp")
public class AdServletForApp extends HttpServlet {
private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private AdService adSvc;
	private List<AdVO> adList;
	private AdVO adVO ;
	private byte[] adImage;

    
    
	@Override
	public void init() throws ServletException {
		super.init();	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		request.setCharacterEncoding("UTF-8");
		
		adSvc = new AdService();
		
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

			adList = new ArrayList<AdVO>();
			adList = adSvc.getNowAdNoImg();
			System.out.println("getAd" + adList.toString());

			outStr = gson.toJson(adList);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println(outStr);
			
		} else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			String ad_no = jsonObject.get("ad_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();

			adImage = adSvc.getNowAdImg(ad_no);
			if(adImage != null){	

				adImage = ImageUtil.shrink(adImage, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(adImage.length);
			}
			
			os.write(adImage);
			os.flush();
			os.close();
			
		} else{
			doGet(request, response);
		}

	}
}
