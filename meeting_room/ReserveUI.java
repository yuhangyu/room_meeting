package meeting_room;

import java.awt.Color;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReserveUI extends JFrame implements ActionListener{
	private Reserve reserve;
	String id;
	
	JLabel reserve_lb = new JLabel("���÷� �����ϱ�");
	JLabel room2_lb = new JLabel("2��");
	JLabel room4_lb = new JLabel("4��");
	JLabel room8_lb = new JLabel("8��");
	
	JLabel memberinfo_lb = new JLabel("ȸ������");	
	JLabel name_lb = new JLabel("�̸� : ");
	JLabel name_value_lb = new JLabel("������ʹ�");
	JLabel balance_lb = new JLabel("�ܾ� : ");
	JLabel balance_value_lb = new JLabel("<TEST> ��");
	
	static JButton a = new JButton("");
	
	JButton reserve_btn = new JButton("����");
	JButton cancel_btn = new JButton("���");
	
	// ������ ���� ���¸� �����ϱ� ���� ���ο� ��ư ��ü ���� 
	static JButton buttonStateChange = new JButton(""); 

	Container c = getContentPane();
 
	public ReserveUI() {
		setTitle("���÷� �����ϱ�");
		setSize(800, 750);
		
		c.setLayout(null);
		
		id = LoginUI.getLoginID();
		
		name_value_lb.setText(MainPageUI.name_value_lb.getText());
		balance_value_lb.setText(MainPageUI.balance_value_lb.getText());
		
		reserve_lb.setBounds(5,2,500,40);
		room2_lb.setBounds(65, 75, 100, 20);
		room4_lb.setBounds(265, 75, 100, 20);
		room8_lb.setBounds(465, 75, 100, 20);
		
		memberinfo_lb.setBounds(590,13,100,40);
		name_lb.setBounds(590,50,100,40);	
		name_value_lb.setBounds(640, 50, 100, 40);
		balance_lb.setBounds(590, 87, 100, 40);
		balance_value_lb.setBounds(640, 87, 150, 40);
		
		reserve_btn.setBounds(605, 150, 160, 85);
		cancel_btn.setBounds(605, 250, 160, 85);
		
		reserve = new Reserve(this);
		
		reserve_btn.addActionListener(reserve);
		cancel_btn.addActionListener(reserve);
		a.addActionListener(this);
		
		//Font ����
		Font font = new Font("Dialog", Font.BOLD, 30); // ��Ʈ����
		Font font2 = new Font("Dialog", Font.PLAIN, 23); 
		Font font3 = new Font("Dialog", Font.PLAIN, 18);
		
		reserve_lb.setFont(font);
		room2_lb.setFont(font2);
		room4_lb.setFont(font2);
		room8_lb.setFont(font2);
		memberinfo_lb.setFont(font3);
		name_lb.setFont(font3);
		name_value_lb.setFont(font3);;
		balance_lb.setFont(font3);
		balance_value_lb.setFont(font3);
		reserve_btn.setFont(font2);
		reserve_btn.setEnabled(false); // ���� ��ư�� ���÷��� ������� Ȱ��ȭ �ǵ��� ����
		cancel_btn.setFont(font2);
		
		Vector<RoomBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.roomAll();

		int buttonWidth = 160;
		int buttonHeight = 85;
		int verticalGap = 15;
		int startY = 110;

		for(int i = 0; i < vlist.size(); i++) {
			RoomBean bean = vlist.get(i);
			JButton button = new JButton(bean.getRoom());
			button.addActionListener(reserve);
			
			// ���� ����Ǿ��ٸ� ��ư�� ��Ȱ��ȭ
		    if (bean.getRstate()) {
		        button.setEnabled(false);
		    }
			
			if (i < 4) {
				button.setBounds(5, startY, 160, 85);
				startY += 100;
			} else if (4<= i && i < 10) {
				if (i == 4) startY -= 400;
				button.setBounds(210, startY, 160, 85);
				startY += 100;
			} else {
				if (i == 10) startY -= 600;
				button.setBounds(405, startY, 160, 85);
				startY += 100;
			}
			
			button.setFont(font2);
			c.add(button);
			
			
		}
		
		//��� �߰�
		c.add(reserve_lb);
		c.add(room2_lb);
		c.add(room4_lb);
		c.add(room8_lb);
		
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(name_value_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		
		c.add(reserve_btn);
		c.add(cancel_btn);
		c.add(a);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a) {
			dispose();
			MainPageUI.a.doClick();
		}
	}

	public static void main(String[] args) {
		ReserveUI rsv = new ReserveUI();
	}
}