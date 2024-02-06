package meeting_room;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class MyInfoMgr {

	private DBConnection pool;

	public MyInfoMgr() {
		try {
			pool = DBConnection.getInstance();
			Connection con = pool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��� ����Ʈ: select
	public Vector<MyInfoBean> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MyInfoBean> vlist = new Vector<MyInfoBean>();
		try {
			//Connection ��ü�� pool ���� ������
			con = pool.getConnection();
			sql = "select * from member where member_level=1";
			pstmt = con.prepareStatement(sql);
			//rs: select ���� ��� ��ü
			rs = pstmt.executeQuery(); //���� SQL�� ����
			
			while (rs.next()) { //Ŀ���� ������ ����
				MyInfoBean bean = new MyInfoBean();
				bean.setID(rs.getString("member_id"));
				bean.setPW(rs.getString("member_pw"));
				bean.setName(rs.getString("member_name"));
				bean.setPhone(rs.getString("member_tel"));
				bean.setMoney(rs.getInt("member_money"));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//con �ݳ�, pstmt, rs �� close
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//�Ѱ��� ���ڵ�: select
	public MyInfoBean select(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MyInfoBean bean = new MyInfoBean();
		try {
			con = pool.getConnection();
			sql = "select * from member where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id); //ù��° ?�� �Ű����� id �� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setID(rs.getString(1));
				bean.setPW(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhone(rs.getString(4));
				bean.setMoney(rs.getInt(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
	//���� ����
	public boolean update(MyInfoBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update member set member_pw=?, member_name=?, member_tel=? where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  bean.getPW());
			pstmt.setString(2,  bean.getName());
			pstmt.setString(3,  bean.getPhone());
			pstmt.setString(4, bean.getID());
			if (pstmt.executeUpdate() == 1) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	//�ݾ�����
	public boolean charge(MyInfoBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update member set member_money=? where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  bean.getMoney());
			pstmt.setString(2, bean.getID());
			if (pstmt.executeUpdate() == 1) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
}