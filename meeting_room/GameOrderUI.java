package meeting_room;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.SSLContext;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

class Game {
	String type;
	String name;
	String imageUrl;
	int price;
	
	Game(String type, String name, String imageUrl, int price) {
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

public class GameOrderUI extends JFrame {
	JPanel panel = new JPanel(new BorderLayout());
	JLabel totalLabel = new JLabel("총 주문 금액: 0");
	//상단 카테고리 표시
	JPanel northPanel = new JPanel(new GridLayout(1, 5));
	String[] gameTypes = {"보드게임", "카드게임", "롤플레잉", "전략게임", "협동게임", "퍼즐게임"};
	//centerPanel을 CardLayout으로 설정
	CardLayout cl = new CardLayout();
	JPanel centerPanel = new JPanel(cl);
	//장바구니 리스트 생성
	DefaultListModel<String> cartList = new DefaultListModel<>();
	JList<String> cart = new JList<>(cartList);
	JScrollPane cartScrollPane = new JScrollPane(cart);
	//게임 리스트 생성
	List<Game> games = new ArrayList<>();
	
	JFrame gameDetail;
	
	public GameOrderUI() {
		setTitle("게임 메뉴");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//창을 X 를 눌러서 닫을 때
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//상세정보(담기) 프레임이 열려있을 때 함께 닫기
				if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
			}
		});
		
		//게임 데이터 생성
		Vector<GameBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.gameAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			GameBean bean = vlist.get(i);
			games.add(new Game(bean.getGtype(), bean.getGname(), "이미지", bean.getGprice()));
		}
		showGame();
		
		//게임 카테고리 버튼에 ActionListener 추가
		for (String gameType : gameTypes) {
			JButton gameTypeButton = new JButton(gameType);
			gameTypeButton.setPreferredSize(new Dimension(100, 30));
			gameTypeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//모든 버튼의 배경색을 초기화
					for (Component comp : northPanel.getComponents()) {
						if (comp instanceof JButton) {
							comp.setBackground(null);
						}
					}
					//현재 누른 버튼의 배경색을 변경
					gameTypeButton.setBackground(Color.YELLOW);
					//해당 음식 유형의 패널로 변경
					cl.show(centerPanel, gameType);
				}
			});
			northPanel.add(gameTypeButton);
		}

		JButton homeButton = new JButton("돌아가기");
		//돌아가기 버튼을 눌렀을 때 새로운 메인 UI가 열리지 않고 현재 UI를 닫도록 수정
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
				dispose();
			}
		});
		northPanel.add(homeButton);
		panel.add(northPanel, BorderLayout.NORTH);
		
		cl.show(centerPanel, "보드게임"); //초기에 보여줄 패널 설정
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

		//장바구니에 추가된 게임을 취소하는 버튼 생성
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
					int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;

					cartList.remove(cart.getSelectedIndex());
					
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					totalLabel.setText("총 주문 금액: " + (total - price * quantity));
				}
			}
		});
		buttonPanel.add(cancelButton);
		
		//총 주문 금액 표시
		JPanel totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(totalLabel, BorderLayout.CENTER);
		
		//주문 요청사항 입력 패널
		JPanel requestPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(225, 800)); // 원하는 크기로 설정

		totalPanel.add(requestPanel, BorderLayout.SOUTH);
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
					JOptionPane.showMessageDialog(null, "구매가 완료되었습니다.");
					cartList.clear();
				}
				totalLabel.setText("총 주문 금액: 0");
			}
		});
		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}

	public void showGame() {
		//게임 카테고리별로 JPanel 생성
	    for (String gameType : gameTypes) {
	        JPanel gamePanel = new JPanel(new GridLayout(0, 5)); // 세로 크기는 무한대로, 가로 크기는 5로 설정하여 고정 크기의 그리드를 생성합니다.
	        int count = 0; // 게임 버튼을 추가한 수를 저장합니다.

	        for (Game game : games) {
	            if (game.getType().equals(gameType)) {
	                JButton gameButton = new JButton("<html><center><img src='" + game.getImageUrl() + "' width='100' height='100'/><br/>" + game.getName() + "<br/>" + game.getPrice() + "</center></html>");

	                gameButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//팝업 창을 생성하고 보여줌
							gameDetail = new JFrame("게임 상세 정보");
							gameDetail.setSize(250, 120);
							gameDetail.setLocationRelativeTo(null);
							
							//팝업 창에 음식 정보를 표시
							JPanel detailPanel = new JPanel();
							
							JLabel nameLabel = new JLabel("게임 이름: " + game.getName());	
							JLabel priceLabel = new JLabel("가격: " + game.getPrice());
							
							JPanel gamePanel = new JPanel();
							gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
							gamePanel.add(nameLabel);
							gamePanel.add(priceLabel);
							
							//담기 버튼 추가
							JButton addButton = new JButton("담기");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									//주문 내역에 게임 추가
									String gameNamePrice = game.getName() + " | " + game.getPrice();
									
									//주문 내역에 이미 같은 게임이 있으면ㄴ
									int currentIndex = -1;
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(game.getName())) {// 게임 이름으로 찾기
											currentIndex = i;
											break;
										}
									}

									//이미 리스트에 있다면
									if (currentIndex != -1) {
										JOptionPane.showMessageDialog(null, "게임은 최대 1개만 선택 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
									} else { //리스트에 없다면
										cartList.addElement(gameNamePrice + " x" + 1);// 새로 추가
										totalLabel.setText("총 주문 금액: " + ((Integer.parseInt(totalLabel.getText().split(": ")[1]) + game.getPrice())));
									}
									gameDetail.dispose();
								}
							});
							gamePanel.add(addButton);
							gamePanel.add(detailPanel);
							gameDetail.add(gamePanel, BorderLayout.NORTH);
							gameDetail.setVisible(true);
						}
	                });
	                gamePanel.add(gameButton);
	                count++;
	            }
	        }
	        //총 20개의 칸 중에서 게임 버튼으로 채우지 못한 나머지 칸을 빈 패널로 채우기
	        for (int i = count; i < 20; i++) {
	            JPanel emptyPanel = new JPanel();
	            gamePanel.add(emptyPanel);
	        }

	        JScrollPane scrollPane = new JScrollPane(gamePanel);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //항상 세로 스크롤바 표시
	        centerPanel.add(scrollPane, gameType);
	    }
	}
	
	public static void main(String[] args) {
		GameOrderUI GOUI = new GameOrderUI();
	}
}