package meeting_room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// ���� �����͸� �����ϴ� Ŭ����
class Food {
	String type;
	String name;
	String detail;
	String imageUrl;
	int price;
	
	Food(String type, String name, String imageUrl, int price) {  // �� ������ �޴� ������ �߰�
		this.type = type;
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
	}
	
	String getType() { return type; }
	String getName() { return name; }
	String getImageUrl() { return imageUrl; }
	int getPrice() { return price; }
}

public class FoodOrderUI extends JFrame {
	JPanel panel = new JPanel(new BorderLayout());
	JLabel totalLabel = new JLabel("�� �ֹ� �ݾ�: 0");
	//��� ī�װ� ǥ��
	JPanel northPanel = new JPanel(new GridLayout(1, 5));
	String[] foodTypes = {"ī��", "����", "���", "���", "����", "��"};
	//centerPanel�� CardLayout���� ����
	CardLayout cl = new CardLayout();
	JPanel centerPanel = new JPanel(cl);
	//��ٱ��� ����Ʈ ����
	DefaultListModel<String> cartList = new DefaultListModel<>();
	JList<String> cart = new JList<>(cartList);
	JScrollPane cartScrollPane = new JScrollPane(cart);
	//���� ����Ʈ ����
	List<Food> foods = new ArrayList<>();
	
	JFrame detailDialog;

