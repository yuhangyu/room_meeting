package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Recharge implements ActionListener {
	private JTextField recharge_value_tf;
	MyInfoMgr mgr;
	int money;
	String id;

	public Recharge(JTextField recharge_value_tf) {
		this.recharge_value_tf = recharge_value_tf;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		String buttonText = sourceButton.getText();
		
		switch (buttonText) {
			case "1000원":
				updateRechargeAmount(1000);
				break;
			case "5000원":
				updateRechargeAmount(5000);
				break;
			case "10000원":
				updateRechargeAmount(10000);
				break;
			case "30000원":
				updateRechargeAmount(30000);
				break;
			case "50000원":
				updateRechargeAmount(50000);
				break;
			case "취소":
				cancelCharge();
				break;
			case "충전하기":
				goCharge();
				break;
			default:
				break;
		}
	}

	private void updateRechargeAmount(int money) {
		int currentAmount = Integer.parseInt(recharge_value_tf.getText());
		currentAmount += money;
		recharge_value_tf.setText(Integer.toString(currentAmount));
	}
	
	private void cancelCharge() {
		// 취소 버튼을 누르면 현재 폼을 닫기
		JFrame frame = (JFrame) recharge_value_tf.getTopLevelAncestor();
		frame.dispose();
	}
	
	private void goCharge() {
		id = LoginUI.getLoginID();
		mgr = new MyInfoMgr();
		MyInfoBean bean = mgr.select(id);
		money = bean.getMoney();
		bean.setID(id);
		bean.setMoney(Integer.parseInt(recharge_value_tf.getText()) + money);
		if(mgr.charge(bean)) {
			JOptionPane.showMessageDialog(null, "충전되었습니다.");
			mgr.select(bean.getID());
			MainPageUI.balance_value_lb.setText(String.valueOf(bean.getMoney()) + "원");
			cancelCharge();
		}
	}
}