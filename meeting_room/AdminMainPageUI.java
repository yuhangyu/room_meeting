package meeting_room;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AdminMainPageUI extends JFrame {
	MyInfoMgr mgr;
	MyInfoBean bean = new MyInfoBean();
	
	String id;
	String name;
	
	JLabel adm_lb = new JLabel("������ :");
	
	JButton Sales_status = new JButton("���� ��Ȳ");
	JButton	Order_history = new JButton("�ֹ� ��Ȳ");
	JButton Reserve = new JButton("���� ����");
	JButton Member_info = new JButton("ȸ�� ���� ����");
	
	public AdminMainPageUI() {
		id = LoginUI.getLoginID();
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
		name = bean.getName();
		
		adm_lb.setText("������ : "+name);
		
		Container c = getContentPane();
		c.setLayout(null);
		setSize(1020, 600);
		setTitle("������ ������");
		adm_lb.setBounds(200, 50, 300, 200);
		Sales_status.setBounds(600, 50, 300, 150);
		Order_history.setBounds(50, 300, 300, 150);
		Reserve.setBounds(350, 300, 300, 150);
		Member_info.setBounds(650, 300, 300, 150);
		
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
	
	public static void main(String[] args) {
		AdminMainPageUI admp = new AdminMainPageUI();
	}

	

}
