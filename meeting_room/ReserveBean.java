package meeting_room;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserveBean {

	int reserve;
	String resvid;
	String resvname;
	String resvphone;
	String resvroom;
	String resvtime;
	int resvusetime;
	int resvperson;
	
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	public String getResvid() {
		return resvid;
	}
	public void setResvid(String resvid) {
		this.resvid = resvid;
	}
	public String getResvname() {
		return resvname;
	}
	public void setResvname(String resvname) {
		this.resvname = resvname;
	}
	public String getResvphone() {
		return resvphone;
	}
	public void setResvphone(String resvphone) {
		this.resvphone = resvphone;
	}
	public String getResvroom() {
		return resvroom;
	}
	public void setResvroom(String resvroom) {
		this.resvroom = resvroom;
	}
	public String getResvtime() {
		return resvtime;
	}
	public void setResvtime(Date resvtime) {
		Date date = resvtime;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format( date );
		this.resvtime = dateString;
	}
	public int getResvusetime() {
		return resvusetime;
	}
	public void setResvusetime(int resvusetime) {
		this.resvusetime = resvusetime;
	}
	public int getResvperson() {
		return resvperson;
	}
	public void setResvperson(int resvperson) {
		this.resvperson = resvperson;
	}
}