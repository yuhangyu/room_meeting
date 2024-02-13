package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class RechargeDetail implements ActionListener{
	private RechargeDetailUI rcdui;
	MyInfoMgr mgr;
	int money;
	String id;

	public RechargeDetail(RechargeDetailUI rcdui) {
		this.rcdui = rcdui;
	}
	
	
	@Override
    public void actionPerformed(ActionEvent e) {
        // Ŭ���� ��ư�� ���� ���� ����
        if (e.getSource() == rcdui.recharge) {
            // ���� ��ư�� Ŭ���� ���
        	id = LoginUI.ID;
    		mgr = new MyInfoMgr();
    		MyInfoBean bean = mgr.select(id);
    		money = bean.getMoney();
    		bean.setID(id);
    		bean.setMoney(Integer.parseInt(rcdui.amount_value.getText()) + money);
    		if(mgr.charge(bean)) {
    			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
    			mgr.select(bean.getID());
    			MainPageUI.balance_value_lb.setText(String.valueOf(bean.getMoney()));
    			rcdui.dispose();
    			RechargeUI.recharge_value_tf.setText("0");
    		}
            
        } else if (e.getSource() == rcdui.cancel) {
            // ��� ��ư�� Ŭ���� ���
        	rcdui.dispose();
        }
    }

	public static void main(String[] args) {

	}

}
