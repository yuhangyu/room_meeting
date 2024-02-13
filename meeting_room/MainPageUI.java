package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainPageUI extends JFrame implements ActionListener {
	
	String id;
	String name;
	int money;
	MyInfoMgr mgr;
	public static int MONEY;

	String formattedDate; // ���� ���� YY-MM-DD
	String formattedDate2; // ���� ���� HH:MM:SS
	String EndformattedDate2; // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
	
	//��� ����
	JLabel memberinfo_lb = new JLabel("ȸ������");	
	JLabel name_lb = new JLabel("�̸� : ");
	JLabel balance_lb = new JLabel("�ܾ� : ");
	static JLabel name_value_lb = new JLabel("");
	static JLabel balance_value_lb = new JLabel("0��");
	
	//Swing���� \n�� ���� �ٹٲ��� �������� �ʾƼ�, HTML�� Ȱ���Ͽ� ��ư ���� �ٹٲ�
	JButton reserve_btn = new JButton("<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>");
	JButton deposit_btn = new JButton("����");
	JButton myinfo_btn = new JButton("�� ����");
	JButton logout_btn = new JButton("�α׾ƿ�");
	static JButton purchasefood_btn = new JButton("���� �ֹ��ϱ�");
	static JButton rentgame_btn = new JButton("������� �뿩");
	static JButton as = new JButton("");

	public MainPageUI() {
		setTitle("���� ������");
		setSize(800, 650);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�ȭ
		
		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		MyInfoBean bean = mgr.select(id);
		
		name = bean.getName();
		money = bean.getMoney();
		name_value_lb.setText(name);
		balance_value_lb.setText(money + "��");
		MONEY = money;
		
		// �ΰ� �߰�
		ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(45, 20, 700, 270);
		c.add(logoLabel);
		
		//��� ��ġ ����
		memberinfo_lb.setBounds(480,  323,  100,  40);
		name_lb.setBounds(480,  360,  100,  40);	
		name_value_lb.setBounds(530, 360, 150, 40);
		balance_lb.setBounds(480, 397, 100, 40);
		balance_value_lb.setBounds(530, 397, 150, 40);
		
		//Font ����
		Font font = new Font("Dialog", Font.PLAIN, 18); // ��Ʈ�� ũ�⸸ ���� 
		memberinfo_lb.setFont(font);
		name_lb.setFont(font);
		name_value_lb.setFont(font);
		balance_lb.setFont(font);
		balance_value_lb.setFont(font);
		reserve_btn.setFont(font);
		deposit_btn.setFont(font);
		purchasefood_btn.setFont(font);
		rentgame_btn.setFont(font);
		logout_btn.setFont(font);
		myinfo_btn.setFont(font);
		
		reserve_btn.setBounds(55, 330, 150, 100);
		deposit_btn.setBounds(230, 330, 150, 100);
		purchasefood_btn.setBounds(55, 440, 150, 100);
		rentgame_btn.setBounds(230, 440, 150, 100);
		myinfo_btn.setBounds(430, 440, 150, 100);
		logout_btn.setBounds(590, 440, 150, 100);
		
		//��� �߰�
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(name_value_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		c.add(reserve_btn);
		c.add(deposit_btn);		
		c.add(purchasefood_btn);
		c.add(rentgame_btn);
		c.add(logout_btn);
		c.add(myinfo_btn);
		
		//��ư �̺�Ʈ �߰�
		reserve_btn.addActionListener(this);
		deposit_btn.addActionListener(this);
		logout_btn.addActionListener(this);
		myinfo_btn.addActionListener(this);
		purchasefood_btn.addActionListener(this);
		rentgame_btn.addActionListener(this);
		as.addActionListener(this);
		
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		if (vlist.size() == 0) {
			purchasefood_btn.setEnabled(false);
			rentgame_btn.setEnabled(false);
		}
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//�ݾ� ������Ʈ�� ���� ��ư �̺�Ʈ
		if (obj == as) {
			id = LoginUI.ID;
			mgr = new MyInfoMgr();
			MyInfoBean bean = mgr.select(id);
			
			money = bean.getMoney();
			balance_value_lb.setText(money + "��");
			MONEY = money;
		} else if (obj == reserve_btn) {
			ReserveUI rui = new ReserveUI();
		} else if (obj == deposit_btn ) {
			RechargeUI rcg = new RechargeUI();
		} else if (obj == purchasefood_btn) {
			if (check() == true) {
				FoodOrderUI FUI = new FoodOrderUI();
			} else {
				JOptionPane.showMessageDialog(null, "���� ���� �ð��� ���� �ʾҽ��ϴ�.\n" + formattedDate2 + " ���� ��� �����մϴ�.");
			}
		} else if (obj == rentgame_btn) {
			if (check() == true) {
				GameOrderUI GUI = new GameOrderUI();
			} else {
				JOptionPane.showMessageDialog(null, "���� ���� �ð��� ���� �ʾҽ��ϴ�.\n" + formattedDate2 + " ���� ��� �����մϴ�.");
			}
		} else if (obj == myinfo_btn) {
			MyInfoUI myinfo = new MyInfoUI();
		} else if (obj == logout_btn) {
			dispose();
			LoginUI login = new LoginUI();
		}
	}
	
	public boolean check() {
		boolean flag = false;
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");

		MyInfoMgr mgr = new MyInfoMgr();
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		ReserveBean bean = vlist.get(0);
		String OriginalResvtime = bean.getResvtime();
		int resvtime = bean.getResvusetime(); // ��� �ð��� �޾ƿ�
			
		try {
			Date date = originalFormat.parse(OriginalResvtime);
				
			//getResvusetime()�� �ð����� ��ȯ�ϰ� ���ϱ�
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date); // Ķ������ ����Ʈ�� �޾ƿ� �ð����� ����
			calendar.add(Calendar.HOUR_OF_DAY, resvtime); // Ķ������ ���ð� ���ϱ�
			
			formattedDate = newFormat.format(date); // ���� ���� YY-MM-DD
			formattedDate2 = newFormat2.format(date); // ���� ���� HH:MM:SS
			EndformattedDate2 = newFormat2.format(calendar.getTime()); // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
			
			// ���� �ð� ��������
			LocalTime currentTime = LocalTime.now();
			
			// Ư�� �ð� ���� ����
			LocalTime startTime = LocalTime.of(Integer.parseInt(formattedDate2.split(":")[0]), Integer.parseInt(formattedDate2.split(":")[1]));   // ���� �ð�: 09:00
			LocalTime endTime = LocalTime.of(Integer.parseInt(EndformattedDate2.split(":")[0]), Integer.parseInt(EndformattedDate2.split(":")[1]));    // ���� �ð�: 17:00
			
			// ���� �ð��� Ư�� �ð� ���� ���� �ִ��� Ȯ��
			if (isWithinTimeRange(currentTime, startTime, endTime)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return flag;
	}
	
	// ���� �ð��� Ư�� �ð� ���� ���� �ִ��� Ȯ���ϴ� �޼���
	private static boolean isWithinTimeRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
		return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}
	
	public static void main(String[] args) {
		
	}
}