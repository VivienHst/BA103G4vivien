package com.fo_act.model;

import java.util.List;


public interface Fo_actDAO_interface {
	 public void insert(Fo_actVO fo_act_VO);
     public void update(Fo_actVO fo_act_VO);
     public void delete(String MEM_AC,String ACT_NO);
     public Fo_actVO findByPrimaryKey(String MEM_AC,String ACT_NO);
     public List<Fo_actVO> getAll();
     
     //查詢會員追蹤的活動
     public List<Fo_actVO> getFo_actByMem_ac(String mem_ac);
     
     //查詢活動追蹤數
     public int countByAct(String act_no);
    
}
