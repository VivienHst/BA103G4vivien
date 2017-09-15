package com.convert_gift.model;

import java.util.List;



public interface Convert_giftDAO_interface {
	 public void insert(Convert_giftVO convert_gift_VO);
     public void update(Convert_giftVO convert_gift_VO);
     public void delete(String APPLY_NO);
     public Convert_giftVO findByPrimaryKey(String APPLY_NO);
     public List<Convert_giftVO> getAll();
     
}
