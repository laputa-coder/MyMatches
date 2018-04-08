package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;

import GameGraphics.mainFrame;
import GameResource.gameConfig;

public class Enity implements gameConfig {
	block bk;
	public int i = 0;
	public int j = 0;
	int x = 0;
	int y = 0;
	int aX = 0;// 水平偏移
	int aY = 0;// 垂直偏移
	Image im;
	private int ID=-1;
	private String Name="无";// 名字
	private int Type=-1;// 类型 0"普通"1"木"2"石"3"铁"4"金"5"钻"6"无敌"

	private int Atk=-1;// 攻击力
	private int Break;// 耐久度
	private int StackAmount;// 堆叠数量
	private int CanPut;// 0否 1是
	private int CanEat;// 0否 1是
	private int Tool;// 工具类型 0"普通"1"铲"2"镐"3"斧"4"锄"
	private int HungerHeal;// 饥饿度恢复值

	public Enity(){
		
	}
	public Enity(int id, int i, int j) {// 初始化物品
		FileInputStream fis;
		try {
			fis = new FileInputStream(enityPath + id + ".enity");

			DataInputStream dis = new DataInputStream(fis);
			this.ID = id;
			this.Name = new String(dis.readUTF());
			this.Type = dis.readInt();
			this.Atk = dis.readInt();
			this.Break = dis.readInt();
			this.StackAmount = dis.readInt();
			this.CanPut = dis.readInt();
			this.Tool = dis.readInt();
			this.CanEat = dis.readInt();
			this.HungerHeal = dis.readInt();
			// Image Rect = tools.makeColorTransparent(
			// new ImageIcon(tools.class.getClassLoader().getResource(
			// "images/display/Rect.png")).getImage(), Color.white);

			im = new ImageIcon("./images/blocks/" + id + ".png").getImage();
			this.i = i;
			this.j = j;
			dis.close();
			fis.close();
		} catch (FileNotFoundException e1) {// 没有该文件
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(enityPath + id + ".enity");

				DataOutputStream dos = new DataOutputStream(fos);

				dos.flush();
				dos.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public Enity(int id, int i, int j, int mX, int mY) {// 初始化物品
		this(id, i, j);
		aX = mX;
		aY = mY;
	}

	public Enity(block b) {// 初始化掉落物品
		this(b.getID(), 0, 0);
		this.bk = b;
		this.i = bk.getI();
		this.j = bk.getJ();
		b.getID();
		im = b.getImg();
	}

	public int getID() {
		return ID;
	}

	public String getName() {// 获取Name
		return this.Name;
	}

	public int getType() {// 获取Type
		return this.Type;
	}

	public int getAtk() {// 获取Atk
		return this.Atk;
	}

	public void setI(int a) {// 设置i
		this.i = a;
	}

	public void setJ(int a) {// 设置j
		this.j = a;
	}

	public void setBreak(int a) {// 设置耐久度
		this.Break = a;
	}

	public int getBreak() {// 获取Break
		return this.Break;
	}

	public int getStackAmount() {// 获取物品堆叠数量
		return this.StackAmount;
	}

	public boolean getCanPut() {
		return this.CanPut == 1 ? true : false;
	}

	public boolean getCanEat() {
		return this.CanEat == 1 ? true : false;
	}

	public int getToolType() {// 获取ToolType
		return this.Tool;
	}

	public int getHungerHeal() {// 获取饥饿度恢复值
		return this.HungerHeal;
	}

	public Image getImg() {// 获取物品图片
		return im;
	}

	public Rectangle getRect() {// 获取矩形碰撞区域
		return new Rectangle(x, y, mainFrame.blockWidth / 2, mainFrame.blockHeight / 2);
	}

	public void drawDrop(Graphics g) {
		x = mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX + mainFrame.blockWidth / 2 + aX;
		y = mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY + mainFrame.blockHeight / 2 + aY;
		g.drawImage(im, x, y, mainFrame.blockWidth / 2, mainFrame.blockHeight / 2, null);
		g.setColor(Color.red);
		// g.drawRect(x, y, blockWidth/2, blockHeight/2);
	}
}