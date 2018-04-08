package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

import GameGraphics.mainFrame;
import GameResource.gameConfig;
import GameTool.tools;

public class NPC implements gameConfig {
	/* 在数组中位置 */
	public int I = 0;
	public int J = 0;
	/* 在屏幕上坐标 */
	public int x = 0;
	public int y = 0;
	Image im;
	int step = mainFrame.blockWidth * 10 / 3 * 2;
	int speed = 20;

	// 取值为0~blockWidth*100
	public int mXX = 0;// 右加左减
	public int mYY = 0;// 右加左减
	// 取值为0~blockWidth
	public int mX = 0;// 右加左减
	public int mY = 0;// 下加上减

	// NPC移动状态
	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;

	// NPC目标
	public int AX = -1;
	public int AY = -1;
	public boolean complete = false;
	public boolean walkAround = false;
	public int atk = 1;// 攻击力
	public String name = "NPC";// 名字
	public int hp = 20;// 生命值
	public boolean canIntersect = false;// 能与玩家发生碰撞
	public boolean canInteract = true;// 能与玩家交互 不可放置物品
	// 是否受重力
	public boolean canDown = true;
	// 是否正在跳跃
	public boolean isJump = false;
	public boolean folowPlayer = false;
	public boolean cannotDoThing = false;

	// 方向
	public int towards = 0;// 1为上，2为下，3为左，4为右
	// 图片序号
	private int right1 = 20;
	private int left1 = 20;

	/* 获取NPC名字 */
	public String getName() {
		return name;
	}

