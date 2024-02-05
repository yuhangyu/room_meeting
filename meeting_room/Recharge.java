package meeting_room;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Recharge extends JFrame implements ActionListener{
	ImageIcon icon = new ImageIcon("src/MeetingRoomProject/img.png"); // �ΰ� ����� �̹��� ������
	JLabel logo_lb = new JLabel(icon); // �ΰ� ���̺�
	
	JButton charge_1000_btn = new JButton("1000��");
	JButton charge_5000_btn = new JButton("5000��");
	JButton charge_10000_btn = new JButton("10000��");
	JButton charge_30000_btn = new JButton("30000��");
	JButton charge_50000_btn = new JButton("50000��");
	
	JLabel recharge_amount_lb = new JLabel("�����ݾ�");
	JTextField recharge_value_tf = new JTextField("0", 30);
	JLabel won_lb = new JLabel("��");
	
	JButton recharge_btn = new JButton("�����ϱ�");
	JButton cancel_btn = new JButton("���");
	
	public Recharge(){
		setTitle("����");
		setSize(800, 600);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		// �̹��� ũ�⿡ �°� JLabel ũ�� ���� 
        Dimension imageSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        logo_lb.setPreferredSize(imageSize);
       
        // �̹����� �������� �߾�, ����ڰ� ���� ���� ��ǥ�� ��ġ��Ŵ
        int x = (getWidth() - imageSize.width) / 2;
        logo_lb.setBounds(x, 35, imageSize.width, imageSize.height);
        
        Font font = new Font("Dialog", Font.PLAIN, 18);
        
        charge_1000_btn.setBounds(30, 210, 120, 70);
        charge_5000_btn.setBounds(180, 210, 120, 70);
        charge_10000_btn.setBounds(330, 210, 120, 70);
        charge_30000_btn.setBounds(480, 210, 120, 70);
        charge_50000_btn.setBounds(630, 210, 120, 70);
    
        
        recharge_amount_lb.setBounds(250, 330, 120, 35);
        recharge_value_tf.setBounds(350, 330, 120, 35);
        // �ؽ�Ʈ �ʵ带 �������� ����
        recharge_value_tf.setHorizontalAlignment(SwingConstants.RIGHT);
        won_lb.setBounds(485, 330, 50, 35);
        
        recharge_amount_lb.setFont(font);
        won_lb.setFont(font);
        
        recharge_btn.setBounds(250 , 400, 120, 70);
        cancel_btn.setBounds(400, 400, 120, 70);
        
        charge_1000_btn.addActionListener(this);
        charge_5000_btn.addActionListener(this);
        charge_10000_btn.addActionListener(this);
        charge_30000_btn.addActionListener(this);
        charge_50000_btn.addActionListener(this);
        recharge_btn.addActionListener(this);
        cancel_btn.addActionListener(this);
		
        c.add(logo_lb);
        
        c.add(charge_1000_btn);
        c.add(charge_5000_btn);
        c.add(charge_10000_btn);
        c.add(charge_30000_btn);
        c.add(charge_50000_btn);
		
        c.add(recharge_amount_lb);
        c.add(recharge_value_tf);
        c.add(won_lb);
        c.add(recharge_btn);
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
		Recharge rcg = new Recharge();

	}

	

}
