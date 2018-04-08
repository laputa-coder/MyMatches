package GameMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameGraphics.mainFrame;
import GameResource.gameConfig;
import GameTool.tools;

public class optionMenu implements gameConfig {
	int y;
	String button[] = { "继续游戏", "退出并保存游戏" };
	Rectangle r_b[] = new Rectangle[2];
	Image i_b[] = new Image[2];
	public int mouseX = 0;// 鼠标X
	public int mouseY = 0;// 鼠标Y
	BufferedImage bufferImage[] = new BufferedImage[3];
	Graphics gBuffer;

	public optionMenu() {
		
		for (int i = 0; i < 3; i++) {
			bufferImage[i] = new BufferedImage(200, 20, BufferedImage.TYPE_INT_ARGB);
			gBuffer = bufferImage[i].getGraphics();
			gBuffer.drawImage(pic_Button, 0, 0, 200, 20, 0, 20 * i, 200, 20 * (i + 1), null);
			gBuffer = null;
		}
		for (int i = 0; i < i_b.length; i++) {
			i_b[i] = bufferImage[1];
		}
	}

	public void doThingOnMouseMove(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		int num = -1;
		for (int i = 0; i < r_b.length; i++) {
			if (r_b[i] != null && r_b[i].contains(mouseX, mouseY)) {
				num = i;
				break;
			}
		}
		if (num != -1) {
			i_b[num] = bufferImage[2];
		} else {
			for (int i = 0; i < i_b.length; i++) {
				i_b[i] = bufferImage[1];
			}
		}
	}

	public void doThingOnMousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		int num = -1;
		if (e.getButton() == 1) {
			for (int i = 0; i < r_b.length; i++) {
				if (r_b[i].contains(mouseX, mouseY)) {
					num = i;
					break;
				}
			}
			if (num != -1) {
				if (num == 0) {
					mainFrame.firstInit = true;
					mainFrame.d.setDrawHP(true);
					mainFrame.d.setDrawEquipColumn(true);
					mainFrame.d.setDrawOptionMenu(false);
					mainFrame.pause = false;
				} else if (num == 1) {
					mainFrame.pause = true;
					tools.writeDropEnity();
					tools.writeMap();
					tools.writeBackPack();
					tools.writePlayerData();
					System.exit(0);
					mainFrame.pause = false;
				}
			}
		}
	}

	public void draw(Graphics g) {

		int width = mainFrame.panelWidth / 3;
		int height = mainFrame.panelHeight / 10;
		g.setColor(Color.black);
		g.drawImage(pic_shadow, 0, 0, mainFrame.panelWidth, mainFrame.panelHeight,null);
		for (int i = 0; i < button.length; i++) {
			g.drawImage(i_b[i], mainFrame.panelWidth / 2 - width / 2,
					mainFrame.panelHeight / 2 - height * 3 + i * (height + 10), width, height, null);
			r_b[i] = new Rectangle(mainFrame.panelWidth / 2 - width / 2,
					mainFrame.panelHeight / 2 - height * 3 + i * (height + 10), width, height);
			g.drawString(button[i], mainFrame.panelWidth / 2 - tools.length(button[i]) * 3 / 2,
					mainFrame.panelHeight / 2 - height * 3 + i * (height + 10) + height / 2);
		}
	}
}
