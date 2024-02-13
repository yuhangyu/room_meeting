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
	JLabel totalLabel = new JLabel("�� �ֹ� �ݾ�: 0");
	//��� ī�װ� ǥ��
	JPanel northPanel = new JPanel(new GridLayout(1, 5));
	String[] gameTypes = {"�������", "ī�����", "���÷���", "��������", "��������", "�������"};
	//centerPanel�� CardLayout���� ����
	CardLayout cl = new CardLayout();
	JPanel centerPanel = new JPanel(cl);
	//��ٱ��� ����Ʈ ����
	DefaultListModel<String> cartList = new DefaultListModel<>();
	JList<String> cart = new JList<>(cartList);
	JScrollPane cartScrollPane = new JScrollPane(cart);
	//���� ����Ʈ ����
	List<Game> games = new ArrayList<>();
	
	JFrame gameDetail;
	
	public GameOrderUI() {
		setTitle("���� �޴�");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//â�� X �� ������ ���� ��
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//������(���) �������� �������� �� �Բ� �ݱ�
				if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
			}
		});
		
		//���� ������ ����
		Vector<GameBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.gameAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			GameBean bean = vlist.get(i);
			games.add(new Game(bean.getGtype(), bean.getGname(), "�̹���", bean.getGprice()));
		}
		showGame();
		
		//���� ī�װ� ��ư�� ActionListener �߰�
		for (String gameType : gameTypes) {
			JButton gameTypeButton = new JButton(gameType);
			gameTypeButton.setPreferredSize(new Dimension(100, 30));
			gameTypeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//��� ��ư�� ������ �ʱ�ȭ
					for (Component comp : northPanel.getComponents()) {
						if (comp instanceof JButton) {
							comp.setBackground(null);
						}
					}
					//���� ���� ��ư�� ������ ����
					gameTypeButton.setBackground(Color.YELLOW);
					//�ش� ���� ������ �гη� ����
					cl.show(centerPanel, gameType);
				}
			});
			northPanel.add(gameTypeButton);
		}

		JButton homeButton = new JButton("���ư���");
		//���ư��� ��ư�� ������ �� ���ο� ���� UI�� ������ �ʰ� ���� UI�� �ݵ��� ����
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
				dispose();
			}
		});
		northPanel.add(homeButton);
		panel.add(northPanel, BorderLayout.NORTH);
		
		cl.show(centerPanel, "�������"); //�ʱ⿡ ������ �г� ����
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

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
					int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;

					cartList.remove(cart.getSelectedIndex());
					
					int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
					totalLabel.setText("�� �ֹ� �ݾ�: " + (total - price * quantity));
				}
			}
		});
		buttonPanel.add(cancelButton);
		
		//�� �ֹ� �ݾ� ǥ��
		JPanel totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(totalLabel, BorderLayout.CENTER);
		
		//�ֹ� ��û���� �Է� �г�
		JPanel requestPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(225, 800)); // ���ϴ� ũ��� ����

		totalPanel.add(requestPanel, BorderLayout.SOUTH);
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
					JOptionPane.showMessageDialog(null, "���Ű� �Ϸ�Ǿ����ϴ�.");
					cartList.clear();
				}
				totalLabel.setText("�� �ֹ� �ݾ�: 0");
			}
		});
		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}

	public void showGame() {
		//���� ī�װ����� JPanel ����
	    for (String gameType : gameTypes) {
	        JPanel gamePanel = new JPanel(new GridLayout(0, 5)); // ���� ũ��� ���Ѵ��, ���� ũ��� 5�� �����Ͽ� ���� ũ���� �׸��带 �����մϴ�.
	        int count = 0; // ���� ��ư�� �߰��� ���� �����մϴ�.

	        for (Game game : games) {
	            if (game.getType().equals(gameType)) {
	                JButton gameButton = new JButton("<html><center><img src='" + game.getImageUrl() + "' width='100' height='100'/><br/>" + game.getName() + "<br/>" + game.getPrice() + "</center></html>");

	                gameButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//�˾� â�� �����ϰ� ������
							gameDetail = new JFrame("���� �� ����");
							gameDetail.setSize(250, 120);
							gameDetail.setLocationRelativeTo(null);
							
							//�˾� â�� ���� ������ ǥ��
							JPanel detailPanel = new JPanel();
							
							JLabel nameLabel = new JLabel("���� �̸�: " + game.getName());	
							JLabel priceLabel = new JLabel("����: " + game.getPrice());
							
							JPanel gamePanel = new JPanel();
							gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
							gamePanel.add(nameLabel);
							gamePanel.add(priceLabel);
							
							//��� ��ư �߰�
							JButton addButton = new JButton("���");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									//�ֹ� ������ ���� �߰�
									String gameNamePrice = game.getName() + " | " + game.getPrice();
									
									//�ֹ� ������ �̹� ���� ������ �����餤
									int currentIndex = -1;
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(game.getName())) {// ���� �̸����� ã��
											currentIndex = i;
											break;
										}
									}

									//�̹� ����Ʈ�� �ִٸ�
									if (currentIndex != -1) {
										JOptionPane.showMessageDialog(null, "������ �ִ� 1���� ���� �����մϴ�.", "���", JOptionPane.WARNING_MESSAGE);
									} else { //����Ʈ�� ���ٸ�
										cartList.addElement(gameNamePrice + " x" + 1);// ���� �߰�
										totalLabel.setText("�� �ֹ� �ݾ�: " + ((Integer.parseInt(totalLabel.getText().split(": ")[1]) + game.getPrice())));
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
	        //�� 20���� ĭ �߿��� ���� ��ư���� ä���� ���� ������ ĭ�� �� �гη� ä���
	        for (int i = count; i < 20; i++) {
	            JPanel emptyPanel = new JPanel();
	            gamePanel.add(emptyPanel);
	        }

	        JScrollPane scrollPane = new JScrollPane(gamePanel);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //�׻� ���� ��ũ�ѹ� ǥ��
	        centerPanel.add(scrollPane, gameType);
	    }
	}
	
	public static void main(String[] args) {
		GameOrderUI GOUI = new GameOrderUI();
	}
}