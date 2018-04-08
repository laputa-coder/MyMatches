package PLAYER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameGraphics.mainFrame;
import GameLogic.Enity;
import GameLogic.NPC;
import GameResource.gameConfig;
import GameTool.tools;

public class player extends NPC implements gameConfig, Runnable {
	public player(int i, int j) {
		super(i, j);
		I = i;
		J = j;
		// TODO Auto-generated constructor stub
	}

	// 玩家相对窗口位置
	public int aX = mainFrame.panelWidth / 2;
	public int aY = mainFrame.panelHeight / 2;

	// 玩家相对数组位置的像素偏移
	// 初始状态为方块边左上角点

	// 取值为0~blockWidth*100
	public int mXX = 0;// 右加左减
	public int mYY = 0;// 右加左减
	// 取值为0~blockWidth
	public int mX = 0;// 右加左减
	public int mY = 0;// 下加上减

	// 步长
	// 取值为0~blockWidth*100
	public int step = mainFrame.blockWidth * 10 / 3 * 2;
	int speed = 20;// 20像素每10毫秒
	public int player_MaxHP = 100;// 最大生命值
	public int player_MaxHunger = 100;// 最大饥饿度

	public int player_HP = 100;// 当前生命值
	public int player_Hunger = 100;// 当前饥饿度

	// 玩家移动状态
	public boolean up = false;
	public boolean down = true;
	public boolean left = false;
	public boolean right = false;

	// 方向
	public int towards = 0;// 1为上，2为下，3为左，4为右

	// 是否正在跳跃
	public boolean isJump = false;

	// 图片序号
	private int right1 = 20;
	private int left1 = 20;