	/* 击退 参数为伤害来源坐标 */
	public void knockBack(final Rectangle r) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isJump == false) {
					new Thread(new Runnable() {
						public void run() {
							cannotDoThing = true;
							left = right = false;
							if (getRect().getX() + getRect().getWidth() / 2 < r.getX() + r.getWidth() / 2) {
								left = true;
							} else {
								right = true;
							}
							isJump = true;
							up = true;
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							up = false;
							down = true;
							left = false;
							right = false;
							cannotDoThing = false;
						}
					}).start();
				}
			}
		}).start();

	}

	/* 跟随玩家 */
	public void folowPlayer() {
		if (folowPlayer == true) {
			folowPlayer = false;
			return;
		} else {
			folowPlayer = true;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (complete == false && folowPlayer == true) {
					if (cannotDoThing == false) {
						if (I > AX - 1) {
							left = true;
							right = false;
							if (tools.intersectJudge(getNextRect(3)) == true) {// 如果向左有障碍
								jump();
							}
						}
						if (I < AX + 1) {
							right = true;
							left = false;
							if (tools.intersectJudge(getNextRect(4)) == true) {// 如果向右有障碍
								jump();
							}
						}
						if (I == AX) {
							right = false;
							left = false;
						}

					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				right = left = false;
			}
		}).start();
	}

	public void setWalkAround(boolean b) {
		if (b == true && b != walkAround) {
			walkAround = b;
			walkAround();
		} else if (b == false) {
			walkAround = b;
		}
	}

	/* 游荡 */
	public void walkAround() {
		int r = new Random().nextInt() % 5 * (new Random().nextBoolean() ? 1 : -1);
		goToXY(I + r, J);
		if (walkAround == true) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if (complete == false) {
							complete = true;
						}
						if (complete == true) {
							complete = false;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							walkAround();
							break;
						}

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	/* 去到某一地点 */
	public void goToXY(int a, int b) {
		this.AX = a;
		this.AY = b;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (complete == false) {
					if (cannotDoThing == false) {
						if (I > AX) {
							right = false;
							left = true;
							if (tools.intersectJudge(getNextRect(3)) == true) {// 如果向左有障碍
								jump();
							}
						}
						if (I < AX) {
							right = true;
							left = false;
							if (tools.intersectJudge(getNextRect(4)) == true) {// 如果向右有障碍
								jump();
							}
						}
						if (I == AX) {
							complete = true;
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				right = left = false;
			}
		}).start();
	}

	/* 跳跃 */
	public void jump() {
		if (isJump == false) {
			new Thread(new Runnable() {
				public void run() {
					isJump = true;
					up = true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					up = false;
					down = true;
				}
			}).start();

		}
	}

	/* 移动 */
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
			}
		}

		if (left) {
			towards = 3;
			left1++;
			if (left1 >= 20) {
				left1 = 0;
			}
			if (tools.intersectJudge(getNextRect(3)) == false) {
				if (mXX - step > 0) {
					mXX -= step;
				} else if (mXX - step <= 0) {
					I -= 1;
					mXX = mXX - step + mainFrame.blockWidth * 100;
				}
				mX = mXX / 100;
			} else {
				left=false;
			}
		}

		if (right) {
			towards = 4;
			right1++;
			if (right1 >= 20) {
				right1 = 0;
			}
			if (tools.intersectJudge(getNextRect(4)) == false) {
				if (mXX + step < mainFrame.blockWidth * 100) {
					mXX += step;
				} else if (mXX + step >= mainFrame.blockWidth * 100) {
					I += 1;
					mXX = mXX + step - mainFrame.blockWidth * 100;
				}
				mX = mXX / 100;
			}
			else{
				right=false;
			}
		}

	}

	/* 获取下一碰撞位置 */
	public Rectangle getNextRect(int t) {

		switch (t) {
		case 1:// 向上
			return new Rectangle(x, y - step / 100, mainFrame.playerWidth - mainFrame.playerWidth / 15,
					mainFrame.playerHeight);
		case 2:// 向下
			return new Rectangle(x, y + step / 100, mainFrame.playerWidth - mainFrame.playerWidth / 15,
					mainFrame.playerHeight);
		case 3:// 向左
			return new Rectangle(x - 2 * (step) / 100, y, mainFrame.playerWidth - mainFrame.playerWidth / 15,
					mainFrame.playerHeight);
		case 4:// 向右
			return new Rectangle(x + 2 * (step) / 100, y, mainFrame.playerWidth - mainFrame.playerWidth / 15,
					mainFrame.playerHeight);
		default:
			return null;
		}

	}

	public NPC(int i, int j) {// 初始化NPC
		im = new ImageIcon("./images/blocks/" + 100 + ".png").getImage();
		this.I = i;
		this.J = j;
	}

	public void setI(int a) {// 设置i
		this.I = a;
	}

	public void setJ(int a) {// 设置j
		this.J = a;
	}

	public Image getImg() {// 获取物品图片
		return im;
	}

	public Rectangle getRect() {// 获取矩形碰撞区域
		return new Rectangle(x, y, mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
	}

	public void draw(Graphics g) {
		x = mainFrame.p.aX + (I - mainFrame.p.I) * mainFrame.blockWidth - mainFrame.p.mX + mX;
		y = mainFrame.p.aY + (J - mainFrame.p.J) * mainFrame.blockHeight - mainFrame.p.mY - mainFrame.playerHeight / 2
				+ mY;
		// g.drawImage(im, x, y, mainFrame.playerWidth - 2,
		// mainFrame.playerHeight, null);

		if (towards == 3) {// 向左
			switch (left1 / 5) {
			case 0:
				g.drawImage(icP[5], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 1:
				g.drawImage(icP[6], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 2:
				g.drawImage(icP[7], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 3:
				g.drawImage(icP[8], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			}

		} else if (towards == 4) {// 向右
			switch (right1 / 5) {
			case 0:
				g.drawImage(icP[1], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 1:
				g.drawImage(icP[2], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 2:
				g.drawImage(icP[3], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			case 3:
				g.drawImage(icP[4], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight,
						null);
				break;
			}
		} else if (towards == 0) {
			g.drawImage(icP[0], x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15, mainFrame.playerHeight, null);
		}

		g.setColor(Color.black);
		int width = tools.length(name) * 3;
		g.drawString(name, x - width / 2 + 3, y - 5);
		g.setColor(Color.red);
		// g.drawRect(x, y, mainFrame.playerWidth - mainFrame.playerWidth / 15,
		// mainFrame.playerHeight);
	}
}
