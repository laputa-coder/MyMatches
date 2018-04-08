package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.FileInputStream;

import GameGraphics.mainFrame;
import GameResource.gameConfig;

public class block implements gameConfig {
	protected int i;
	protected int j;
	private Image ima;
	private String name;
	protected boolean drawRect = true;
	private int ID;
	protected int X = 0;
	protected int Y = 0;

	public block(int i, int j, Image ima, int id) {
		this.i = i;
		this.j = j;
		this.ima = ima;
		this.ID = id;
		X = mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX;
		Y = mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY;
	}

	public int getID() {
		return ID;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public static int getHP(int id) {
		FileInputStream fis;
		int result = 0;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			dis.readInt();
			result = dis.readInt();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getDropID(int id) {
		FileInputStream fis;
		int result = 0;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			dis.readInt();
			dis.readInt();
			result = dis.readInt();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getDropAmount(int id) {
		FileInputStream fis;
		int result = 0;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			result = dis.readInt();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getName(int id) {
		FileInputStream fis;
		String result = null;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			result = dis.readUTF();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getType(int id) {
		FileInputStream fis;
		int result = -1;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			result = dis.readInt();
			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getSkill(int id) {
		FileInputStream fis;
		int result = 0;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			result=dis.readInt();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean getCanIntersect(int id){
		FileInputStream fis;
		int result = 0;
		try {
			fis = new FileInputStream(blockPath + id + ".block");

			DataInputStream dis = new DataInputStream(fis);

			dis.readUTF();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			dis.readInt();
			result=dis.readInt();

			dis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result==0?false:true;
	}
	
	public String getName() {
		return this.name;
	}

	public String toString() {
		return i + " " + j;
	}

	public void setImg(Image ima) {
		this.ima = ima;
	}

	public Image getImg() {
		return ima;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public Rectangle getRect() {// 获取矩形碰撞区域
		return new Rectangle(mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX,
				mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY, mainFrame.blockWidth, mainFrame.blockHeight);
	}

	public void draw(Graphics g) {
		X = mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX;
		Y = mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY;
		g.drawImage(getImg(), X, Y, mainFrame.blockWidth, mainFrame.blockHeight, null);
		if (drawRect == true) {
			g.setColor(Color.black);
			g.drawRect(X, Y, mainFrame.blockWidth, mainFrame.blockHeight);
		}

	}

	public void setDrawRect(boolean b) {
		drawRect = b;
	}
}
