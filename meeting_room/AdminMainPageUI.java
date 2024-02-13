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
	
	JLabel adm_lb = new JLabel("������ :");
	
	JButton Sales_state = new JButton("���� ��Ȳ");
	JButton	OrderInfo = new JButton("�ֹ� ��Ȳ");
	JButton Reserve = new JButton("���� ����");
	JButton Member_info = new JButton("ȸ�� ���� ����");
	
	JButton Log_out = new JButton("�α� �ƿ�");
	
	public AdminMainPageUI() {
		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
		name = bean.getName();
		
		adm_lb.setText("������ : "+ name);
		
		Container c = getContentPane();
		c.setLayout(null);
		setSize(1020, 600);
		setTitle("������ ������");
		
		adm_lb.setBounds(80, 50, 300, 200);
		Sales_state.setBounds(650, 50, 300, 150);
		OrderInfo.setBounds(350, 50, 300, 150);
		Reserve.setBounds(350, 300, 300, 150);
		Member_info.setBounds(650, 300, 300, 150);
		Log_out.setBounds(80, 300, 200, 150);
		
		Sales_state.addActionListener(this);
		OrderInfo.addActionListener(this);
		Reserve.addActionListener(this);
		Member_info.addActionListener(this);
		Log_out.addActionListener(this);
				
		c.add(adm_lb);
		c.add(Sales_state);
		c.add(OrderInfo);
		c.add(Reserve);
		c.add(Member_info);
		c.add(Log_out);
		
		Font font = new Font("Dialog", Font.BOLD, 30);
		Font font1 = new Font("Dialog", Font.BOLD, 27);
		
		adm_lb.setFont(font);
		Sales_state.setFont(font);
		OrderInfo.setFont(font);
		Reserve.setFont(font);
		Member_info.setFont(font);
		Log_out.setFont(font1);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == Sales_state) {
			
		}else if(obj == OrderInfo) {
			OrderInfo orderinfo = new OrderInfo();
		}else if(obj == Reserve) {
			ReserveInfoUI reserveinfo_ui = new ReserveInfoUI();
		}else if(obj == Member_info) {
			MemberInfoUI MIUI = new MemberInfoUI();
		}else if(obj == Log_out) {
			this.dispose();
			LoginUI li = new LoginUI();
		}
	}
	public static void main(String[] args) {
		AdminMainPageUI admin_ui = new AdminMainPageUI();
	}
}