package meeting_room;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

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
	
	JLabel totalLabel = new JLabel("�� �ֹ� �ݾ�: 0");
	JPanel northPanel = new JPanel(new GridLayout(1, 5));
	String[] gameTypes = {"�������", "ī�����", "���÷���", "��������", "��������", "�������"};
	CardLayout cl = new CardLayout();
	JPanel centerPanel = new JPanel(cl);
	JPanel panel = new JPanel(new BorderLayout());
	DefaultListModel<String> cartList = new DefaultListModel<>();
	JList<String> cart = new JList<>(cartList);
	JScrollPane cartScrollPane = new JScrollPane(cart);
	List<Game> games = new ArrayList<>();
	
	public GameOrderUI() {
		setTitle("���� �޴�");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Vector<GameBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.gameAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			GameBean bean = vlist.get(i);
			games.add(new Game(bean.getGtype(), bean.getGname(), "�̹���", bean.getGprice()));
		}

		showGame();
		
		for (String gameType : gameTypes) {
			JButton gameTypeButton = new JButton(gameType);
			gameTypeButton.setPreferredSize(new Dimension(100, 30));
			gameTypeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component comp : northPanel.getComponents()) {
						if (comp instanceof JButton) {
							comp.setBackground(null);
						}
					}
					gameTypeButton.setBackground(Color.YELLOW);
					cl.show(centerPanel, gameType);
				}
			});
			northPanel.add(gameTypeButton);
		}

		JButton homeButton = new JButton("���ư���");
		// Ȩ ��ư�� ������ �� ���ο� ���� UI�� ������ �ʰ� ���� UI�� �ݵ��� ����
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		northPanel.add(homeButton);
		panel.add(northPanel, BorderLayout.NORTH);
		
		cl.show(centerPanel, "�������");
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

		JButton cancelButton = new JButton("���");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cart.getSelectedIndex() != -1) {
					String selected = cartList.getElementAt(cart.getSelectedIndex());
					String[] split = selected.split(" ");
					int price = 0;
					for (int i = split.length - 1; i >= 0; i--) {
						try {
							price = Integer.parseInt(split[i]);
							break;
						} catch (NumberFormatException ex) {
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
		
		JPanel totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(totalLabel, BorderLayout.NORTH);
		
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
		
		JButton purchaseButton = new JButton("����");
		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cartList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "��ٱ��ϰ� ����ֽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "���Ű� �Ϸ�Ǿ����ϴ�.");
					cartList.clear();
					requestField.setText("");
				}
				totalLabel.setText("�� �ֹ� �ݾ�: 0");
			}
		});
		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}
	
	public void showGame() {
		for (String gameType : gameTypes) {
			JPanel gamePanel = new JPanel(new GridLayout(0, 4));// ���� ũ��� 0���� �����Ͽ� �ڵ����� �����ǵ��� ��
			for (Game game : games) {
				if (game.getType().equals(gameType)) {
					JButton gameButton = new JButton("<html><center><img src='" + game.getImageUrl() + "' width='100' height='100'/><br/>" + game.getName() + "<br/>" + game.getPrice() + "</center></html>");
					gameButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JFrame gameDetail = new JFrame("���� �� ����");
							gameDetail.setSize(300, 250);
							gameDetail.setLocationRelativeTo(null);
							
							JPanel detailPanel = new JPanel();
							detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
							
							JLabel nameLabel = new JLabel("���� �̸�: " + game.getName());	
							JLabel priceLabel = new JLabel("����: " + game.getPrice());

							Font font = new Font("Dialog", Font.BOLD, 20); // ��Ʈ����
							nameLabel.setFont(font);
							priceLabel.setFont(font);
							
							detailPanel.add(nameLabel);
							detailPanel.add(priceLabel);
							gameDetail.add(detailPanel);
							
							JButton addButton = new JButton("�ֹ��ϱ�");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									String gameNamePrice = game.getName() + " | " + game.getPrice();
								
									int currentIndex = -1;
									
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(game.getName())) {// ���� �̸����� ã��
											currentIndex = i;
											break;
										}
									}
	
									if (currentIndex != -1) { // �̹� ����Ʈ�� �ִٸ�
										JOptionPane.showMessageDialog(null, "������ �ִ� 1���� ���� �����մϴ�.", "���", JOptionPane.WARNING_MESSAGE);
									} else { // ����Ʈ�� ���ٸ�
										cartList.addElement(gameNamePrice + " x" + 1);// ���� �߰�
										totalLabel.setText("�� �ֹ� �ݾ�: " + ((Integer.parseInt(totalLabel.getText().split(": ")[1]) + game.getPrice())));
									}
									gameDetail.dispose();
								}
							});
							gameDetail.add(addButton, BorderLayout.SOUTH);
							gameDetail.setVisible(true);
						}
					});
					gamePanel.add(gameButton);
				}
			}
			JScrollPane scrollPane = new JScrollPane(gamePanel); //gamePanel�� JScrollPane�� �߰�
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// �׻� ���� ��ũ�ѹ� ǥ��
			centerPanel.add(scrollPane, gameType); //JScrollPane�� centerPanel�� �߰�
		}
	}
	
	public static void main(String[] args) {
		GameOrderUI GUI = new GameOrderUI();
	}
}