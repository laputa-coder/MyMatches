package GameGraphics;

import javax.swing.JFrame;

import GameTool.tools;

/*刷新主界面的线程*/
public class updateThread implements Runnable {
	JFrame j;
	public static int stdTime=0;
	updateThread(JFrame j) {
		this.j = j;
	}

	public void run() {
		while (true) {
			tools.getTimeBetween();
			j.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stdTime++;
			if (stdTime == 10000) {//  100秒重置
				stdTime = 1;
			}
		}
	}
}
