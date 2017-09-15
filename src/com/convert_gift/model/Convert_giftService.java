package com.convert_gift.model;

import java.sql.Date;
import java.util.List;

public class Convert_giftService {

	private Convert_giftDAO_interface dao;
	
	public Convert_giftService(){
		dao=new Convert_giftJNDIDAO();
		
		
		
	}
	
	public Convert_giftVO addConvert_gift(String apply_no,String mem_ac,String apply_name,String apply_phone,String gift_no,Date apply_date,String apply_stat,String apply_add,Date send_date,String send_no){
		
		Convert_giftVO convert_gift_VO=new Convert_giftVO();
		convert_gift_VO.setApply_no(apply_no);
		convert_gift_VO.setMem_ac(mem_ac);
		convert_gift_VO.setApply_name(apply_name);
		convert_gift_VO.setApply_phone(apply_phone);
		convert_gift_VO.setGift_no(gift_no);
		convert_gift_VO.setApply_date(apply_date);
		convert_gift_VO.setApply_stat(apply_stat);
		convert_gift_VO.setApply_add(apply_add);
		convert_gift_VO.setSend_date(send_date);
		convert_gift_VO.setSend_no(send_no);
		dao.insert(convert_gift_VO);
		return convert_gift_VO;
	}
	public Convert_giftVO updateConvert_gift(String apply_no,String mem_ac,String apply_name,String apply_phone,String gift_no,Date apply_date,String apply_stat,String apply_add,Date send_date,String send_no){
		
		Convert_giftVO convert_gift_VO=new Convert_giftVO();
		convert_gift_VO.setApply_no(apply_no);
		convert_gift_VO.setMem_ac(mem_ac);
		convert_gift_VO.setApply_name(apply_name);
		convert_gift_VO.setApply_phone(apply_phone);
		convert_gift_VO.setGift_no(gift_no);
		convert_gift_VO.setApply_date(apply_date);
		convert_gift_VO.setApply_stat(apply_stat);
		convert_gift_VO.setApply_add(apply_add);
		convert_gift_VO.setSend_date(send_date);
		convert_gift_VO.setSend_no(send_no);
		dao.update(convert_gift_VO);
		return convert_gift_VO;
	}
	
	public void deleteConvert_gift(String apply_no){
		dao.delete(apply_no);
	}
	public Convert_giftVO getOneConvert_gift(String apply_no){
		return dao.findByPrimaryKey(apply_no);
	}
	
	public List<Convert_giftVO> getAll(){
		return dao.getAll();
	}
	
	
}