	public FoodOrderUI() {
		setTitle("���� �޴�");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//â�� X �� ������ ���� ��
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//������(���) �������� �������� �� �Բ� �ݱ�
				if (detailDialog != null && detailDialog.isVisible() == true) detailDialog.dispose();
			}
		});
		
		//���� ������ ����
		Vector<FoodBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.foodAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			FoodBean bean = vlist.get(i);
			foods.add(new Food(bean.getFtype(), bean.getFname(), "�̹���", bean.getFprice()));
		}
		showFood();
		
		//���� ī�װ� ��ư�� ActionListener �߰�
		for (String foodType : foodTypes) {
			JButton foodTypeButton = new JButton(foodType);
			foodTypeButton.setPreferredSize(new Dimension(100, 30));
			foodTypeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//��� ��ư�� ������ �ʱ�ȭ
					for (Component comp : northPanel.getComponents()) {
						if (comp instanceof JButton) {
							comp.setBackground(null);
						}
					}
					//���� ���� ��ư�� ������ ����
					foodTypeButton.setBackground(Color.YELLOW);
					//�ش� ���� ������ �гη� ����
					cl.show(centerPanel, foodType);
				}
			});
			northPanel.add(foodTypeButton);
		}
		
		
		JButton homeButton = new JButton("���ư���");
		//���ư��� ��ư�� ������ �� ���ο� ���� UI�� ������ �ʰ� ���� UI�� �ݵ��� ����
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (detailDialog != null && detailDialog.isVisible() == true) detailDialog.dispose();
				dispose();
			}
		});
		northPanel.add(homeButton);
		panel.add(northPanel, BorderLayout.NORTH);
		
		cl.show(centerPanel, "ī��"); //�ʱ⿡ ������ �г� ����
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		//��ٱ��Ͽ� �߰��� ������ ����ϴ� ��ư ����
		JButton cancelButton = new JButton("���");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cart.getSelectedIndex() != -1) {
					//������ �׸��� ���ݰ� ������ ������
					String selected = cartList.getElementAt(cart.getSelectedIndex());
					String[] split = selected.split(" ");
					int price = 0;
					for (int i = split.length - 1; i >= 0; i--) {
						try {
							price = Integer.parseInt(split[i]);
							break;
						} catch (NumberFormatException ex) {
							//���ڰ� �ƴ� ���ڿ��� ������ �����ϰ� ��� ����
						}
					}
					int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;  // ���� ������ �ִ� ��� ������
				
					//������ �׸� ����
					cartList.remove(cart.getSelectedIndex()); 
					
					//�� �ֹ� �ݾ� ������Ʈ
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					totalLabel.setText("�� �ֹ� �ݾ�: " + (total - price * quantity));
				}
			}
		});
		buttonPanel.add(cancelButton);
		  
		
		//�� �ֹ� �ݾ� ǥ��
		JPanel totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(totalLabel, BorderLayout.NORTH);
		
		//�ֹ� ��û���� �Է� �г�
		JPanel requestPanel = new JPanel();
		JLabel requestLabel = new JLabel("�ֹ� ��û����: ");
		JTextField requestField = new JTextField(20);
		requestField.setPreferredSize(new Dimension(200, 40));
		requestPanel.add(requestLabel);
		requestPanel.add(requestField);
		totalPanel.add(requestPanel, BorderLayout.CENTER);
		
		rightPanel.add(totalPanel, BorderLayout.NORTH);
		rightPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		panel.add(rightPanel, BorderLayout.EAST);
		
		JButton purchaseButton = new JButton("�ֹ��ϱ�");
		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cartList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "��ٱ��ϰ� ����ֽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
				} else {
					MyInfoMgr mgr = new MyInfoMgr();
					MyInfoBean bean = mgr.select(LoginUI.ID);
					TotalBean bean4 = mgr.selecttotal(LoginUI.ID);
					int money = bean.getMoney();
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					if (money < total) {
						JOptionPane.showMessageDialog(null, "�ܾ��� �����մϴ�.", "���", JOptionPane.ERROR_MESSAGE);
						return;
					}
					for (int i = 0; i < cartList.size(); i++) {
						OrderInfoBean beans = new OrderInfoBean();
						FoodBean bean2 = mgr.food(cartList.getElementAt(i).split(" ")[0]);
						Vector<ReserveBean> vlist;
						vlist = mgr.reserveUser(LoginUI.ID);
						ReserveBean bean3 = vlist.get(0);
						beans.setRoom_no(bean3.getResvroom());
						beans.setFoodid(LoginUI.ID);
						beans.setFoodname(bean2.getFood());
						beans.setFoodcount(Integer.parseInt(cartList.getElementAt(i).split("x")[1]));
						beans.setFoodprice(bean2.getFprice() * Integer.parseInt(cartList.getElementAt(i).split("x")[1]));
						beans.setFoodrequest(requestField.getText());
						mgr.foodsales(beans);
					}
					int totalmoney = bean4.getTotal();
					bean4.setTotal(totalmoney + total);
					bean.setMoney(money - total);
					if (mgr.charge(bean) && mgr.totalprice(bean4)) {
						JOptionPane.showMessageDialog(null, "���Ű� �Ϸ�Ǿ����ϴ�.");
						cartList.clear(); // ��ٱ��� ����
						requestField.setText(""); // �ֹ� ��û���� �ʱ�ȭ
						ReserveUI.a.doClick();
						return;
					}
				}
				totalLabel.setText("�� �ֹ� �ݾ�: 0");
			}
		});
		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}
	
	public void showFood() {
		//���� ī�װ����� JPanel ����
		for (String foodType : foodTypes) {
		JPanel foodPanel = new JPanel(new GridLayout(0, 5)); // ���� ũ��� ���Ѵ��, ���� ũ��� 5�� �����Ͽ� ���� ũ���� �׸��带 �����մϴ�.
		int count = 0; // ���� ��ư�� �߰��� ���� �����մϴ�.
		
			for (final Food food : foods) {
				if (food.getType().equals(foodType)) {
					JButton foodButton = new JButton("<html><center><img src='" + food.getImageUrl() + "' width='100' height='100'/><br/>" + food.getName() + "<br/>" + food.getPrice() + "</center></html>");
					foodButton.setPreferredSize(new Dimension(100, 100));  //������ ��ư�� ũ�� ����
					foodButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//�˾� â�� �����ϰ� ������
							detailDialog = new JFrame("���� �� ����");
							detailDialog.setSize(250, 150);
							detailDialog.setLocationRelativeTo(null);
							
							//�˾� â�� ���� ������ ǥ��
							JPanel detailPanel = new JPanel();
							detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
							
							JLabel nameLabel = new JLabel("�̸�: " + food.getName());
							JLabel priceLabel = new JLabel("����: " + food.getPrice());
							
							SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // �ʱ� �� 1, �ּ� �� 1, �ִ� �� 10, �ܰ� 1
							JSpinner quantitySpinner = new JSpinner(model);
							JLabel quantityLabel = new JLabel("����: ");
							quantitySpinner.setPreferredSize(new Dimension(50, 25));
		
							JPanel quantityPanel = new JPanel();
							quantityPanel.add(quantityLabel);
							quantityPanel.add(quantitySpinner);
							quantityLabel.setLabelFor(quantitySpinner);
							
							detailPanel.add(nameLabel);
							detailPanel.add(priceLabel);
							detailPanel.add(quantityPanel);
							
							//��� ��ư �߰�
							JButton addButton = new JButton("���");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									//�ֹ� ������ ���� �߰�
									String foodNamePrice = food.getName() + " " + food.getPrice();
								
									//���ǳʿ��� ���õ� ������ ������
									int quantity = (Integer) quantitySpinner.getValue();
									
									//�ֹ� ������ �̹� ���� ������ ������
									int currentIndex = -1;
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(foodNamePrice)) {
											currentIndex = i;
											break;
										}
									}
			
									//�̹� ����Ʈ�� �ִٸ�
									if (currentIndex != -1) {
										String oldEntry = cartList.getElementAt(currentIndex);
										String[] split = oldEntry.split("x");
										int oldCount = split.length == 2 ? Integer.parseInt(split[1].trim()) : 1;
										cartList.setElementAt(foodNamePrice + " x" + (oldCount + quantity), currentIndex);  // ���� ����
									} else {  //����Ʈ�� ���ٸ�
										cartList.addElement(foodNamePrice + " x" + quantity);  // ������ŭ ���� �߰�
									}
									totalLabel.setText("�� �ֹ� �ݾ�: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + food.getPrice() * quantity));
									
									detailDialog.dispose();
								}
							});
							detailPanel.add(addButton);
							detailDialog.add(detailPanel, BorderLayout.NORTH);
							detailDialog.setVisible(true);
						}
					});
					foodPanel.add(foodButton);
					count++;
				}
			}
			//�� 20���� ĭ �߿��� ���� ��ư���� ä���� ���� ������ ĭ�� �� �гη� ä���
			for (int i = count; i < 20; i++) {
				JPanel emptyPanel = new JPanel();
				foodPanel.add(emptyPanel);
			}
			
			JScrollPane scrollPane = new JScrollPane(foodPanel);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// �׻� ���� ��ũ�ѹ� ǥ��
			centerPanel.add(scrollPane, foodType);
		}
	}
	
	public static void main(String[] args) {
		FoodOrderUI FOUI = new FoodOrderUI();
	}
}