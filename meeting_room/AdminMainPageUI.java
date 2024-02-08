package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AdminMainPageUI extends JFrame implements ActionListener{
	MyInfoMgr mgr;
	MyInfoBean bean = new MyInfoBean();
	
	String id;
	String name;
	
	JLabel adm_lb = new JLabel("관리자 :");
	
	JButton Sales_state = new JButton("매출 현황");
	JButton	Order_history = new JButton("주문 현황");
	JButton Reserve = new JButton("예약 정보");
	JButton Member_info = new JButton("회원 정보 관리");
	
	JButton Log_out = new JButton("로그 아웃");
	
	public AdminMainPageUI() {
		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
		name = bean.getName();
		
		adm_lb.setText("관리자 : "+name);
		
		Container c = getContentPane();
		c.setLayout(null);
		setSize(1020, 600);
		setTitle("관리자 페이지");
		adm_lb.setBounds(200, 50, 300, 200);
		Sales_state.setBounds(650, 50, 300, 150);
		Order_history.setBounds(50, 300, 300, 150);
		Reserve.setBounds(350, 300, 300, 150);
		Member_info.setBounds(650, 300, 300, 150);
		Log_out.setBounds(50, 200, 150, 100);
		
		Sales_state.addActionListener(this);
		Order_history.addActionListener(this);
		Reserve.addActionListener(this);
		Member_info.addActionListener(this);
		Log_out.addActionListener(this);
		
		
		c.add(adm_lb);
		c.add(Sales_state);
		c.add(Order_history);
		c.add(Reserve);
		c.add(Member_info);
		c.add(Log_out);
		
		Font font = new Font("Dialog", Font.BOLD, 30);
		
		adm_lb.setFont(font);
		Sales_state.setFont(font);
		Order_history.setFont(font);
		Reserve.setFont(font);
		Member_info.setFont(font);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == Sales_state) {
			
		}else if(obj == Order_history) {
			OrderInfo orderinfo = new OrderInfo();
		}else if(obj == Reserve) {
			ReserveInfoUI reserveinfo_ui = new ReserveInfoUI();
		}else if(obj == Member_info) {
			MemberInfoUI MIUI = new MemberInfoUI();
		}
		else if(obj == Log_out) {
			this.dispose();
			LoginUI li = new LoginUI();
		}
	}
	public static void main(String[] args) {
		AdminMainPageUI admin_ui = new AdminMainPageUI();
	}
}