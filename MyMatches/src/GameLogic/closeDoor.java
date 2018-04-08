package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import GameGraphics.mainFrame;

public class closeDoor extends block{

	public closeDoor(int i, int j, Image ima, int id) {
		super(i, j, ima, id);
	}
	public Rectangle getRect() {// 获取矩形碰撞区域
		return new Rectangle(mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX,
				mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY, 
				mainFrame.blockWidth, 
				mainFrame.blockHeight);
	}

	public void draw(Graphics g) {
		X = mainFrame.p.aX + (i - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX;
		Y = mainFrame.p.aY + (j - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY;
		g.drawImage(getImg(), X+mainFrame.blockWidth/2, Y, 
				mainFrame.blockWidth/4, 
				mainFrame.blockHeight, null);
		if (drawRect == true) {
			g.setColor(Color.black);
			g.drawRect(X+mainFrame.blockWidth/2, Y, 
					mainFrame.blockWidth/4,
					mainFrame.blockHeight);
		}

	}
}
