package MAP;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

class MyPanel extends JPanel implements gameConfig {

	public void paint(Graphics g) {
		super.paint(g);
		// g.drawImage(ic[0], 30, 30, 30, 30, null);
		for (int i = 0; i < panelHeight / blockWidth; i++) {
			for (int j = 0; j < panelWidth / blockWidth; j++) {
				if (map.icons[i][j] != null) {
					g.drawImage(map.icons[i][j], getDrawX(j), getDrawY(i), 30, 30, null);
				}
			}
		}
		repaint();
	}

	// 将数组下标转化成对应的图片左上角坐标
	public int getDrawX(int j) {
		int x = j * blockWidth;
		return x;
	}

	// 将数组下标转化成对应的图片左上角坐标
	public int getDrawY(int i) {
		int y = i * blockWidth;
		return y;
	}
}

public class map extends JFrame implements MouseListener, gameConfig {
	static int[][] map = new int[panelHeight / blockWidth][panelWidth / blockWidth];
	static Image[][] icons = new Image[panelHeight / blockWidth][panelWidth / blockWidth];
	public boolean draw = false;
	public boolean clear = false;

	public static void main(String[] args) {
		map sm = new map();
		blockSet bs = new blockSet();
		new itemEditor();
		new Recipe();
	}

	public map() {
		setTitle("1");
		this.setSize(700, 700);
		this.setBounds(200, 0, 700, 700);
		this.setDefaultCloseOperation(3);
		setResizable(false);
		getContentPane().setLayout(null);

		final MyPanel panel = new MyPanel();
		panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		// panel.setSize(1000,1000);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 498, 661);
		getContentPane().add(scrollPane);

		// scrollPane.setViewportView(panel);
		// FlowLayout flowLayout = (FlowLayout) panel.getLayout();

		final JComboBox<Integer> comboBox = new JComboBox<Integer>();
		for (int i = 0; i < ic.length; i++) {

			comboBox.addItem(i + 1);
		}

		comboBox.setBounds(505, 40, 169, 21);
		getContentPane().add(comboBox);

		JButton btnNewButton = new JButton("\u4FDD\u5B58");
		btnNewButton.setBounds(508, 87, 93, 23);
		getContentPane().add(btnNewButton);

		JButton button = new JButton("\u8BFB\u5165");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(path);

					DataInputStream dis = new DataInputStream(fis);
					int i = panelHeight / blockWidth;
					int j = panelWidth / blockWidth;
					i = dis.readInt();
					j = dis.readInt();
					for (int ii = 0; ii < i; ii++) {
						for (int jj = 0; jj < j; jj++) {
							map[ii][jj] = dis.readInt();
							if (map[ii][jj] >= 1)
								icons[ii][jj] = ic[map[ii][jj] - 1];
						}
					}
					dis.close();
					fis.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panel.repaint();
			}
		});
		button.setBounds(508, 128, 93, 23);
		getContentPane().add(button);


		panel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (draw == true) {
					int j = e.getX() / blockWidth;
					int i = e.getY() / blockWidth;
					System.out.println(i + "<>" + j);
					Image icon = (Image) ic[(int) comboBox.getSelectedItem() - 1];
					map[i][j] = comboBox.getSelectedIndex() + 1;
					System.out.println(map[i][j]);
					icons[i][j] = icon;
				} else if (clear == true) {
					int j = e.getX() / blockWidth;
					int i = e.getY() / blockWidth;
					map[i][j] = 0;
					icons[i][j] = null;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		panel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					clear=false;
					draw = !draw;
					System.out.println(draw);
				} else if (e.getButton() == 3) {
					draw=false;
					clear = !clear;
					System.out.println(clear);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(path);

					DataOutputStream dos = new DataOutputStream(fos);
					int i = panelHeight / blockWidth;
					int j = panelWidth / blockWidth;
					dos.writeInt(i);
					dos.writeInt(j);
					for (int ii = 0; ii < i; ii++) {
						for (int jj = 0; jj < j; jj++) {
							dos.writeInt(map[ii][jj]);

						}
					}
					dos.flush();
					dos.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		this.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
