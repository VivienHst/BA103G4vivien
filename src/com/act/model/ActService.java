package com.act.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.act_comm.model.Act_commVO;
import com.act_pair.model.Act_pairVO;
import com.fo_act.model.Fo_actVO;
import com.prod.model.ProdVO;

public class ActService {

	private ActDAO_interface dao;
	
	public ActService(){
		dao= new ActJNDIDAO();
	}
	
	public ActVO addAct(String act_no,String mem_ac,String org_cont,String act_name, int min_mem,int max_mem,int mem_count,Date act_op_date,Date act_ed_date,Date dl_date,Date fd_date,String act_add,String act_add_lat,String act_add_lon,String act_cont,String act_tag,int act_fee,String pay_way,byte[] act_pic1,byte[] act_pic2,byte[] act_pic3,String act_stat,String re_cont,Date review_ed_date){
		
		
		ActVO ActVO=new ActVO();
		ActVO.setAct_no(act_no);
		ActVO.setMem_ac(mem_ac);
		ActVO.setOrg_cont(org_cont);
		ActVO.setAct_name(act_name);
		ActVO.setMin_mem(min_mem);
		ActVO.setMax_mem(max_mem);
		ActVO.setMem_count(mem_count);
		ActVO.setAct_op_date(act_op_date);
		ActVO.setAct_ed_date(act_ed_date);
		ActVO.setDl_date(dl_date);
		ActVO.setFd_date(fd_date);
		ActVO.setAct_add(act_add);
		ActVO.setAct_add_lat(act_add_lat);
		ActVO.setAct_add_lon(act_add_lon);
		ActVO.setAct_cont(act_cont);
		ActVO.setAct_tag(act_tag);
		ActVO.setAct_fee(act_fee);
		ActVO.setPay_way(pay_way);
		ActVO.setAct_pic1(act_pic1);
		ActVO.setAct_pic2(act_pic2);
		ActVO.setAct_pic3(act_pic3);
		ActVO.setAct_stat(act_stat);
		ActVO.setRe_cont(re_cont);
		ActVO.setReview_ed_date(review_ed_date);
		
		dao.insert(ActVO);
		return ActVO;
	}
	
public ActVO updateAct(String act_no,String mem_ac,String org_cont,String act_name, int min_mem,int max_mem,int mem_count,Date act_op_date,Date act_ed_date,Date dl_date,Date fd_date,String act_add,String act_add_lat,String act_add_lon,String act_cont,String act_tag,int act_fee,String pay_way,byte[] act_pic1,byte[] act_pic2,byte[] act_pic3,String act_stat,String re_cont,Date review_ed_date){
		
		ActVO ActVO=new ActVO();
		ActVO.setAct_no(act_no);
		ActVO.setMem_ac(mem_ac);
		ActVO.setOrg_cont(org_cont);
		ActVO.setAct_name(act_name);
		ActVO.setMin_mem(min_mem);
		ActVO.setMax_mem(max_mem);
		ActVO.setMem_count(mem_count);
		ActVO.setAct_op_date(act_op_date);
		ActVO.setAct_ed_date(act_ed_date);
		ActVO.setDl_date(dl_date);
		ActVO.setFd_date(fd_date);
		ActVO.setAct_add(act_add);
		ActVO.setAct_add_lat(act_add_lat);
		ActVO.setAct_add_lon(act_add_lon);
		ActVO.setAct_cont(act_cont);
		ActVO.setAct_tag(act_tag);
		ActVO.setAct_fee(act_fee);
		ActVO.setPay_way(pay_way);
		ActVO.setAct_pic1(act_pic1);
		ActVO.setAct_pic2(act_pic2);
		ActVO.setAct_pic3(act_pic3);
		ActVO.setAct_stat(act_stat);
		ActVO.setRe_cont(re_cont);
		ActVO.setReview_ed_date(review_ed_date);
		
		dao.update(ActVO);
		return ActVO;
	}
	
public void deleteAct(String act_no){
	dao.delete(act_no);
}

public ActVO getOneAct(String act_no) {
	return dao.findByPrimaryKey(act_no);
}

public List<ActVO> getAll() {
	return dao.getAll();
}

public Set<Act_commVO> getAct_commByAct_no(String ACT_NO){
	return dao.getAct_commByAct_no(ACT_NO);
}
public Set<Act_pairVO> getAct_pairByAct_no1(String ACT_NO){
	
	return dao.getAct_pairByAct_no(ACT_NO);
}
public Set<Fo_actVO> getFo_actByAct_no(String ACT_NO){
	
	return dao.getFo_actByAct_no(ACT_NO);
}

public List<ActVO> getAllNoImg() {
	return dao.getAllNoImg();

}

public List<byte[]> getImageByPK(String act_no) {
	return dao.getImageByPK(act_no);
}



}
