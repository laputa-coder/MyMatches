package GameLogic;

import GameGraphics.mainFrame;
import GameResource.gameConfig;
import GameTool.tools;

public class shoot extends NPC implements gameConfig {

	public shoot(int i, int j, int x, int y) {
		super(i, j);
		canDown=false;
		canInteract=false;
		name="shoot";
		goToXY(x, y);
	}

	public void goToXY(int a, int b) {
		this.AX = a;
		this.AY = b;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (complete == false) {
					if (I > AX) {
						right = false;
						left = true;
					}
					if (I < AX) {
						right = true;
						left = false;
					}
					if (J > AY) {
						up = true;
						down = false;
					}
					if (J < AY) {
						up = false;
						down = true;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				right = left = up = down = false;
			}
		}).start();
	}

	public void move() {
		step = (int) (speed * tools.timeResult);// timeResult：两帧之间的时间间隔
		if (up) {
			if (tools.intersectJudge(getNextRect(1)) == false) {
				if (mYY - step >= 0) {
					mYY -= step;
				} else if (mYY - step < 0) {
					J -= 1;
					mYY = mYY - step + mainFrame.blockWidth * 100;
				}
				mY = mYY / 100;
			} else {
				up = false;
				complete = true;
			}
		}

		if (down) {
			if (tools.intersectJudge(getNextRect(2)) == false) {
				isJump = true;
				if (mYY + step < mainFrame.blockWidth * 100) {
					mYY += step;
				} else if (mYY + step >= mainFrame.blockWidth * 100) {
					J += 1;
					mYY = mYY + step - mainFrame.blockWidth * 100;
				}
				mY = mYY / 100;
			} else {
				down = false;
				isJump = false;
				complete = true;
			}
		}

		if (left) {
			if (tools.intersectJudge(getNextRect(3)) == false) {
				if (mXX - step > 0) {
					mXX -= step;
				} else if (mXX - step <= 0) {
					I -= 1;
					mXX = mXX - step + mainFrame.blockWidth * 100;
				}
				mX = mXX / 100;
			} else {
				complete = true;
			}
		}

		if (right) {
			if (tools.intersectJudge(getNextRect(4)) == false) {
				if (mXX + step < mainFrame.blockWidth * 100) {
					mXX += step;
				} else if (mXX + step >= mainFrame.blockWidth * 100) {
					I += 1;
					mXX = mXX + step - mainFrame.blockWidth * 100;
				}
				mX = mXX / 100;
			} else {
				complete = true;
			}
		}

	}
}
