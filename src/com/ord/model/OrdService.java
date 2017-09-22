package com.ord.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.ord_list.model.Ord_listVO;

public class OrdService {
	private OrdDAO_interface dao;
	
	public OrdService() {
		dao = new OrdDAO();
	}
	
	public OrdVO addOrd(String ord_no, String mem_ac, Integer send_fee, Integer total_pay, String ord_name, String ord_phone,
			String ord_add, String pay_info, String ord_stat, Date ord_date, Date pay_date, Date pay_chk_date,
			Date send_date, String send_id) {

		OrdVO ordVO = new OrdVO();
		ordVO.setOrd_no(ord_no);
		ordVO.setMem_ac(mem_ac);
		ordVO.setSend_fee(send_fee);
		ordVO.setTotal_pay(total_pay);
		ordVO.setOrd_name(ord_name);
		ordVO.setOrd_phone(ord_phone);
		ordVO.setOrd_add(ord_add);
		ordVO.setPay_info(pay_info);
		ordVO.setOrd_stat(ord_stat);
		ordVO.setOrd_date(ord_date);
		ordVO.setPay_date(pay_date);
		ordVO.setPay_chk_date(pay_chk_date);
		ordVO.setSend_date(send_date);
		ordVO.setSend_id(send_id);

		dao.insert(ordVO);
		
		return ordVO;
	}
	
	public void addOrd(OrdVO ordVO){
		dao.insert(ordVO);
	}
	
	public OrdVO updateOrd(String ord_no, String mem_ac, Integer send_fee, Integer total_pay, String ord_name, String ord_phone,
			String ord_add, String pay_info, String ord_stat, Date ord_date, Date pay_date, Date pay_chk_date,
			Date send_date, String send_id) {

		OrdVO ordVO = new OrdVO();
		ordVO.setOrd_no(ord_no);
		ordVO.setMem_ac(mem_ac);
		ordVO.setSend_fee(send_fee);
		ordVO.setTotal_pay(total_pay);
		ordVO.setOrd_name(ord_name);
		ordVO.setOrd_phone(ord_phone);
		ordVO.setOrd_add(ord_add);
		ordVO.setPay_info(pay_info);
		ordVO.setOrd_stat(ord_stat);
		ordVO.setOrd_date(ord_date);
		ordVO.setPay_date(pay_date);
		ordVO.setPay_chk_date(pay_chk_date);
		ordVO.setSend_date(send_date);
		ordVO.setSend_id(send_id);

		dao.update(ordVO);
		
		return ordVO;
	}
	
	public void updateOrd(OrdVO ordVO){
		dao.update(ordVO);
	}
	

	public void deleteOrd(String ord_no) {
		dao.delete(ord_no);
	}

	public OrdVO getOneOrd(String ord_no) {
		return dao.findByPrimaryKey(ord_no);
	}

	public List<OrdVO> getAll() {
		return dao.getAll();
	}
	
	public Set<Ord_listVO> getOrd_listByOrd(String ord_no){
		return dao.getOrd_listByOrd(ord_no);
	}
	
	
	public List<OrdVO> getOrdByMem_ac(String mem_ac){
		return dao.getOrdByMem_ac(mem_ac);
	}
	
	
	
	
	
}
