package com.gift_data.model;
import java.util.List;
import java.util.Set;

import com.convert_gift.model.Convert_giftVO;



public interface Gift_dataDAO_interface {
	 public void insert(Gift_dataVO gift_data_VO);
     public void update(Gift_dataVO gift_data_VO);
     public void delete(String GIFT_NO);
     public Gift_dataVO findByPrimaryKey(String GIFT_NO);
     public List<Gift_dataVO> getAll();
     
     
     // 0914修改
     public Set<Convert_giftVO> getConvert_giftByGift_no(String GIFT_NO);
     
     
}
