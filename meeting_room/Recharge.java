package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;



public class Recharge implements ActionListener{
	private JTextField recharge_value_tf;
	
	public Recharge(JTextField recharge_value_tf) {
        this.recharge_value_tf = recharge_value_tf;
    }
	
	@Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        String buttonText = sourceButton.getText();

        switch (buttonText) {
            case "1000��":
                updateRechargeAmount(1000);
                break;
            case "5000��":
                updateRechargeAmount(5000);
                break;
            case "10000��":
                updateRechargeAmount(10000);
                break;
            case "30000��":
                updateRechargeAmount(30000);
                break;
            case "50000��":
                updateRechargeAmount(50000);
                break;
            case "���":
            	calcelCharge();
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
    
    private void calcelCharge() {
    	// ��� ��ư�� ������ ���� ���� �ݱ�
        JFrame frame = (JFrame) recharge_value_tf.getTopLevelAncestor();
        frame.dispose();
    	
    }

}

