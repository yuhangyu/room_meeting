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
        // 클릭된 버튼에 따라 동작 수행
        if (e.getSource() == rcdui.recharge) {
            // 충전 버튼이 클릭된 경우
        	id = LoginUI.ID;
    		mgr = new MyInfoMgr();
    		MyInfoBean bean = mgr.select(id);
    		money = bean.getMoney();
    		bean.setID(id);
    		bean.setMoney(Integer.parseInt(rcdui.amount_value.getText()) + money);
    		if(mgr.charge(bean)) {
    			JOptionPane.showMessageDialog(null, "충전되었습니다.");
    			mgr.select(bean.getID());
    			MainPageUI.balance_value_lb.setText(String.valueOf(bean.getMoney()));
    			rcdui.dispose();
    			RechargeUI.recharge_value_tf.setText("0");
    		}
            
        } else if (e.getSource() == rcdui.cancel) {
            // 취소 버튼이 클릭된 경우
        	rcdui.dispose();
        }
    }

	public static void main(String[] args) {

	}

}
