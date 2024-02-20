package meeting_room;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

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

public class GameOrderUI extends JFrame implements Runnable, ActionListener {
	
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	
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
	JButton purchaseButton = new JButton("�ֹ��ϱ�");
	JFrame gameDetail;
	String room;
	String in_time;
	JTextField requestField;
	boolean flag = false;
	
	public void updateTotalPrice() {
	    int total = 0;
	    for (int i = 0; i < cartList.size(); i++) {
	        String item = cartList.getElementAt(i);
	        String[] split = item.split("\\s+(?=x)");
	        String gameInfo = split[0].trim();  // "ȣ���� ���� - 2000"
	        int quantity = split.length == 2 ? Integer.parseInt(split[1].trim().replaceAll("[^0-9]", "")) : 1;
	        String gameName = gameInfo.replaceAll("\\s+-.*", "").trim();
	        int price = 0;
	        for (Game game : games) {
	            if (game.getName().equals(gameName)) {
	                price = game.getPrice();
	                break;
	            }
	        }
	        total += price * quantity;
	    }
	    totalLabel.setText("�� �ֹ� �ݾ�: " + total + "��");
	}

	       
	 
	 

	
	public GameOrderUI(String room, String in_time) {
		this.room = room;
		this.in_time = in_time;
		
		setTitle("���� �޴�");
		setSize(1000, 800);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		cart.setCellRenderer(new CartItemRenderer());
		
		cart.setFixedCellHeight(50); // Adjust the value as needed

		cart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = cart.locationToIndex(e.getPoint());
                if (selectedIndex != -1) {
                    Rectangle bounds = cart.getCellBounds(selectedIndex, selectedIndex);
                    if (bounds != null && e.getX() >= bounds.width - 80) { // Adjust the value as needed
                        cartList.remove(selectedIndex);
                        updateTotalPrice();
                    }
                }
            }
        });
		
		//â�� X �� ������ ���� ��
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//������(���) �������� �������� �� �Բ� �ݱ�
				if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
			}
		});
		
		cart.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int index = cart.locationToIndex(e.getPoint());
		            if (index >= 0) {
		                String item = cartList.getElementAt(index);
		                String[] split = item.split("x");
		                int oldQuantity = 1;
		                if (split.length == 2) {
		                    try {
		                        oldQuantity = Integer.parseInt(split[1].trim().replaceAll("[^0-9]", ""));
		                    } catch (NumberFormatException ex) {
		                        // Handle the exception
		                    }
		                }

		                SpinnerNumberModel model = new SpinnerNumberModel(oldQuantity, 1, 10, 1);
		                JSpinner spinner = new JSpinner(model);
		                JOptionPane.showMessageDialog(null, spinner, "������ �Է��ϼ���", JOptionPane.QUESTION_MESSAGE);
		                try {
		                    int newQuantity = (Integer) spinner.getValue();
		                    cartList.setElementAt(split[0].trim() + " x" + newQuantity, index);
		                    
		                  
		                    updateTotalPrice();
		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(null, "��ȿ�� ������ �Է��ϼ���", "����", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        }
		    }
		});
		
		//���� ������ ����
		Vector<GameBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.gameAll();
		
		for (int i = 0; i < vlist.size(); i++) {
			GameBean bean = vlist.get(i);
			String name = bean.getGname().trim();
			String img;
			if (name.contains(":"))
				img = "./Game/" + name.replace(":", "_") + ".jpg";
			else
				img = "./Game/" + name + ".jpg";
			int price = bean.getGprice();
			games.add(new Game(bean.getGtype(), name, img, price));
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

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		// ��ü ��� ��ư�� �̺�Ʈ �ڵ鷯
				JButton cancelButton = new JButton("��ü ���");
				cancelButton.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        // ��ٱ��� �� ��� �׸� ����
				        cartList.clear(); 
				        // �� �ֹ� �ݾ� ������Ʈ
				        totalLabel.setText("�� �ֹ� �ݾ�: 0");
				    }
				});
				buttonPanel.add(cancelButton);
		
				//�� �ֹ� �ݾ� ǥ��
				JPanel totalPanel = new JPanel(new BorderLayout());
				totalPanel.add(totalLabel, BorderLayout.NORTH);
				
				//�ֹ� ��û���� �Է� �г�
				JPanel requestPanel = new JPanel();
				JLabel requestLabel = new JLabel("�ֹ� ��û����: ");
				requestField = new JTextField(20);
				requestField.setPreferredSize(new Dimension(200, 40));
				requestPanel.add(requestLabel);
				requestPanel.add(requestField);
				totalPanel.add(requestPanel, BorderLayout.CENTER);
				
				rightPanel.add(totalPanel, BorderLayout.NORTH);
				rightPanel.add(buttonPanel, BorderLayout.SOUTH);
				
				panel.add(rightPanel, BorderLayout.EAST);
		

		purchaseButton.addActionListener(this);

		buttonPanel.add(purchaseButton);
		add(panel);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == purchaseButton) {
			try {
				new Thread(this).start();
				if (flag == false) {
					JOptionPane.showMessageDialog(null, "���� ���� �ð��� �ƴմϴ�.", "���", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (flag == true) {
		            if (cartList.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "��ٱ��ϰ� ����ֽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
		            } else {
		                MyInfoMgr mgr = new MyInfoMgr();
		                MyInfoBean bean = mgr.select(LoginUI.ID);
		                int money = bean.getMoney();
		                int total = Integer.parseInt(totalLabel.getText().split(": ")[1].replace("��", ""));
		                if (money < total) {
		                    JOptionPane.showMessageDialog(null, "�ܾ��� �����մϴ�.", "���", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
		                for (int i = 0; i < cartList.size(); i++) {
		                    OrderInfoBean beans = new OrderInfoBean();
		                        GameBean bean2 = mgr.game(cartList.getElementAt(i).split(" - ")[0]);
		                        beans.setRoom_no(room);
		                        beans.setGamename(bean2.getGame());
		                        beans.setGameid(LoginUI.ID);
		                        beans.setGameprice(bean2.getGprice() * Integer.parseInt(cartList.getElementAt(i).split("x")[1]));
		                        beans.setGamecount(Integer.parseInt(cartList.getElementAt(i).split("x")[1]));
		                        mgr.gamesales(beans);
		                }
		                OrderBean bean4 = new OrderBean();
		                bean4.setOrder_id(LoginUI.ID);
		                bean4.setOrder_room(room);
		                bean4.setOrder_total(total);
						if (!"".equals(requestField.getText()))
							bean4.setOrder_request(requestField.getText());
		                
		                LocalDateTime currentTime = LocalDateTime.now();
		                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                String day = currentTime.format(formatter);
		                TotalBean bean5 = mgr.useAll(room,  LoginUI.ID, day);
		                bean5.setGame_total(bean5.getGame_total() + total);
		                bean5.setTotal(bean5.getTotal() + total);
		                bean5.setIntime(in_time);
		                bean.setMoney(money - total);
		                if (mgr.charge(bean) && mgr.order(bean4) && mgr.totalprice(bean5)) {
		                    JOptionPane.showMessageDialog(null, "���Ű� �Ϸ�Ǿ����ϴ�.");
		                    sendMessage(MeetingProtocol.ID + MeetingProtocol.MODE + LoginUI.ID);
		                    sendMessage(MeetingProtocol.ORDER + MeetingProtocol.MODE + room);
		                    cartList.clear(); // ��ٱ��� ����
		                    ReserveUI.a.doClick();
		                    dispose();
		                    return;
		                }
		            }
		            totalLabel.setText("�� �ֹ� �ݾ�: 0");
				}
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		}
	}
	
	class CartItemRenderer extends JPanel implements ListCellRenderer<Object> {
	    private static final long serialVersionUID = 1L;

	    private JLabel label;
	    private JButton cancelButton;

	    public CartItemRenderer() {
	        setLayout(new BorderLayout());
	        setOpaque(true);

	        label = new JLabel();
	        label.setPreferredSize(new Dimension(200, 30));

	        cancelButton = new JButton("���");
	        

	        JPanel contentPanel = new JPanel(new BorderLayout());
	        contentPanel.add(label, BorderLayout.CENTER);
	        contentPanel.add(cancelButton, BorderLayout.EAST);

	        add(contentPanel, BorderLayout.CENTER);
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
	            boolean cellHasFocus) {
	        label.setText(value.toString());

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }

	        return this;
	    }
	}

	public void showGame() {
		//���� ī�װ����� JPanel ����
	    for (String gameType : gameTypes) {
	        JPanel gamePanel = new JPanel(new GridLayout(0, 5)); // ���� ũ��� ���Ѵ��, ���� ũ��� 5�� �����Ͽ� ���� ũ���� �׸��带 �����մϴ�.
	        int count = 0; // ���� ��ư�� �߰��� ���� �����մϴ�.

	        for (Game game : games) {
	            if (game.getType().equals(gameType)) {
				ImageIcon icon = new ImageIcon(game.getImageUrl());
				Image originalImage = icon.getImage();
				Image resizedImage = originalImage.getScaledInstance(150, 140, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(resizedImage);
				
	            	JButton gameButton = new JButton("<html><center>" + game.getName() + "<br>" + game.getPrice() + "��</center></html>");
				gameButton.setIcon(resizedIcon);
	            	gameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	            	gameButton.setHorizontalTextPosition(SwingConstants.CENTER);
	            	gameButton.setPreferredSize(new Dimension(100, 100));
					gameButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//�˾� â�� �����ϰ� ������
							if (gameDetail != null && gameDetail.isVisible() == true) gameDetail.dispose();
							gameDetail = new JFrame("���� �� ����");
							gameDetail.setSize(250, 150);
							gameDetail.setLocationRelativeTo(null);
							
							//�˾� â�� ���� ������ ǥ��
							JPanel gamePanel = new JPanel();
							gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
							
							JLabel nameLabel = new JLabel("���� �̸�: " + game.getName());	
							JLabel priceLabel = new JLabel("����: " + game.getPrice());
							
							SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // �ʱ� �� 1, �ּ� �� 1, �ִ� �� 10, �ܰ� 1
							JSpinner quantitySpinner = new JSpinner(model);
							JLabel quantityLabel = new JLabel("����: ");
							quantitySpinner.setPreferredSize(new Dimension(50, 25));
							
							JPanel quantityPanel = new JPanel();
							quantityPanel.add(quantityLabel);
							quantityPanel.add(quantitySpinner);
							quantityLabel.setLabelFor(quantitySpinner);
							
							gamePanel.add(nameLabel);
							gamePanel.add(priceLabel);
							gamePanel.add(quantityPanel);
							
							//��� ��ư �߰�
							JButton addButton = new JButton("���");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									//�ֹ� ������ ���� �߰�
									String gameNamePrice = game.getName() + " - " + game.getPrice();
									
									//���ǳʿ��� ���õ� ������ ������
							        int quantity = (Integer) quantitySpinner.getValue();

									//�ֹ� ������ �̹� ���� ������ ������
									int currentIndex = -1;
									for (int i = 0; i < cartList.size(); i++) {
										if (cartList.get(i).startsWith(gameNamePrice)) {// ���� �̸����� ã��
											currentIndex = i;
											break;
										}
									}

									 //�̹� ����Ʈ�� �ִٸ�
							        if (currentIndex != -1) {
							            String oldEntry = cartList.getElementAt(currentIndex);
							            String[] split = oldEntry.split("x");
							            int oldCount = split.length == 2 ? Integer.parseInt(split[1].trim().replaceAll("[^0-9]", "")) : 1;
							            cartList.setElementAt(gameNamePrice + " x" + (oldCount + quantity), currentIndex);  // ���� ����
							        } else {  //����Ʈ�� ���ٸ�
							            cartList.addElement(gameNamePrice + " x" + quantity);  // ������ŭ ���� �߰�
							        
							        }
							     // �� �ֹ� �ݾ� ������Ʈ
							        int total = Integer.parseInt(totalLabel.getText().split(": ")[1].replaceAll("[^0-9]", "")) + game.getPrice() * quantity;
							        totalLabel.setText("�� �ֹ� �ݾ�: " + total);
							        


							        // �� �ֹ� �ݾ� ������Ʈ
							        updateTotalPrice();

							        gameDetail.dispose();
							    }
							});
							gamePanel.add(addButton);
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
	
	public void run() {
		try {
			String host = "127.0.0.1";
			int port = MeetingServer.PORT;
			connect(host, port);
			flag = true;
			while(true) {
				String line = in.readLine();
				if(line==null)
					break;
				else
					routine(line);
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
	}
	
	public void routine(String line) {
		int idx = line.indexOf(MeetingProtocol.MODE);
		String cmd = line.substring(0, idx);
		String data = line.substring(idx + 1);
		
		if (cmd.equals(MeetingProtocol.MESSAGE)) {
			idx = data.indexOf(';');
			cmd = data.substring(0, idx);
			data = data.substring(idx + 1);
		}
	}
	
	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		if (out != null)
			out.println(msg);
	}
	
	public static void main(String[] args) {
		
	}
}