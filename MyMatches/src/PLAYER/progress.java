package PLAYER;

import java.awt.Graphics;

import GameGraphics.mainFrame;
import GameResource.gameConfig;

public class progress implements gameConfig {// ½ø¶ÈÌõ

	public int progress_max;
	public int progress_now;

	public void setMax(int max) {
		progress_max = max;
	}

	public void setNow(int now) {
		progress_now = now;
	}

	public void draw(Graphics g) {
		g.drawImage(pic_Progress, mainFrame.panelWidth / 2 - 230, mainFrame.panelHeight - 170,
				mainFrame.panelWidth / 2 + 230, mainFrame.panelHeight - 155, 0, 0, 182, 5, null);
		g.drawImage(pic_Progress, mainFrame.panelWidth / 2 - 230, mainFrame.panelHeight - 170,
				(int) ((float) progress_now / progress_max * (460)) + mainFrame.panelWidth / 2 - 230,
				mainFrame.panelHeight - 155, 0, 5, (int) ((float) progress_now / progress_max * 182), 10, null);
	}
}
