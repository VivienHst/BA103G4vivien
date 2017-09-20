package com.mem.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.prod.model.ProdJDBCDAO;

import java.sql.*;

public class MemJDBCDAO implements MemDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@54.92.7.228:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO MEM VALUES "
			+ "(?,'U'||mem_NO_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	private static final String UPDATE = "UPDATE MRM SET "
			+ "MEM_PWD +?,"
			+ "MEM_LNAME +?,"
			+ "MEM_FNAME +?,"
			+ "MEM_EMAIL +?,"
			+ "MEM_PHONE +?,"
			+ "MEM_ADD +?,"
			+ "MEM_PIC +?,"
			+ "MEM_SET +?,"
			+ "MEM_TOTAL_PT +?,"
			+ "MEM_PT +?,"
			+ "GRADE_NO +?,"
			+ "MEM_STAT +?,"
			+ "MEM_STAT_CDATE +?,"
			+ "MEM_REG_DATE +?,"
			+ "WHERE MEM_AC =?";			

				
	private static final String DELETE = "DELETE FROM MEM WHERE MEM_AC = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM MEM";
	private static final String GET_ONE_STMT = "SELECT * FROM MEM WHERE MEM_AC = ?";
	
	
	@Override
	public void insert(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memVO.getMem_ac());
			pstmt.setString(2, memVO.getMem_pwd());
			pstmt.setString(3, memVO.getMem_lname());
			pstmt.setString(4, memVO.getMem_fname());
			pstmt.setString(5, memVO.getMem_email());
			pstmt.setString(6, memVO.getMem_phone());
			pstmt.setString(7, memVO.getMem_add());
			pstmt.setBytes(8, memVO.getMem_pic());
			pstmt.setString(9, memVO.getMem_set());
			pstmt.setInt(10, memVO.getMem_total_pt());
			pstmt.setInt(11, memVO.getMem_pt());
			pstmt.setInt(12, memVO.getGrade_no());
			pstmt.setString(13, memVO.getMem_stat());
			pstmt.setDate(14, memVO.getMem_stat_cdate());
			pstmt.setDate(15, memVO.getMem_reg_date());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}


	@Override
	public void update(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memVO.getMem_pwd());
			pstmt.setString(2, memVO.getMem_lname());
			pstmt.setString(3, memVO.getMem_fname());
			pstmt.setString(4, memVO.getMem_email());
			pstmt.setString(5, memVO.getMem_phone());
			pstmt.setString(6, memVO.getMem_add());
			pstmt.setBytes(7, memVO.getMem_pic());
			pstmt.setString(8, memVO.getMem_set());
			pstmt.setInt(9, memVO.getMem_total_pt());
			pstmt.setInt(10, memVO.getMem_pt());
			pstmt.setInt(11, memVO.getGrade_no());
			pstmt.setString(12, memVO.getMem_stat());
			pstmt.setDate(13, memVO.getMem_stat_cdate());
			pstmt.setDate(14, memVO.getMem_reg_date());
			pstmt.setString(15, memVO.getMem_ac());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			
		}
		 catch (ClassNotFoundException e) {
				e.printStackTrace();
		}finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}


	@Override
	public void delete(String mem_ac) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mem_ac);
			pstmt.executeUpdate();
			
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			if(con != null){
				try {
					con.rollback();
				} catch (SQLException exc){
					throw new RuntimeException("rollback error occured"
							+ exc.getMessage());
				}
			}
			throw new RuntimeException("A database error occured ."
					+ e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}
				catch(SQLException se){
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
	public MemVO findByPrimaryKey(String mem_ac) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();
			
			while (rs.next()){

				memVO = new MemVO();

				memVO.setMem_ac(rs.getString("mem_ac"));
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_pwd(rs.getString("mem_pwd"));
				memVO.setMem_lname(rs.getString("mem_lname"));
				memVO.setMem_fname(rs.getString("mem_fname"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setMem_set(rs.getString("mem_set"));
				memVO.setMem_total_pt(rs.getInt("mem_total_pt"));
				memVO.setMem_pt(rs.getInt("mem_pt"));
				memVO.setGrade_no(rs.getInt("mem_no"));
				memVO.setMem_stat(rs.getString("mem_stat"));
				memVO.setMem_stat_cdate(rs.getDate("mem_stat_cdate"));
				memVO.setMem_reg_date(rs.getDate("mem__reg_date"));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return memVO;
	}


	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				memVO = new MemVO();
				memVO.setMem_ac(rs.getString("mem_ac"));
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_pwd(rs.getString("mem_pwd"));
				memVO.setMem_lname(rs.getString("mem_lname"));
				memVO.setMem_fname(rs.getString("mem_fname"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setMem_set(rs.getString("mem_set"));
				memVO.setMem_total_pt(rs.getInt("mem_total_pt"));
				memVO.setMem_pt(rs.getInt("mem_pt"));
				memVO.setGrade_no(rs.getInt("mem_no"));
				memVO.setMem_stat(rs.getString("mem_stat"));
				memVO.setMem_stat_cdate(rs.getDate("mem_stat_cdate"));
				memVO.setMem_reg_date(rs.getDate("mem__reg_date"));	
				list.add(memVO);
			}
			
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	public static void main (String[] args) throws IOException{
		MemJDBCDAO dao = new MemJDBCDAO();
		//getByPrimaryKeyTest(dao);
//		insertTest(dao);
//      updateTest(dao);
//		dao.delete("testtest11");

	}
	
	public static void insertTest(MemJDBCDAO dao) throws IOException{
		MemVO memVO01 = new MemVO();
		memVO01.setMem_ac("testtest11");
		memVO01.setMem_pwd("test123");
		memVO01.setMem_lname("八");
		memVO01.setMem_fname("寶粥");
		memVO01.setMem_email("testtest@test.com");
		memVO01.setMem_phone("0912345789");
		memVO01.setMem_add("宜蘭縣壯圍鄉壯圍鄉美福路16號之1");
		memVO01.setMem_pic(getPictureByteArray("C:\\Users\\Java\\Desktop\\專題用圖片\\mem01.jpg"));
		memVO01.setMem_set("肯亞,半水洗,深焙");
		memVO01.setMem_total_pt(200);
		memVO01.setMem_pt(50);
		memVO01.setGrade_no(1);
		memVO01.setMem_stat("正常");
		memVO01.setMem_stat_cdate(null);
		memVO01.setMem_reg_date(java.sql.Date.valueOf("2017-09-20"));

		
		dao.insert(memVO01);
		System.out.println("新增一個會員");
	}
	
	public static void updateTest(MemJDBCDAO dao) throws IOException{
		MemVO memVO01 = new MemVO();
		memVO01.setMem_ac("testtest11");
		memVO01.setMem_pwd("333test");
		memVO01.setMem_lname("五");
		memVO01.setMem_fname("穀飯");
		memVO01.setMem_email("testtest@test.com");
		memVO01.setMem_phone("0912345789");
		memVO01.setMem_add("宜蘭縣壯圍鄉壯圍鄉美福路16號之1");
		memVO01.setMem_pic(getPictureByteArray("C:\\Users\\Java\\Desktop\\專題用圖片\\mem01.jpg"));
		memVO01.setMem_set("肯亞,半水洗,深焙");
		memVO01.setMem_total_pt(200);
		memVO01.setMem_pt(50);
		memVO01.setGrade_no(1);
		memVO01.setMem_stat("正常");
		memVO01.setMem_stat_cdate(null);
		memVO01.setMem_reg_date(java.sql.Date.valueOf("2017-09-20"));
		
		dao.update(memVO01);
		System.out.println("修改一個會員");
	}
	
	public static void getByPrimaryKeyTest(MemJDBCDAO dao){
		MemVO memVO = dao.findByPrimaryKey("testtest11");
		System.out.println(memVO.getMem_no() + ", ");
		System.out.println(memVO.getMem_ac() + ", ");
		System.out.println(memVO.getMem_pwd() + ", ");
		System.out.println(memVO.getMem_lname() + ", ");
		System.out.println(memVO.getMem_fname() + ", ");
		System.out.println(memVO.getMem_email() + ", ");
		System.out.println(memVO.getMem_phone() + ", ");
		System.out.println(memVO.getMem_add() + ", ");
		System.out.println(memVO.getMem_pic() + ", ");
		System.out.println(memVO.getMem_set() + ", ");
		System.out.println(memVO.getMem_total_pt() + ", ");
		System.out.println(memVO.getMem_pt() + ", ");
		System.out.println(memVO.getGrade_no() + ", ");
		System.out.println(memVO.getMem_stat() + ", ");
		System.out.println(memVO.getMem_stat_cdate() + ", ");
		System.out.println(memVO.getMem_reg_date() + ", ");
	}
	
	
		
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
	

}
