package GameGraphics;

import java.awt.Graphics;

import GameResource.gameConfig;

public class backGround implements gameConfig {
	int i = 0;
	int j = 0;
	boolean day = false;
	/*
	 * public backGround() { new Thread(new Runnable() {
	 * 
	 * @Override public void run() { while (true) { for (i = 0; i < 2; i++) {
	 * for (j = 0; j < 4; j++) { try { Thread.sleep(500); } catch
	 * (InterruptedException e) { e.printStackTrace(); } } } day = true; try {
	 * Thread.sleep(2000); } catch (InterruptedException e) {
	 * e.printStackTrace(); } day = false; } } }).start(); }
	 */

	public void draw(Graphics g) {

		/*
		 * if(day==false){ g.setColor(Color.black); g.fillRect(0, 0, panelWidth,
		 * panelHeight); g.drawImage(moon, panelWidth-400, -100, panelWidth+100,
		 * 400, j * 32, i * 32, (j + 1) * 32, (i + 1) * 32, null); }else{
		 * g.setColor(Color.black); g.fillRect(0, 0, panelWidth, panelHeight);
		 * g.drawImage(sun, panelWidth-400, -100, panelWidth+100, 400, null); }
		 */
	}
}
