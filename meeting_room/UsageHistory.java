package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;


import javax.swing.JFrame;
import javax.swing.JLabel;

public class UsageHistory extends JFrame implements ActionListener{
	JLabel usage_history_lb = new JLabel("�̿� ����");
	JLabel start_date_lb = new JLabel("�˻� ������");
	JLabel end_date_lb = new JLabel("�˻� ������");

	UsageHistory(){
		setTitle("�� ����");
		setSize(750, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		usage_history_lb.setBounds(20,  10,  200,  40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(400, 50, 150, 50);
		
		Font font = new Font("Dialog", Font.BOLD, 27);
		Font font1 = new Font("Dialog", Font.BOLD, 18);

		usage_history_lb.setFont(font);
		start_date_lb.setFont(font1);
		end_date_lb.setFont(font1);
		
		c.add(usage_history_lb);
		c.add(start_date_lb);
		c.add(end_date_lb);
		
		
		
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
		UsageHistory uh = new UsageHistory();

	}

	

}
