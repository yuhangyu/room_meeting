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

// 음식 데이터를 저장하는 클래스
class Food {
	String type;
	String name;
	String detail;
	String imageUrl;
	int price;
	
	Food(String type, String name, String imageUrl, int price) {  // 상세 정보를 받는 생성자 추가
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
	JLabel totalLabel = new JLabel("총 주문 금액: 0");
	//상단 카테고리 표시
	JPanel northPanel = new JPanel(new GridLayout(1, 5));
	String[] foodTypes = {"카페", "음료", "면류", "밥류", "과자", "술"};
	//centerPanel을 CardLayout으로 설정
	CardLayout cl = new CardLayout();
	JPanel centerPanel = new JPanel(cl);
	//장바구니 리스트 생성
	DefaultListModel<String> cartList = new DefaultListModel<>();
	JList<String> cart = new JList<>(cartList);
	JScrollPane cartScrollPane = new JScrollPane(cart);
	//음식 리스트 생성
	List<Food> foods = new ArrayList<>();
	
	JFrame detailDialog;

	public FoodOrderUI() {
		setTitle("음식 메뉴");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//창을 X 를 눌러서 닫을 때
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//상세정보(담기) 프레임이 열려있을 때 함께 닫기
				if (detailDialog != null && detailDialog.isVisible() == true) detailDialog.dispose();
			}
		});
		
		//음식 데이터 생성
		Vector<FoodBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.foodAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			FoodBean bean = vlist.get(i);
			foods.add(new Food(bean.getFtype(), bean.getFname(), "이미지", bean.getFprice()));
		}
		showFood();
		
		//음식 카테고리 버튼에 ActionListener 추가
		for (String foodType : foodTypes) {
			JButton foodTypeButton = new JButton(foodType);
			foodTypeButton.setPreferredSize(new Dimension(100, 30));
			foodTypeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//모든 버튼의 배경색을 초기화
					for (Component comp : northPanel.getComponents()) {
						if (comp instanceof JButton) {
							comp.setBackground(null);
						}
					}
					//현재 누른 버튼의 배경색을 변경
					foodTypeButton.setBackground(Color.YELLOW);
					//해당 음식 유형의 패널로 변경
					cl.show(centerPanel, foodType);
				}
			});
			northPanel.add(foodTypeButton);
		}
		
		
		JButton homeButton = new JButton("돌아가기");
		//돌아가기 버튼을 눌렀을 때 새로운 메인 UI가 열리지 않고 현재 UI를 닫도록 수정
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (detailDialog != null && detailDialog.isVisible() == true) detailDialog.dispose();
				dispose();
			}
		});
		northPanel.add(homeButton);
		panel.add(northPanel, BorderLayout.NORTH);
		
		cl.show(centerPanel, "카페"); //초기에 보여줄 패널 설정
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		//장바구니에 추가된 음식을 취소하는 버튼 생성
		JButton cancelButton = new JButton("취소");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cart.getSelectedIndex() != -1) {
					//선택한 항목의 가격과 수량을 가져옴
					String selected = cartList.getElementAt(cart.getSelectedIndex());
					String[] split = selected.split(" ");
					int price = 0;
					for (int i = split.length - 1; i >= 0; i--) {
						try {
							price = Integer.parseInt(split[i]);
							break;
						} catch (NumberFormatException ex) {
							//숫자가 아닌 문자열을 만나면 무시하고 계속 진행
						}
					}
					int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;  // 수량 정보가 있는 경우 가져옴
				
					//선택한 항목 삭제
					cartList.remove(cart.getSelectedIndex()); 
					
					//총 주문 금액 업데이트
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					totalLabel.setText("총 주문 금액: " + (total - price * quantity));
				}
			}
		});
		buttonPanel.add(cancelButton);
		  
		
		//총 주문 금액 표시
		JPanel totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(totalLabel, BorderLayout.NORTH);
		
		//주문 요청사항 입력 패널
		JPanel requestPanel = new JPanel();
		JLabel requestLabel = new JLabel("주문 요청사항: ");
		JTextField requestField = new JTextField(20);
		requestField.setPreferredSize(new Dimension(200, 40));
		requestPanel.add(requestLabel);
		requestPanel.add(requestField);
		totalPanel.add(requestPanel, BorderLayout.CENTER);
		
		rightPanel.add(totalPanel, BorderLayout.NORTH);
		rightPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		panel.add(rightPanel, BorderLayout.EAST);
		
		JButton purchaseButton = new JButton("주문하기");
		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cartList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					MyInfoMgr mgr = new MyInfoMgr();
					MyInfoBean bean = mgr.select(LoginUI.ID);
					TotalBean bean4 = mgr.selecttotal(LoginUI.ID);
					int money = bean.getMoney();
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					if (money < total) {
						JOptionPane.showMessageDialog(null, "잔액이 부족합니다.", "경고", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "구매가 완료되었습니다.");
						cartList.clear(); // 장바구니 비우기
						requestField.setText(""); // 주문 요청사항 초기화
						ReserveUI.a.doClick();
						return;
					}
				}
				totalLabel.setText("총 주문 금액: 0");
			}
		});
		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}
	
	public void showFood() {
		//음식 카테고리별로 JPanel 생성
		for (String foodType : foodTypes) {
		JPanel foodPanel = new JPanel(new GridLayout(0, 5)); // 세로 크기는 무한대로, 가로 크기는 5로 설정하여 고정 크기의 그리드를 생성합니다.
		int count = 0; // 음식 버튼을 추가한 수를 저장합니다.
		
			for (final Food food : foods) {
				if (food.getType().equals(foodType)) {
					JButton foodButton = new JButton("<html><center><img src='" + food.getImageUrl() + "' width='100' height='100'/><br/>" + food.getName() + "<br/>" + food.getPrice() + "</center></html>");
					foodButton.setPreferredSize(new Dimension(100, 100));  //생성된 버튼의 크기 고정
					foodButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//팝업 창을 생성하고 보여줌
							detailDialog = new JFrame("음식 상세 정보");
							detailDialog.setSize(250, 150);
							detailDialog.setLocationRelativeTo(null);
							
							//팝업 창에 음식 정보를 표시
							JPanel detailPanel = new JPanel();
							detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
							
							JLabel nameLabel = new JLabel("이름: " + food.getName());
							JLabel priceLabel = new JLabel("가격: " + food.getPrice());
							
							SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // 초기 값 1, 최소 값 1, 최대 값 10, 단계 1
							JSpinner quantitySpinner = new JSpinner(model);
							JLabel quantityLabel = new JLabel("수량: ");
							quantitySpinner.setPreferredSize(new Dimension(50, 25));
		
							JPanel quantityPanel = new JPanel();
							quantityPanel.add(quantityLabel);
							quantityPanel.add(quantitySpinner);
							quantityLabel.setLabelFor(quantitySpinner);
							
							detailPanel.add(nameLabel);
							detailPanel.add(priceLabel);
							detailPanel.add(quantityPanel);
							
							//담기 버튼 추가
							JButton addButton = new JButton("담기");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									//주문 내역에 음식 추가
									String foodNamePrice = food.getName() + " " + food.getPrice();
								
									//스피너에서 선택된 수량을 가져옴
									int quantity = (Integer) quantitySpinner.getValue();
									
									//주문 내역에 이미 같은 음식이 있으면
									int currentIndex = -1;
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(foodNamePrice)) {
											currentIndex = i;
											break;
										}
									}
			
									//이미 리스트에 있다면
									if (currentIndex != -1) {
										String oldEntry = cartList.getElementAt(currentIndex);
										String[] split = oldEntry.split("x");
										int oldCount = split.length == 2 ? Integer.parseInt(split[1].trim()) : 1;
										cartList.setElementAt(foodNamePrice + " x" + (oldCount + quantity), currentIndex);  // 수량 증가
									} else {  //리스트에 없다면
										cartList.addElement(foodNamePrice + " x" + quantity);  // 수량만큼 새로 추가
									}
									totalLabel.setText("총 주문 금액: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + food.getPrice() * quantity));
									
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
			//총 20개의 칸 중에서 게임 버튼으로 채우지 못한 나머지 칸을 빈 패널로 채우기
			for (int i = count; i < 20; i++) {
				JPanel emptyPanel = new JPanel();
				foodPanel.add(emptyPanel);
			}
			
			JScrollPane scrollPane = new JScrollPane(foodPanel);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 항상 세로 스크롤바 표시
			centerPanel.add(scrollPane, foodType);
		}
	}
	
	public static void main(String[] args) {
		FoodOrderUI FOUI = new FoodOrderUI();
	}
}