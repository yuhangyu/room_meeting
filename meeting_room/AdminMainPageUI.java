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
	
	JButton Sales_status = new JButton("매출 현황");
	JButton	Order_history = new JButton("주문 현황");
	JButton Reserve = new JButton("예약 정보");
	JButton Member_info = new JButton("회원 정보 관리");
	
	public AdminMainPageUI() {
		id = LoginUI.getLoginID();
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
		name = bean.getName();
		
		adm_lb.setText("관리자 : "+name);
		
		Container c = getContentPane();
		c.setLayout(null);
		setSize(1020, 600);
		setTitle("관리자 페이지");
		adm_lb.setBounds(200, 50, 300, 200);
		Sales_status.setBounds(650, 50, 300, 150);
		Order_history.setBounds(50, 300, 300, 150);
		Reserve.setBounds(350, 300, 300, 150);
		Member_info.setBounds(650, 300, 300, 150);
		
		Sales_status.addActionListener(this);
		Order_history.addActionListener(this);
		Reserve.addActionListener(this);
		Member_info.addActionListener(this);
		
		
		c.add(adm_lb);
		c.add(Sales_status);
		c.add(Order_history);
		c.add(Reserve);
		c.add(Member_info);
		
		Font font = new Font("Dialog", Font.BOLD, 30);
		
		adm_lb.setFont(font);
		Sales_status.setFont(font);
		Order_history.setFont(font);
		Reserve.setFont(font);
		Member_info.setFont(font);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		setResizable(false);
			
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == Sales_status) {
			
		}else if(obj == Order_history) {
			
		}else if(obj == Reserve) {
			ReserveInfoUI reserveinfo_ui = new ReserveInfoUI();
		}else if(obj == Member_info) {
			MemberInfoUI MIUI = new MemberInfoUI();
		}
	}
	public static void main(String[] args) {
		AdminMainPageUI admin_ui = new AdminMainPageUI();
	}

	

}
