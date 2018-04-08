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

public class blockSet  extends JFrame implements gameConfig{
	private JTextField text_ID;
	private JTextField text_NAME;
	private JTextField textFILE;
	private JTextField text_HP;
	private JTextField text_DropID;
	private JTextField text_DropAmount;
	public blockSet() {
		setTitle("方块编辑器");
		//this.setSize(197, 307);
		setBounds(400,200,331, 267);
		this.setDefaultCloseOperation(3);  
		setResizable(false);
		getContentPane().setLayout(null);
		
		text_ID = new JTextField();
		text_ID.setBounds(94, 41, 66, 21);
		getContentPane().add(text_ID);
		text_ID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u65B9\u5757ID");
		lblNewLabel.setBounds(10, 42, 74, 15);
		getContentPane().add(lblNewLabel);
		
		text_NAME = new JTextField();
		text_NAME.setColumns(10);
		text_NAME.setBounds(94, 72, 66, 21);
		getContentPane().add(text_NAME);
		
		JLabel label = new JLabel("\u65B9\u5757\u540D\u5B57");
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

		btn_SAVE.setBounds(10, 205, 105, 23);
		getContentPane().add(btn_SAVE);
		
		JLabel label_1 = new JLabel("\u65B9\u5757\u7C7B\u578B");
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
		
		
		final JComboBox<String> box_skill = new JComboBox<String>();
		box_skill.setBounds(254, 72, 66, 21);
		box_skill.addItem("普通");
		box_skill.addItem("箱子");
		box_skill.addItem("熔炉");
		box_skill.addItem("合成台");
		box_skill.addItem("附魔台");
		getContentPane().add(box_skill);
		
		JComboBox<String> box_canIntersect = new JComboBox<String>();
		box_canIntersect.setBounds(254, 100, 66, 21);
		box_canIntersect.addItem("false");
		box_canIntersect.addItem("true");
		getContentPane().add(box_canIntersect);
		
		JLabel label_2 = new JLabel("\u65B9\u5757\u751F\u547D");
		label_2.setBounds(10, 140, 74, 15);
		getContentPane().add(label_2);
		
		text_HP = new JTextField();
		text_HP.setColumns(10);
		text_HP.setBounds(94, 139, 66, 21);
		getContentPane().add(text_HP);
		
		text_DropID = new JTextField();
		text_DropID.setColumns(10);
		text_DropID.setBounds(94, 171, 66, 21);
		getContentPane().add(text_DropID);
		
		JLabel lblid = new JLabel("\u6389\u843D\u7269ID");
		lblid.setBounds(10, 172, 74, 15);
		getContentPane().add(lblid);
		
		text_DropAmount = new JTextField();
		text_DropAmount.setColumns(10);
		text_DropAmount.setBounds(254, 41, 66, 21);
		getContentPane().add(text_DropAmount);
		
		JLabel label_3 = new JLabel("\u6389\u843D\u7269\u6570\u91CF");
		label_3.setBounds(170, 42, 74, 15);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\u65B9\u5757\u4F5C\u7528");
		label_4.setBounds(170, 75, 74, 15);
		getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("\u662F\u5426\u78B0\u649E");
		label_5.setBounds(170, 103, 74, 15);
		getContentPane().add(label_5);
		

		
		
		
		
		
		
		
		
		
		
		
		btn_SAVE.addActionListener(new ActionListener() {//保存
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(blockPath+textFILE.getText()+".block");

					DataOutputStream dos = new DataOutputStream(fos);
					
					dos.writeUTF(text_NAME.getText());
					dos.writeInt(box_type.getSelectedIndex());
					dos.writeInt(Integer.valueOf(text_HP.getText()));
					dos.writeInt(Integer.valueOf(text_DropID.getText()));
					dos.writeInt(Integer.valueOf(text_DropAmount.getText()));
					dos.writeInt(box_skill.getSelectedIndex());
					dos.writeInt(box_canIntersect.getSelectedIndex());
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
					fis = new FileInputStream(blockPath+textFILE.getText()+".block");

					DataInputStream dis = new DataInputStream(fis);
					text_ID.setText(textFILE.getText());
					text_NAME.setText(dis.readUTF());
					box_type.setSelectedIndex(dis.readInt());
					text_HP.setText(String.valueOf(dis.readInt()));
					text_DropID.setText(String.valueOf(dis.readInt()));
					text_DropAmount.setText(String.valueOf(dis.readInt()));
					box_skill.setSelectedIndex(dis.readInt());
					box_canIntersect.setSelectedIndex(dis.readInt());
					
					dis.close();
					fis.close();
				} catch (FileNotFoundException e1) {//没有该文件
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(blockPath+textFILE.getText()+".block");

						DataOutputStream dos = new DataOutputStream(fos);
						
						
						dos.flush();
						dos.close();
						System.out.println("创建新方块");
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
