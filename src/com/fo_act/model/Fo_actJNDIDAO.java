package com.fo_act.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Fo_actJNDIDAO implements Fo_actDAO_interface{

private static DataSource ds=null;
	
	static{
		Context ctx;
		try {
			ctx = new InitialContext();
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	private static final String INSERT_STMT ="insert into fo_act values(?,?,?)";
	private static final String GET_ALL_STMT ="select * from fo_act";
	private static final String GET_ONE_STMT="select * from fo_act where MEM_AC=? AND ACT_NO=?";
	private static final String DELETE = "delete from fo_act where MEM_AC=? AND ACT_NO=?";
	private static final String UPDATE ="update fo_act set FO_ACT_DATE=? where MEM_AC=? AND ACT_NO=?";
	private static final String GET_MEM_FO_ACT_STMT="select * from fo_act where MEM_AC=?";
	private static final String GET_COUNT_BY_ACT =
			"SELECT COUNT(*) FROM FO_ACT WHERE ACT_NO = ?";


	
	@Override
	public void insert(Fo_actVO fo_act_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, fo_act_VO.getMem_ac());
			pstmt.setString(2, fo_act_VO.getAct_no());
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		
		
		
		
	}
	@Override
	public void update(Fo_actVO fo_act_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setDate(1,fo_act_VO.getFo_act_date());
			pstmt.setString(2, fo_act_VO.getMem_ac());
			pstmt.setTimestamp(3, (fo_act_VO.getFo_act_date()!=null)? new Timestamp(fo_act_VO.getFo_act_date().getTime()):null);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		
	}
	@Override
	public void delete(String MEM_AC, String ACT_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1,MEM_AC);
			pstmt.setString(2, ACT_NO);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	@Override
	public Fo_actVO findByPrimaryKey(String MEM_AC, String ACT_NO) {
		// TODO Auto-generated method stub
		 Fo_actVO fo_act_vo=null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		 
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setString(1, MEM_AC);
				pstmt.setString(2, ACT_NO);
				 rs=pstmt.executeQuery();
				 
				 while(rs.next()){
					 fo_act_vo=new Fo_actVO();
					 fo_act_vo.setMem_ac(rs.getString("MEM_AC"));
					 fo_act_vo.setAct_no(rs.getString("ACT_NO"));
					 fo_act_vo.setFo_act_date(rs.getDate("FO_ACT_DATE"));
					 
				 }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			
			}
			return  fo_act_vo;
		 
	}
	@Override
	public List<Fo_actVO> getAll() {
		List<Fo_actVO> list=new ArrayList<Fo_actVO>();
		 Fo_actVO fo_act_vo=null;
		 Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					fo_act_vo=new Fo_actVO();
					fo_act_vo.setMem_ac(rs.getString("MEM_AC"));
					fo_act_vo.setAct_no(rs.getString("ACT_NO"));
					fo_act_vo.setFo_act_date(rs.getDate("FO_ACT_DATE"));
					list.add(fo_act_vo);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return list;
			
			
	}
	
	@Override
	public List<Fo_actVO> getFo_actByMem_ac(String mem_ac) {
		// TODO Auto-generated method stub
		List<Fo_actVO> list=new ArrayList<Fo_actVO>();
		 Fo_actVO fo_act_vo=null;
		 Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_MEM_FO_ACT_STMT);
					pstmt.setString(1, mem_ac);

					rs=pstmt.executeQuery();
					
					while(rs.next()){
						fo_act_vo=new Fo_actVO();
						fo_act_vo.setMem_ac(rs.getString("MEM_AC"));
						fo_act_vo.setAct_no(rs.getString("ACT_NO"));
						fo_act_vo.setFo_act_date(rs.getDate("FO_ACT_DATE"));
						list.add(fo_act_vo);
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return list;
	}
	
	
	@Override
	public int countByAct(String act_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Integer count = 0;
		
		try {
	
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_COUNT_BY_ACT);		
			pstmt.setString(1, act_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				count = rs.getInt(1);
			}
	
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return count;
	}

}
