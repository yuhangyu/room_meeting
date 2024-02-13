package meeting_room;

public class OrderInfoBean {
	//주문 현황
	String room_no;
	String ordertime;
	int totalprice;
	//주문 현황 디테일
	String foodtype;
	int foodcount;
	int foodprice;
	String gametype;
	int gameprice;
	String foodrequest;
	boolean foodstate;
	
	public boolean isFoodstate() {
		return foodstate;
	}
	public void setFoodstate(boolean foodstate) {
		this.foodstate = foodstate;
	}
	public String getFoodrequest() {
		return foodrequest;
	}
	public void setFoodrequest(String foodrequest) {
		this.foodrequest = foodrequest;
	}
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	public String getFoodtype() {
		return foodtype;
	}
	public void setFoodtype(String foodtype) {
		this.foodtype = foodtype;
	}
	public int getFoodcount() {
		return foodcount;
	}
	public void setFoodcount(int foodcount) {
		this.foodcount = foodcount;
	}
	public int getFoodprice() {
		return foodprice;
	}
	public void setFoodprice(int foodprice) {
		this.foodprice = foodprice;
	}
	public String getGametype() {
		return gametype;
	}
	public void setGametype(String gametype) {
		this.gametype = gametype;
	}
	public int getGameprice() {
		return gameprice;
	}
	public void setGameprice(int gameprice) {
		this.gameprice = gameprice;
	}

}
