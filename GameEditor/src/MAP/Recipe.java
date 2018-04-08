package MAP;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Recipe extends JFrame implements gameConfig {
	private JTextField text_ID;
	private JTextField text0;
	private JTextField text1;
	private JTextField text2;
	private JTextField text5;
	private JTextField text4;
	private JTextField text3;
	private JTextField text8;
	private JTextField text7;
	private JTextField text6;
	private JTextField textAmount;

	public Recipe() {
		setTitle("合成表编辑器");
		// this.setSize(197, 307);
		setBounds(400, 200, 193, 242);
		this.setDefaultCloseOperation(3);
		setResizable(false);
		getContentPane().setLayout(null);

		JButton button = new JButton("\u8BFB\u5165\u6216\u521B\u5EFAID");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(recipePath + text_ID.getText() + ".recipe");

					DataInputStream dis = new DataInputStream(fis);
					text0.setText(String.valueOf(dis.readInt()));
					text1.setText(String.valueOf(dis.readInt()));
					text2.setText(String.valueOf(dis.readInt()));
					text3.setText(String.valueOf(dis.readInt()));
					text4.setText(String.valueOf(dis.readInt()));
					text5.setText(String.valueOf(dis.readInt()));
					text6.setText(String.valueOf(dis.readInt()));
					text7.setText(String.valueOf(dis.readInt()));
					text8.setText(String.valueOf(dis.readInt()));
					textAmount.setText(String.valueOf(dis.readInt()));
					dis.close();
					fis.close();
				} catch (FileNotFoundException e1) {// 没有该文件
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(recipePath + text_ID.getText() + ".recipe");
						DataOutputStream dos = new DataOutputStream(fos);
						dos.flush();
						dos.close();
						System.out.println("创建新合成表");
					} catch (Exception e3) {
						e3.printStackTrace();
					}

				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		button.setBounds(69, 10, 105, 23);
		getContentPane().add(button);

		text_ID = new JTextField();
		text_ID.setColumns(10);
		text_ID.setBounds(10, 12, 49, 21);
		getContentPane().add(text_ID);

		text0 = new JTextField();
		text0.setColumns(10);
		text0.setBounds(10, 51, 49, 21);
		getContentPane().add(text0);

		text1 = new JTextField();
		text1.setColumns(10);
		text1.setBounds(69, 51, 49, 21);
		getContentPane().add(text1);

		text2 = new JTextField();
		text2.setColumns(10);
		text2.setBounds(128, 51, 49, 21);
		getContentPane().add(text2);

		text5 = new JTextField();
		text5.setColumns(10);
		text5.setBounds(128, 84, 49, 21);
		getContentPane().add(text5);

		text4 = new JTextField();
		text4.setColumns(10);
		text4.setBounds(69, 84, 49, 21);
		getContentPane().add(text4);

		text3 = new JTextField();
		text3.setColumns(10);
		text3.setBounds(10, 84, 49, 21);
		getContentPane().add(text3);

		text8 = new JTextField();
		text8.setColumns(10);
		text8.setBounds(128, 115, 49, 21);
		getContentPane().add(text8);

		text7 = new JTextField();
		text7.setColumns(10);
		text7.setBounds(69, 115, 49, 21);
		getContentPane().add(text7);

		text6 = new JTextField();
		text6.setColumns(10);
		text6.setBounds(10, 115, 49, 21);
		getContentPane().add(text6);

		JButton button_1 = new JButton("\u4FDD\u5B58ID");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(recipePath + text_ID.getText() + ".recipe");

					DataOutputStream dos = new DataOutputStream(fos);

					dos.writeInt(Integer.valueOf(text0.getText()));
					dos.writeInt(Integer.valueOf(text1.getText()));
					dos.writeInt(Integer.valueOf(text2.getText()));
					dos.writeInt(Integer.valueOf(text3.getText()));
					dos.writeInt(Integer.valueOf(text4.getText()));
					dos.writeInt(Integer.valueOf(text5.getText()));
					dos.writeInt(Integer.valueOf(text6.getText()));
					dos.writeInt(Integer.valueOf(text7.getText()));
					dos.writeInt(Integer.valueOf(text8.getText()));
					dos.writeInt(Integer.valueOf(textAmount.getText()));
					dos.flush();
					dos.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		button_1.setBounds(39, 180, 105, 23);
		getContentPane().add(button_1);

		textAmount = new JTextField();
		textAmount.setColumns(10);
		textAmount.setBounds(79, 146, 65, 21);
		getContentPane().add(textAmount);

		JLabel label = new JLabel("\u751F\u6210\u6570\u91CF");
		label.setBounds(23, 146, 58, 15);
		getContentPane().add(label);
		this.setVisible(true);
	}
}