	public void run() {
		int a = 1;
		while (true) {
			// 玩家线程
			if (mainFrame.pause == false) {
				move();
				if (tools.intersectJudge(getNextRect(2)) == false && isJump == false) {
					down = true;
				}
				if(player_HP<=0){//玩家死亡
					player_HP=player_MaxHP;
					player_Hunger=player_MaxHunger;
					//掉落背包物品
					for (int ii = 0; ii < mainFrame.bp.bp_enity.size(); ii++) {
						for (int jj = 0; jj <  mainFrame.bp.stackAmount.get(ii); jj++) {
							Enity e = new Enity( mainFrame.bp.bp_enity.get(ii).getID(), I, J);
							e.setBreak( mainFrame.bp.bp_enity.get(ii).getBreak());
							 mainFrame.drop_Enity.add(e);
						}
						mainFrame.bp.stackAmount.set(ii, 0);
					}
					I=476;
					J=117;
				}
				if(player_Hunger<=0){//饥饿值小于等于0
					if (a * 10 % 2000 == 0) {// 每2秒 减少五点生命
						player_HP-=5;
					}
				}
				if (a * 10 % 30000 == 0) {// 每30秒 减少五点饥饿值
					if(player_Hunger>0){
						player_Hunger -= 5;
						if(player_Hunger<0){
							player_Hunger=0;
						}
					}
				}
				
				if (a * 10 % 30000 == 0) {// 每30秒 加五点生命
					if(player_Hunger>0&&player_HP<player_MaxHP){
						player_HP+=5;
						if(player_HP>player_MaxHP){
							player_HP=player_MaxHP;
						}
					}
				}

				a++;
				if (a == 10000) {// 重置
					a = 1;
				}
				if(right==false&&left==false)
					towards=0;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* 击退 参数为伤害来源坐标 */
	public void knockBack(final Rectangle r) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				mainFrame.d.setCannotDothing(true);
				if (isJump == false) {
					new Thread(new Runnable() {
						public void run() {
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
						}
					}).start();
				}
				if (mainFrame.d.getDrawBackPack() != true && mainFrame.ViewMod != true) {
					mainFrame.d.setCannotDothing(false);
				}
			}
		}).start();

	}

	public void jump(final int time) {

		if (isJump == false) {
			new Thread(new Runnable() {
				public void run() {
					isJump = true;
					up = true;
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					up = false;
					down = true;
				}
			}).start();

		}
	}

	public void move() {
		step = (int) (speed * tools.timeResult);// timeResult：两帧之间的时间间隔

		if (up) {
			if (!tools.intersectJudge(getNextRect(1))) {
				if (mYY - step >= 0) {
					mYY -= step;
				} else if (mY - step < 0) {
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
				// System.out.println(gameMap.map0[X][Y + 1]);
				if (mXX - step > 0) {
					mXX -= step;
				} else if (mXX - step <= 0) {
					I -= 1;
					mXX = mXX - step + mainFrame.blockWidth * 100;
				}
				mX = mXX / 100;
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

		}

	}

	public Rectangle getRect() {
		return new Rectangle(x, y,
				mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
	}

	public Rectangle getNextRect(int t) {
		switch (t) {
		case 1:// 向上
			return new Rectangle(x, y- (step+ 99) / 100,
					mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
		case 2:// 向下
			return new Rectangle(x, y + (step+ 99) / 100,
					mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
		case 3:// 向左
			return new Rectangle(x - (step + 99) / 100, y,
					mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
		case 4:// 向右
			return new Rectangle(x + (step + 99) / 100, y,
					mainFrame.playerWidth - mainFrame.blockWidth / 15, mainFrame.playerHeight);
		default:
			return null;
		}

	}

	public void draw(Graphics gBuffer) {
		// gBuffer.drawImage(icP[0], player.aX, player.aY - playerWidth,
		// playerWidth, playerHeight, null);
		x = this.aX;
		y = this.aY - mainFrame.playerWidth+ mainFrame.blockWidth / 15;
		if (towards == 3) {// 向左
			/* 绘制装备 */
			if (mainFrame.bp.bp_enity.size() >= mainFrame.select
					&& mainFrame.bp.stackAmount.get(mainFrame.select - 1) > 0) {
				gBuffer.drawImage(mainFrame.bp.bp_enity.get(mainFrame.select - 1).getImg(), this.aX + 5, this.aY,
						mainFrame.blockWidth / 2, mainFrame.blockHeight / 2, null);
			}
			switch (left1 / 5) {
			case 0:
				gBuffer.drawImage(icP[5], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 1:
				gBuffer.drawImage(icP[6], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 2:
				gBuffer.drawImage(icP[7], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 3:
				gBuffer.drawImage(icP[8], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			}

		} else if (towards == 4) {// 向右
			switch (right1 / 5) {
			case 0:
				gBuffer.drawImage(icP[1], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 1:
				gBuffer.drawImage(icP[2], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 2:
				gBuffer.drawImage(icP[3], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			case 3:
				gBuffer.drawImage(icP[4], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
				break;
			}
			/* 绘制装备 */
			if (mainFrame.bp.bp_enity.size() >= mainFrame.select
					&& mainFrame.bp.stackAmount.get(mainFrame.select - 1) > 0) {
				gBuffer.drawImage(mainFrame.bp.bp_enity.get(mainFrame.select - 1).getImg(),
						this.aX + mainFrame.blockWidth - 7, this.aY, mainFrame.blockWidth / 2,
						mainFrame.blockHeight / 2, null);
			}
		} else if (towards == 0) {
			gBuffer.drawImage(icP[0], x, y, mainFrame.playerWidth- mainFrame.blockWidth / 15, mainFrame.playerHeight, null);
			/* 绘制装备 */
			if (mainFrame.bp.bp_enity.size() >= mainFrame.select
					&& mainFrame.bp.stackAmount.get(mainFrame.select - 1) > 0) {
				gBuffer.drawImage(mainFrame.bp.bp_enity.get(mainFrame.select - 1).getImg(), this.aX - 7, this.aY,
						mainFrame.blockWidth / 2, mainFrame.blockHeight / 2, null);
			}
		}

		gBuffer.setColor(Color.red);
		// gBuffer.drawRect(this.aX, this.aY - mainFrame.playerWidth,
		// mainFrame.playerWidth - 2, mainFrame.playerHeight);
	}

}
