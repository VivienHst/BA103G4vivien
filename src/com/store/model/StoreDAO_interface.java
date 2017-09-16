package com.store.model;

import java.util.List;

import com.prod.model.ProdVO;



public interface StoreDAO_interface {
	 public void insert(StoreVO storeVO);
     public void update(StoreVO storeVO);
     public void update_stat(StoreVO storeVO);
     public void delete(String store_no);
     public StoreVO findByPrimaryKey(String store_no);
     public List<StoreVO> getAll();
     public List<StoreVO> getAllNoImg();
 	 public List<byte[]> getImageByPK(String stores_no); 
 	 public StoreVO findByPrimaryKeyNoImg(String store_no);
    

}
