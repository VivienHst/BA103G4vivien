package com.store.model;

import java.util.List;
import java.util.Set;

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
 	 public StoreVO findByMem(String mem_ac);
     public List<StoreVO> getAll_stat(String store_stat);//後端選擇審核狀態
     public Set<ProdVO> getProdsByStore_no(String store_no);
     public Set<ProdVO> getProdsByStore_noNoImg(String store_no);

    
   

    

}
