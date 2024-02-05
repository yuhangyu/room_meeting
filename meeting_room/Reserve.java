package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Reserve extends JFrame implements ActionListener{
	
	JLabel reserve_lb = new JLabel("���÷� �����ϱ�");
	JLabel room2_lb = new JLabel("2��");
	JLabel room4_lb = new JLabel("4��");
	JLabel room8_lb = new JLabel("8��");
	
	JLabel memberinfo_lb = new JLabel("ȸ������");	
	JLabel name_lb = new JLabel("�̸�");
	JLabel balance_lb = new JLabel("�ܾ� : ");
	JLabel balance_value_lb = new JLabel("300000��");
	
	JButton room2_1_btn = new JButton("1�� ��");
	JButton room2_2_btn = new JButton("2�� ��");
	JButton room2_3_btn = new JButton("3�� ��");
	JButton room2_4_btn = new JButton("4�� ��");
	
	JButton room4_1_btn = new JButton("1�� ��");
	JButton room4_2_btn = new JButton("��� ��");
	JButton room4_3_btn = new JButton("3�� ��");
	JButton room4_4_btn = new JButton("4�� ��");
	JButton room4_5_btn = new JButton("5�� ��");
	JButton room4_6_btn = new JButton("6�� ��");
	
	JButton room8_1_btn = new JButton("1�� ��");
	JButton room8_2_btn = new JButton("2�� ��");
	
	JButton reserve_btn = new JButton("����");
	JButton cancel_btn = new JButton("���");

	
	
	public Reserve() {
		setTitle("���÷� �����ϱ�");
		setSize(800, 750);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		reserve_lb.setBounds(5,  2,  500,  40);
		room2_lb.setBounds(65, 75, 100, 20);
		room4_lb.setBounds(265, 75, 100, 20);
		room8_lb.setBounds(465, 75, 100, 20);
		
		memberinfo_lb.setBounds(590,  13,  100,  40);
		name_lb.setBounds(590,  50,  100,  40);	
		balance_lb.setBounds(590, 87, 100, 40);
		balance_value_lb.setBounds(640, 87, 150, 40);
		
		room2_1_btn.setBounds(5, 110, 160, 85);
		room2_2_btn.setBounds(5, 210, 160, 85);
		room2_3_btn.setBounds(5, 310, 160, 85);
		room2_4_btn.setBounds(5, 410, 160, 85);
		
		room4_1_btn.setBounds(210, 110, 160, 85);
		room4_2_btn.setBounds(210, 210, 160, 85);
		room4_3_btn.setBounds(210, 310, 160, 85);
		room4_4_btn.setBounds(210, 410, 160, 85);
		room4_5_btn.setBounds(210, 510, 160, 85);
		room4_6_btn.setBounds(210, 610, 160, 85);
		
		room8_1_btn.setBounds(405, 110, 160, 85);
		room8_2_btn.setBounds(405, 210, 160, 85);
		
		reserve_btn.setBounds(605, 150, 160, 85);
		cancel_btn.setBounds(605, 250, 160, 85);
		
		
		
		room2_1_btn.addActionListener(this);
		room2_2_btn.addActionListener(this);
		room2_3_btn.addActionListener(this);
		room2_4_btn.addActionListener(this);
		
		room4_1_btn.addActionListener(this);
		room4_2_btn.addActionListener(this);
		room4_3_btn.addActionListener(this);
		room4_4_btn.addActionListener(this);
		room4_5_btn.addActionListener(this);
		room4_6_btn.addActionListener(this);
		
		room8_1_btn.addActionListener(this);
		room8_2_btn.addActionListener(this);
		
		reserve_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
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
		balance_lb.setFont(font3);
		balance_value_lb.setFont(font3);
		
		room2_1_btn.setFont(font2);
		room2_2_btn.setFont(font2);
		room2_3_btn.setFont(font2);
		room2_4_btn.setFont(font2);
		
		room4_1_btn.setFont(font2);
		room4_2_btn.setFont(font2);
		room4_3_btn.setFont(font2);
		room4_4_btn.setFont(font2);
		room4_5_btn.setFont(font2);
		room4_6_btn.setFont(font2);
		
		room8_1_btn.setFont(font2);
		room8_2_btn.setFont(font2);
		
		reserve_btn.setFont(font2);
		reserve_btn.setEnabled(false); // ���� ��ư�� ���÷��� ������� Ȱ��ȭ �ǵ��� ����
									   // ����Ʈ = false
		
		room4_2_btn.setEnabled(false); // ������� �� ���÷� ����Ʈ ���� 
		room4_2_btn.setBackground(new Color(255, 180, 180)); // ������� �� �� ���� 
		
		cancel_btn.setFont(font2);
		
		c.add(reserve_lb);
		c.add(room2_lb);
		c.add(room4_lb);
		c.add(room8_lb);
		
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		
		c.add(room2_1_btn);
		c.add(room2_2_btn);
		c.add(room2_3_btn);
		c.add(room2_4_btn);
		
		c.add(room4_1_btn);
		c.add(room4_2_btn);
		c.add(room4_3_btn);
		c.add(room4_4_btn);
		c.add(room4_5_btn);
		c.add(room4_6_btn);
		
		c.add(room8_1_btn);
		c.add(room8_2_btn);
		
		c.add(reserve_btn);
		c.add(cancel_btn);
		
		
		
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}


	public static void main(String[] args) {
		Reserve rsv = new Reserve();

	}


}
