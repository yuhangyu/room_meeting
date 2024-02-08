package meeting_room;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
	
	//������ ���� ��� �����
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
	
	//���� ����Ʈ
	public Vector<ReserveBean> reserveAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReserveBean> vlist = new Vector<ReserveBean>();
		try {
			con = pool.getConnection();
			sql = "select * from reserve";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReserveBean bean = new ReserveBean();
				Timestamp dtime = rs.getTimestamp("resv_time");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				bean.setResvid(rs.getString("resv_id"));
				bean.setResvname(rs.getString("resv_name"));
				bean.setResvphone(rs.getString("resv_tel"));
				bean.setResvroom(rs.getString("resv_room"));
				bean.setResvtime(format.format(dtime));
				bean.setResvusetime(rs.getInt("resv_usetime"));
				bean.setResvperson(rs.getInt("resv_person"));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//���� ����Ʈ
	public Vector<FoodBean> foodAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<FoodBean> vlist = new Vector<FoodBean>();
		try {
			con = pool.getConnection();
			sql = "select * from food";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FoodBean bean = new FoodBean();
				bean.setFood(rs.getString("food"));
				bean.setFname(rs.getString("food_name"));
				bean.setFprice(rs.getInt("food_price"));
				bean.setFtype(rs.getString("food_type"));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//������� ����Ʈ
	public Vector<GameBean> gameAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<GameBean> vlist = new Vector<GameBean>();
		try {
			con = pool.getConnection();
			sql = "select * from game";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				GameBean bean = new GameBean();
				bean.setGame(rs.getString("game"));
				bean.setGname(rs.getString("game_name"));
				bean.setGprice(rs.getInt("game_price"));
				bean.setGstate(rs.getBoolean("game_state"));
				bean.setGtype(rs.getString("game_type"));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//�� ����Ʈ
	public Vector<RoomBean> roomAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<RoomBean> vlist = new Vector<RoomBean>();
		try {
			con = pool.getConnection();
			sql = "select * from room";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RoomBean bean = new RoomBean();
				bean.setRoom(rs.getString("room"));
				bean.setRperson(rs.getInt("room_person"));
				bean.setRprice(rs.getInt("room_price"));
				bean.setRadd(rs.getInt("room_add"));
				bean.setRstate(rs.getBoolean("room_state"));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	//�� select
	public RoomBean room(String rid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		RoomBean bean = new RoomBean();
		try {
			con = pool.getConnection();
			sql = "select * from room where room = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rid); //ù��° ?�� �Ű����� id �� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setRoom(rs.getString(1));
				bean.setRperson(rs.getInt(2));
				bean.setRprice(rs.getInt(3));
				bean.setRadd(rs.getInt(4));
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
	
	//�����ϱ�
	public boolean reserve(ReserveBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert into reserve values (NULL, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  bean.getResvid());
			pstmt.setString(2,  bean.getResvname());
			pstmt.setString(3,  bean.getResvphone());
			pstmt.setString(4,  bean.getResvroom());
			pstmt.setString(5,  bean.getResvtime());
			pstmt.setInt(6,  bean.getResvusetime());
			pstmt.setInt(7,  bean.getResvperson());
			if (pstmt.executeUpdate() == 1) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
}