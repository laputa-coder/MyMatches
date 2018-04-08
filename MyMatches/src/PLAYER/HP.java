package PLAYER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameGraphics.mainFrame;
import GameResource.gameConfig;
import GameTool.tools;

public class HP implements gameConfig {
	player p;

	public HP(player p) {
		this.p = p;
	}

	public int getHP() {
		return p.player_HP;
	}

	public int getMaxHP() {
		return p.player_MaxHP;
	}

	public void setHP(int h) {
		p.player_HP = h;
	}

	public void setMaxHP(int h) {
		p.player_MaxHP = h;
	}

	public int getHunger() {
		return p.player_Hunger;
	}

	public int getMaxHunger() {
		return p.player_MaxHunger;
	}

	public void setHunger(int h) {
		p.player_Hunger = h;
	}

	public void setMaxHunger(int h) {
		p.player_MaxHunger = h;
	}

	public void draw(Graphics g) {
		BufferedImage bufferImage[] = new BufferedImage[6];
		Graphics gBuffer;
		// new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB)
		for (int i = 0; i < 6; i++) {
			bufferImage[i] = new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
			gBuffer = bufferImage[i].getGraphics();
			gBuffer.drawImage(pic_HP, 0, 0, 9, 9, 9 * i, 0, (i + 1) * 9, 9, null);
			gBuffer = null;
		}

		for (int i = 0; i < getMaxHP() / 10; i++) {// 每十点生命值为一颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[0], Color.white), mainFrame.panelWidth / 2 - 250 + i * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}

		for (int i = 0; i < getHP() / 10; i++) {// 每十点生命值为一颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[4], Color.white), mainFrame.panelWidth / 2 - 250 + i * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}
		for (int i = 0; i < getHP() / 5; i++) {// 每5生命值为半颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[5], Color.white), mainFrame.panelWidth / 2 - 250 + (i / 2) * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}

		for (int i = 0; i < 6; i++) {
			bufferImage[i] = new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
			gBuffer = bufferImage[i].getGraphics();
			gBuffer.drawImage(pic_HUNGER, 0, 0, 9, 9, 9 * i, 0, (i + 1) * 9, 9, null);
			gBuffer = null;
		}

		for (int i = 0; i < getMaxHunger() / 10; i++) {// 每十点生命值为一颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[0], Color.white), mainFrame.panelWidth / 2 + 20 + i * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}

		for (int i = 0; i< getHunger() / 10; i++) {// 每十点生命值为一颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[4], Color.white), mainFrame.panelWidth / 2 + 20 + (getMaxHunger() / 10-1-i) * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}
		for (int i = 0; i < getHunger() / 5; i++) {// 每5生命值为半颗心
			g.drawImage(tools.makeColorTransparent(bufferImage[5], Color.white), mainFrame.panelWidth / 2 + 20 + (getMaxHunger() / 10-1-(i / 2)) * 22,
					mainFrame.panelHeight - 130, 20, 20, null);
		}
	}
}
