package MAP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;



import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class itemEditor  extends JFrame implements gameConfig{
	private JTextField text_ID;
	private JTextField text_NAME;
	private JTextField textFILE;
	private JTextField text_ATK;
	private JTextField textBreak;
	private JTextField textStackAmount;
	private JTextField text_HungerHeal;
	public itemEditor() {
		setTitle("物品编辑器");
		//this.setSize(340, 261);
		setBounds(400,200,340, 253);
		this.setDefaultCloseOperation(3);  
		setResizable(false);
		getContentPane().setLayout(null);
		
		text_ID = new JTextField();
		text_ID.setBounds(94, 41, 66, 21);
		getContentPane().add(text_ID);
		text_ID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u7269\u54C1ID");
		lblNewLabel.setBounds(10, 42, 74, 15);
		getContentPane().add(lblNewLabel);
		
		text_NAME = new JTextField();
		text_NAME.setColumns(10);
		text_NAME.setBounds(94, 72, 66, 21);
		getContentPane().add(text_NAME);
		
		JLabel label = new JLabel("\u7269\u54C1\u540D\u5B57");
		label.setBounds(10, 73, 74, 15);
		getContentPane().add(label);
		
		textFILE = new JTextField();
		textFILE.setColumns(10);
		textFILE.setBounds(10, 10, 49, 21);
		getContentPane().add(textFILE);
		
		JButton btn_CREATE = new JButton("\u8BFB\u5165\u6216\u521B\u5EFAID");

		btn_CREATE.setBounds(69, 8, 105, 23);
		getContentPane().add(btn_CREATE);
		
		JButton btn_SAVE = new JButton("\u4FDD\u5B58ID");

		btn_SAVE.setBounds(10, 191, 105, 23);
		getContentPane().add(btn_SAVE);
		
		JLabel label_1 = new JLabel("\u7269\u54C1\u7C7B\u578B");
		label_1.setBounds(10, 106, 74, 15);
		getContentPane().add(label_1);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		box_type.setBounds(94, 103, 66, 21);
		box_type.addItem("普通");
		box_type.addItem("木");
		box_type.addItem("石");
		box_type.addItem("铁");
		box_type.addItem("金");
		box_type.addItem("钻");
		box_type.addItem("泥土");
		
		getContentPane().add(box_type);
		
		JLabel label_2 = new JLabel("\u653B\u51FB\u529B");
		label_2.setBounds(10, 140, 74, 15);
		getContentPane().add(label_2);
		
		text_ATK = new JTextField();
		text_ATK.setColumns(10);
		text_ATK.setBounds(94, 139, 66, 21);
		getContentPane().add(text_ATK);
		
		final JComboBox<String> Box_tool = new JComboBox<String>();
		Box_tool.setBounds(254, 140, 66, 21);
		getContentPane().add(Box_tool);
		Box_tool.addItem("普通");
		Box_tool.addItem("铲");
		Box_tool.addItem("镐");
		Box_tool.addItem("斧");
		Box_tool.addItem("锄");
		
		JLabel label_3 = new JLabel("\u5DE5\u5177\u7C7B\u578B");
		label_3.setBounds(170, 143, 74, 15);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\u53EF\u5426\u653E\u7F6E");
		label_4.setBounds(170, 109, 74, 15);
		getContentPane().add(label_4);
		
		final JComboBox<String> Box_canPut = new JComboBox<String>();
		Box_canPut.setBounds(254, 106, 66, 21);
		getContentPane().add(Box_canPut);
		Box_canPut.addItem("否");
		Box_canPut.addItem("是");
		final JComboBox<String> Box_canEat = new JComboBox<String>();
		Box_canEat.setBounds(254, 170, 66, 21);
		getContentPane().add(Box_canEat);
		Box_canEat.addItem("否");
		Box_canEat.addItem("是");
		
		textBreak = new JTextField();
		textBreak.setColumns(10);
		textBreak.setBounds(254, 41, 66, 21);
		getContentPane().add(textBreak);
		
		JLabel label_5 = new JLabel("\u8010\u4E45\u5EA6");
		label_5.setBounds(170, 42, 74, 15);
		getContentPane().add(label_5);
		
		textStackAmount = new JTextField();
		textStackAmount.setColumns(10);
		textStackAmount.setBounds(254, 72, 66, 21);
		getContentPane().add(textStackAmount);
		
		JLabel label_6 = new JLabel("\u5806\u53E0\u6570\u91CF");
		label_6.setBounds(170, 73, 74, 15);
		getContentPane().add(label_6);
		

		
		JLabel label_7 = new JLabel("\u53EF\u5426\u98DF\u7528");
		label_7.setBounds(170, 173, 74, 15);
		getContentPane().add(label_7);
		
		text_HungerHeal = new JTextField();
		text_HungerHeal.setColumns(10);
		text_HungerHeal.setBounds(254, 201, 66, 21);
		getContentPane().add(text_HungerHeal);
		
		JLabel label_8 = new JLabel("\u9965\u997F\u5EA6\u6062\u590D");
		label_8.setBounds(170, 202, 74, 15);
		getContentPane().add(label_8);
		
		
		
		
		
		
		
		
		
		
		
		btn_SAVE.addActionListener(new ActionListener() {//保存
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(enityPath+textFILE.getText()+".enity");

					DataOutputStream dos = new DataOutputStream(fos);
					
					dos.writeUTF(text_NAME.getText());
					dos.writeInt(box_type.getSelectedIndex());
					dos.writeInt(Integer.valueOf(text_ATK.getText()));
					dos.writeInt(Integer.valueOf(textBreak.getText()));
					dos.writeInt(Integer.valueOf(textStackAmount.getText()));
					dos.writeInt(Box_canPut.getSelectedIndex());
					dos.writeInt(Box_tool.getSelectedIndex());
					dos.writeInt(Box_canEat.getSelectedIndex());
					dos.writeInt(Integer.valueOf(text_HungerHeal.getText()));
					
					dos.flush();
					dos.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		
		
		btn_CREATE.addActionListener(new ActionListener() {//读入或创建方块资料
			public void actionPerformed(ActionEvent e) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(enityPath+textFILE.getText()+".enity");

					DataInputStream dis = new DataInputStream(fis);
					text_ID.setText(textFILE.getText());
					text_NAME.setText(dis.readUTF());
					box_type.setSelectedIndex(dis.readInt());
					text_ATK.setText(String.valueOf(dis.readInt()));
					textBreak.setText(String.valueOf(dis.readInt()));
					textStackAmount.setText(String.valueOf(dis.readInt()));
					Box_canPut.setSelectedIndex(dis.readInt());
					Box_tool.setSelectedIndex(dis.readInt());
					Box_canEat.setSelectedIndex(dis.readInt());
					text_HungerHeal.setText(String.valueOf(dis.readInt()));
					
					dis.close();
					fis.close();
				} catch (FileNotFoundException e1) {//没有该文件
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(enityPath+textFILE.getText()+".enity");

						DataOutputStream dos = new DataOutputStream(fos);
						
						
						dos.flush();
						dos.close();
						System.out.println("创建新物品");
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				
			}
		});
		setVisible(true);
	}
}
