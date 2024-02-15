package meeting_room;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RechargeUI extends JFrame implements ActionListener, DocumentListener {
	ImageIcon icon = new ImageIcon("meeting_room/img.png"); // �ΰ� ����� �̹��� ������
	JLabel logo_lb = new JLabel(icon); // �ΰ� ���̺�
	
	JButton charge_1000_btn = new JButton("1000��");
	JButton charge_5000_btn = new JButton("5000��");
	JButton charge_10000_btn = new JButton("10000��");
	JButton charge_30000_btn = new JButton("30000��");
	JButton charge_50000_btn = new JButton("50000��");
	
	JLabel recharge_amount_lb = new JLabel("�����ݾ�");
	static JTextField recharge_value_tf = new JTextField("0", 30);
	JLabel won_lb = new JLabel("��");
	
	JButton recharge_btn = new JButton("�����ϱ�");
	static JButton cancel_btn = new JButton("���");
	
	public RechargeUI(){
		setTitle("����");
		setSize(800, 800);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		// �ΰ� �߰�
        ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(45, 50, 700, 270);
        c.add(logoLabel);
		
        recharge_value_tf.setText("0");
        
		Font font = new Font("Dialog", Font.PLAIN, 18);
		
		charge_1000_btn.setBounds(30, 410, 120, 70);
		charge_5000_btn.setBounds(180, 410, 120, 70);
		charge_10000_btn.setBounds(330, 410, 120, 70);
		charge_30000_btn.setBounds(480, 410, 120, 70);
		charge_50000_btn.setBounds(630, 410, 120, 70);

		won_lb.setBounds(485, 530, 50, 35);
		recharge_amount_lb.setBounds(250, 530, 120, 35);
		recharge_value_tf.setBounds(350, 530, 120, 35);
		recharge_btn.setBounds(250, 600, 120, 70);
		cancel_btn.setBounds(400, 600, 120, 70);
		
		// �ؽ�Ʈ �ʵ带 �������� ����
		recharge_value_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		recharge_amount_lb.setFont(font);
		won_lb.setFont(font);
		
		recharge_btn.setEnabled(false);
		recharge_value_tf.getDocument().addDocumentListener(this);
		
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
		
		charge_1000_btn.addActionListener(this);
		charge_5000_btn.addActionListener(this);
		charge_10000_btn.addActionListener(this);
		charge_30000_btn.addActionListener(this);
		charge_50000_btn.addActionListener(this);	
		cancel_btn.addActionListener(this);
		recharge_btn.addActionListener(this);
		
		
		recharge_value_tf.addKeyListener(new KeyAdapter() {
		    @Override
		    // �ؽ�Ʈ �ʵ忡 ���ڰ� �ƴ� �ٸ� �Է��� �ϴ� ��� ���� 
		    public void keyTyped(KeyEvent e) {
		        char inputChar = e.getKeyChar();
		        if (!Character.isDigit(inputChar)) {
		            e.consume(); // �Էµ� ���� ���ڰ� �ƴϸ� ����
		        }
		        //updateRechargeAmountOnTextChange();
		    }
		    
//		    @Override
//		    // �ؽ�Ʈ �ʵ尡 0���� �����ϴ� ���ڿ��϶� ���ڸ��� 0 ���� ���� 
//		    public void keyReleased(KeyEvent e) {
//		        updateRechargeAmountOnTextChange();
//		    }
//		    
		    
		});

		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���� â�� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
	}
	
	
	
	// �ؽ�Ʈ �ʵ��� ���� �ٲ𶧸��� �����ϱ� ��ư�� Ȱ��ȭ ���� üũ 
	// DocumentListener �޼��� ����
	@Override
	public void changedUpdate(DocumentEvent e) {
		checkAndEnableButton();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
	    checkAndEnableButton();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	    checkAndEnableButton();
	}

	private void checkAndEnableButton() {
		// �ؽ�Ʈ �ʵ��� ���� �����̸鼭 0�� �ƴ���, �Ǵ� ������ �ƴ��� üũ�Ͽ� ��ư Ȱ��ȭ
		String textFieldValue = recharge_value_tf.getText().trim();
		
		recharge_btn.setEnabled(isNumeric(textFieldValue) && !textFieldValue.equals("0") && !textFieldValue.isEmpty());
		}

//	// �ؽ�Ʈ �ʵ忡 ���� �Է��� �� ȣ��Ǵ� �޼ҵ�
//	private void updateRechargeAmountOnTextChange() {
//	    String currentText = recharge_value_tf.getText().trim();
//
//	    // �ؽ�Ʈ �ʵ� ���� 0���� �����ϴ� ��� ���ڸ� 0 ����
//        if (currentText.startsWith("0")) {
//            currentText = currentText.replaceFirst("^0+(?!$)", "");
//        }
//	    
//	    if (currentText.equals("")) {
//	        recharge_btn.setEnabled(false);
//	    } else {
//	        recharge_btn.setEnabled(true);
//	    }
//	}
	
	// ���� ���� üũ �޼��� �߰�
	private boolean isNumeric(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == charge_1000_btn) {
			updateRechargeAmount(1000);
		} else if (obj == charge_5000_btn) {
			updateRechargeAmount(5000);
		} else if (obj == charge_10000_btn) {
			updateRechargeAmount(10000);
		} else if (obj == charge_30000_btn) {
			updateRechargeAmount(30000);
		} else if (obj == charge_50000_btn) {
			updateRechargeAmount(50000);
		} else if (obj == cancel_btn) {
			dispose();
		} else if (obj == recharge_btn) {
			if(Integer.parseInt(recharge_value_tf.getText()) == 0) {
				JOptionPane.showMessageDialog(this, "���� �ݾ��� 0���Դϴ�. ���� �ݾ��� �Է��ϼ���.", "���", JOptionPane.WARNING_MESSAGE);
		        return;
			}
			RechargeDetailUI rcdui = new RechargeDetailUI();
		}
	}
	
	
	// �������� ������ 0���� ������ �ʰ� �ٸ� ���ڿ��� ����ϴ� ���� �ذ� �Ұ�
	
	// recharge_value_tf �ʵ尡 ������ ��쿡�� ��ư�� Ŭ���ϸ� �ش� ��ư�� �ݾ׸�ŭ tf�� �����ϴ� �ڵ�� ��ü 
	private void updateRechargeAmount(int money) {
		if(recharge_value_tf.getText().equals("")) {
			recharge_value_tf.setText(String.valueOf(money));
		}
		
		else {
			int currentAmount = Integer.parseInt(recharge_value_tf.getText());
			currentAmount += money;
			recharge_value_tf.setText(Integer.toString(currentAmount));
		}
	}
	
	public static void main(String[] args) {
		
	}
}