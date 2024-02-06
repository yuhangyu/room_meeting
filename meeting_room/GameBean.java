package meeting_room;

public class GameBean {
	
	private String game;
	private String gname;
	private int gprice;
	private boolean gstate;
	
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public int getGprice() {
		return gprice;
	}
	public void setGprice(int gprice) {
		this.gprice = gprice;
	}
	public boolean isGstate() {
		return gstate;
	}
	public void setGstate(boolean gstate) {
		this.gstate = gstate;
	}
}