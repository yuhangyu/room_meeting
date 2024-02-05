package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class MainPage implements ActionListener{
	private MainPageUI mainPageUI;
	
<<<<<<< HEAD
	public MainPage(MainPageUI mainPageUI) {
		this.mainPageUI = mainPageUI;
=======
	//Swing���� \n�� ���� �ٹٲ��� �������� �ʾƼ�, HTML�� Ȱ���Ͽ� ��ư ���� �ٹٲ�
	JButton reserve_btn = new JButton("<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>");
	JButton deposit_btn = new JButton("����");
	JButton purchasefood_btn = new JButton("���� �ֹ��ϱ�");
	JButton rentgame_btn = new JButton("������� �뿩");
	JButton logout_btn = new JButton("�α׾ƿ�");
	JButton myinfo_btn = new JButton("�� ����");

	
	public MainPage() {
		setTitle("���� ������");
		setSize(600, 350);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�ȭ
		
		//��� ��ġ ����
		memberinfo_lb.setBounds(380,  3,  100,  40);
		name_lb.setBounds(380,  40,  100,  40);	
		balance_lb.setBounds(380, 77, 100, 40);
		balance_value_lb.setBounds(430, 77, 150, 40);
		
		//Font ����
		Font font = new Font("Dialog", Font.PLAIN, 18); // ��Ʈ�� ũ�⸸ ���� 
		memberinfo_lb.setFont(font); // ȸ������ ���̺� ���� 
		name_lb.setFont(font); // ȸ������ ���̺� ���� 
		balance_lb.setFont(font); // ȸ������ ���̺� ���� 
		balance_value_lb.setFont(font); // ȸ������ ���̺� ���� 
		reserve_btn.setFont(font);
		deposit_btn.setFont(font);
		purchasefood_btn.setFont(font);
		rentgame_btn.setFont(font);
		
		reserve_btn.setBounds(10, 10, 150, 100);
		deposit_btn.setBounds(180, 10, 150, 100);
		purchasefood_btn.setBounds(10, 120, 150, 100);
		rentgame_btn.setBounds(180, 120, 150, 100);
		myinfo_btn.setBounds(380, 120, 100, 40);
		logout_btn.setBounds(380, 170, 100, 40);
		
				
		//��ư �̺�Ʈ �߰�
		reserve_btn.addActionListener(this);
		deposit_btn.addActionListener(this);
		purchasefood_btn.addActionListener(this);
		rentgame_btn.addActionListener(this);
		logout_btn.addActionListener(this);
		myinfo_btn.addActionListener(this);
				
		//��� �߰�
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		c.add(reserve_btn);
		c.add(deposit_btn);		
		c.add(purchasefood_btn);
		c.add(rentgame_btn);
		c.add(logout_btn);
		c.add(myinfo_btn);
				
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		setVisible(true);
		setResizable(false);
>>>>>>> branch 'master' of https://github.com/yuhangyu/room_meeting.git
		
	}

	
	@Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();

        switch (sourceButton.getText()) {
            case "<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>":
                // ���÷� �����ϱ� UI ����
                ReserveUI rsv = new ReserveUI();
                rsv.setVisible(true);
                break;

            case "����":
                // ���� UI ���� 
                RechargeUI rcg = new RechargeUI();
                rcg.setVisible(true);
                break;

            case "���� �ֹ��ϱ�":
                // ���� �ֹ� ��� ����
                
                break;

            case "������� �뿩":
                // ������� �뿩 ��� ����
                
                break;

            case "�α׾ƿ�":
            	mainPageUI.dispose();
                // �α��� UI ����
            	LoginUI login = new LoginUI();
                login.setVisible(true);
                break;

            case "�� ����":
                // �� ���� UI ����
            	MyInfoUI myinfo = new MyInfoUI();
                myinfo.setVisible(true);
                break;

            default:
                break;
        
        }
	}
}


