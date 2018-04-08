package GameGraphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import GameLogic.Enity;
import GameLogic.NPC;
import GameLogic.block;
import GameLogic.chatBubbles;
import GameLogic.closeDoor;
import GameMenu.mainMenu;
import GameMenu.optionMenu;
import GameMission.mission;
import GameResource.*;
import GameTool.tools;
import PLAYER.HP;
import PLAYER.backpack;
import PLAYER.chestBar;
import PLAYER.player;
import PLAYER.progress;
import PLAYER.recipeBar;

/*主界面*/
public class mainFrame extends JFrame
		implements gameConfig, KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

	// 窗口大小
	public static int panelWidth = 1000;
	public static int panelHeight = 600;

	// 游戏方块大小
	public static int blockWidth = 30;
	public static int blockHeight = blockWidth;
	public static boolean firstInit = false;
	// NPC大小
	public static int playerWidth = blockWidth;
	public static int playerHeight = playerWidth * 2 - 5;

	public static int mouseX;
	public static int mouseY;
	static int view = panelWidth / 15 / 2 + 2;

	public static player p;

	static NPC npc;
	Image iBuffer = null;
	Graphics gBuffer = null;
	public static display d;
	backGround bg;

	Image s = null;
	// Light light;
	// 是否正在破坏方块
	public static boolean pause = true;
	// 是否正在破坏方块
	boolean destroy = false;
	// 是否正在吃
	boolean eating = false;
	// 图片标号
	int destroyAmount = 0;

	// 正在破坏的方块
	int bI;
	int bJ;
	// 方块生命值
	int block_ID = 1;
	int block_HP = 0;
	// 食物生命值
	int eatingPiece = 0;
	// 是否处于Debug模式
	public static boolean Debug = false;
	// 是否处于视角变换模式
	public static boolean ViewMod = false;

	public static ArrayList<block> bk = new ArrayList<block>();// 当前屏幕内的block
	public static ArrayList<Enity> drop_Enity = new ArrayList<Enity>();// dropEnity
	public static ArrayList<NPC> npcs = new ArrayList<NPC>();// npc
	public static ArrayList<chatBubbles> cb = new ArrayList<chatBubbles>();// chatBubbles
	public static mission ms = new mission();
	public static backpack bp;// 背包
	public static chestBar chest;// 箱子
	public static recipeBar rb;// 合成表
	public static HP hp;// 血条饥饿条
	public static mainMenu mm = new mainMenu();// 主菜单
	public static optionMenu om = new optionMenu();// 设置菜单
	public static progress Progress;// 进度条

	public static int lastPlayerLoc[] = { 0, 0 };// 玩家的上一坐标
	public static int locDif[] = { 0, 0 };// 玩家当前坐标与上一坐标差值
	public static int select = 1;// 装备栏选择项 1-10
	// 初始化方块
	public static boolean initing = true;

	public mainFrame() {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseWheelListener(this);
		d = new display();
		init();
	}

	// 新掉落物
	public static void newDropEnity(int id, int i, int j, int amount) {
		for (int ii = 0; ii < amount; ii++) {
			drop_Enity.add(new Enity(id, i, j));
		}
	}

	/* 新聊天泡泡 */
	public static void newChatBubble(NPC n, long delay, String line) {
		cb.add(new chatBubbles(n, delay, line));
	}

	public void init() {
		updateThread ut = new updateThread(this);
		new Thread(ut).start();
		this.setTitle(title);
		this.setSize(panelWidth, panelHeight);
		this.setBounds(100, 0, panelWidth, panelHeight);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		c.setLayout(null);
		setResizable(false);
		setVisible(true);
	}

	public static void startGame() {
		bp = new backpack(p, mainFrame.panelHeight / 2 - 80, 45, 5, 10);// 背包

		tools.readMap();
		tools.readDropEnity();
		tools.readBackPack();

		// 启动人物移动线程
		for (int i = 0; i < 1; i++) {
			npc = new NPC(482, 112);
			npc.name = "hahaha";
			// npc.setWalkAround(true);
			npc.canDown = false;
			npcs.add(npc);
		}
		npc = new NPC(483, 112);
		npc.name = "NPC";
		npc.canDown = false;
		npcs.add(npc);

		p = new player(476, 117);
		// light=new Light();
		// light.X=panelWidth-100;
		// light.Y=100;
		new Thread(p).start();

		chest = new chestBar(p, mainFrame.panelHeight / 2 - 250, 45, 3, 10);// 箱子
		rb = new recipeBar(p, mainFrame.panelHeight / 2 - 250, 45, 10);// 合成表
		rb.setSize(2);

		hp = new HP(p);// 血条
		Progress = new progress();// 进度条

		tools.readPlayerData();
		// bg=new backGround();
		// 启动刷新界面线程

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					for (int i = 0; i < drop_Enity.size(); i++) {
						if (drop_Enity.get(i).getRect().intersects(p.getRect())) {// 捡到掉落物
							if (bp.addItem(drop_Enity.get(i)) == true) {// 成功捡到掉落物
								drop_Enity.remove(i);
							}
						}
					}
					int view1 = panelWidth / 30 / 2 + 2;
					for (int i = 0; i < npcs.size(); i++) {
						if (npcs.get(i).canIntersect == true && npcs.get(i).getRect().intersects(p.getRect())) {// 被攻击
							p.player_HP -= npcs.get(i).atk;
							p.knockBack(npcs.get(i).getRect());
						}
						if ((npcs.get(i).I < p.I - view1 || npcs.get(i).I > p.I + view1 || npcs.get(i).J < p.J - view1
								|| npcs.get(i).J > p.J + view1) && npcs.get(i).canDown == true) {
							npcs.remove(i);
							i--;
						}
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					for (int i = 0; i < npcs.size(); i++) {
						if (pause == false) {
							NPC n = npcs.get(i);
							if (n.folowPlayer == true) {
								n.AX = mainFrame.p.I;
								n.AY = mainFrame.p.J;
							}
							if (i == n.AX && n.J == n.AY && n.folowPlayer == false) {
								n.complete = true;
							}
							n.move();
							if (tools.intersectJudge(n.getNextRect(2)) == false && n.isJump == false
									&& n.canDown == true) {
								n.down = true;
							}
							if (n.canIntersect == true) {
								if (Math.sqrt((p.I - n.I) * (p.I - n.I) + (p.J - n.J) * (p.J - n.J)) < 6) {// NPC发现玩家
									n.setWalkAround(false);
									n.complete = false;
									if (n.folowPlayer == false) {
										System.out.println("被" + n.getName() + "发现");
										n.folowPlayer();
									}
								} else {
									if (n.folowPlayer == true) {
										System.out.println(n.getName() + "失去敌意");
										n.folowPlayer();
									}
									n.setWalkAround(true);
								}
							}
							if (n.left == false && n.right == false) {
								n.towards = 0;
							}
						}
					}
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		// initBlock();
		// newDropEnity(102, 479, 117, 1);
		// newDropEnity(101, 480, 117, 1);
		// newDropEnity(100, 481, 117, 1);
		// newDropEnity(200, 482, 117, 1);
		// newDropEnity(200, 482, 117, 1);
		// newDropEnity(200, 482, 117, 1);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public static void initBlock(Graphics g) {
		initing = true;
		Image ima = null;
		bk = new ArrayList<block>();
		for (int i = p.I - view; i <= p.I + view; i++) {
			for (int j = p.J - view; j <= p.J + view; j++) {
				if (i >= 0 && j >= 0 && i < gameMap.map0[0].length && j < gameMap.map0.length) {
					ima = gameMap.getMapIcon(gameMap.map0[j][i]);
					if (gameMap.map0[j][i] == 21 || gameMap.map0[j][i] == 22) {// closeDoor
						bk.add(new closeDoor(i, j, ima, gameMap.map0[j][i]));
					} else {
						bk.add(new block(i, j, ima, gameMap.map0[j][i]));
					}
					if (bk.get(bk.size() - 1).getImg() != null) {
						if (bk.get(bk.size() - 1).getID() == 14) {// 刷怪笼
							tools.produceMob(bk.get(bk.size() - 1));
						} else if (bk.get(bk.size() - 1).getID() >= 15 && bk.get(bk.size() - 1).getID() <= 17) {// 萝卜苗
							tools.growSeed(bk.get(bk.size() - 1));
						} 
						else if (bk.get(bk.size() - 1).getID() ==24) {// 树苗
							tools.growTree(bk.get(bk.size() - 1));
						}
						bk.get(bk.size() - 1).draw(g);
					}

				}
			}
		}
		firstInit = true;
		initing = false;

	}

	// public void refreshBlock(Graphics g){
	// Image ima = null;
	// for(int i=0;i<bk.size();i++){
	// if (bk.get(i).getImg() != null) {
	// if (bk.get(i).getID() == 14) {// 刷怪笼
	// tools.produceMob(bk.get(i));
	// } else if (bk.get(i).getID() >= 15 && bk.get(i).getID() <= 17) {// 萝卜苗
	// tools.grow(bk.get(i));
	// }
	// bk.get(i).draw(g);
	// }
	// }
	// if(p.I!=lastPlayerLoc[0]||p.J!=lastPlayerLoc[1]){
	// locDif[0]=p.I-lastPlayerLoc[0];
	// locDif[1]=p.J-lastPlayerLoc[1];
	// lastPlayerLoc[0]=p.I;
	// lastPlayerLoc[1]=p.J;
	// //p.I/J - view方块最小I、J
	// //p.I/J + view方块最大I、J
	// if(locDif[0]>0){
	// for(int i=0;i<bk.size();i++){
	// if(bk.get(i).getI()==p.I - view){
	// bk.remove(bk.get(i));
	// }
	// }
	// }
	// if(locDif[0]<0){
	// for(int i=0;i<bk.size();i++){
	// if(bk.get(i).getI()==p.I + view){
	// bk.remove(bk.get(i));
	// }
	// }
	//
	//// ima=gameMap.getMapIcon(gameMap.map0[j][i]);
	//// if (gameMap.map0[j][i] == 21 || gameMap.map0[j][i] == 22) {// closeDoor
	//// bk.add(new closeDoor(i, j, ima, gameMap.map0[j][i]));
	//// } else {
	//// bk.add(new block(i, j, ima, gameMap.map0[j][i]));
	//// }
	// }
	// if(locDif[1]>0){
	// for(int i=0;i<bk.size();i++){
	// if(bk.get(i).getJ()==p.J - view){
	// bk.remove(bk.get(i));
	// }
	// }
	// }
	// if(locDif[1]<0){
	// for(int i=0;i<bk.size();i++){
	// if(bk.get(i).getJ()==p.J + view){
	// bk.remove(bk.get(i));
	// }
	// }
	// }
	// }
	// }
	public void drawDropEnity(Graphics g) {
		for (int i = 0; i < drop_Enity.size(); i++) {

			drop_Enity.get(i).drawDrop(g);

		}
	}

	public void drawNPCs(Graphics g) {
		for (int i = 0; i < npcs.size(); i++) {
			npcs.get(i).draw(g);
		}
	}

	public void drawChatBubbles(Graphics g) {
		int amount = cb.size();
		for (int i = 0; i < amount; i++) {
			cb.get(i).draw(g);
		}
		for (int i = 0; i < amount; i++) {
			if (cb.get(i).drawBubble == false) {
				cb.remove(i);
				amount--;
				i--;
			}
		}
	}

	public void paint(Graphics g) {

		if (iBuffer == null) {
			iBuffer = createImage(this.getWidth(), this.getHeight());
			gBuffer = iBuffer.getGraphics();
		}

		gBuffer.setColor(Color.white);
		gBuffer.fillRect(0, 0, panelWidth, panelHeight);
		// gBuffer.drawImage(dayNight, -100, -100,
		// panelWidth+200,panelHeight*2+200,null);

		// bg.draw(gBuffer);

		if (firstInit == true) {
			gBuffer.setFont(new Font("微软雅黑", Font.PLAIN, 13));

			initBlock(gBuffer);// 画方块
			if (destroy == true) {
				block b = tools.getPosBlock(bI, bJ);
				int x = b.getX();
				int y = b.getY();
				gBuffer.drawImage(pic_destroyImg[destroyAmount], x, y, blockWidth, blockHeight, null);
			}

			gBuffer.setColor(Color.red);
			block b = tools.getPixBlock(mouseX, mouseY);
			if (Math.sqrt((p.I - b.getI()) * (p.I - b.getI()) + (p.J - b.getJ()) * (p.J - b.getJ())) <= 5) {
				gBuffer.drawRect(b.getX(), b.getY(), blockWidth, blockHeight);
			}

			drawDropEnity(gBuffer);// 画掉落物品
			drawNPCs(gBuffer);

			// light.draw(gBuffer);
			p.draw(gBuffer);// 画人物
			drawChatBubbles(gBuffer);

		}
		d.draw(gBuffer);
		if (firstInit == true && Debug) {
			gBuffer.setColor(Color.red);
			gBuffer.drawString("block:" + bk.size(), 20, 50);
			gBuffer.drawString("isJumping:" + p.isJump, 20, 70);
			gBuffer.drawString("I:" + p.I + " J:" + p.J + " mx:" + p.mX + " my:" + p.mY, 20, 90);
			gBuffer.drawString("block_NAME:" + block.getName(block_ID), 20, 110);
			gBuffer.drawString("blockHP:" + block_HP, 20, 130);
			gBuffer.drawString("eatingPiece:" + eatingPiece, 20, 150);
			gBuffer.drawString("Select:" + select, 20, 170);
			gBuffer.drawString("PlayerHP:" + p.player_HP + "/" + p.player_MaxHP, 20, 190);
			gBuffer.drawString("ViewMod:" + ViewMod, 20, 210);
			gBuffer.drawString("CannotDothing:" + d.getCannotDothing(), 20, 230);
			gBuffer.drawString("MOUSE X:" + mouseX + " MOUSE Y:" + mouseY, 20, 250);
			int world[] = tools.getPixWorldXY(mouseX, mouseY);
			gBuffer.drawString("WORLD I:" + world[0] + " J:" + world[1] + " mX:" + world[2] + " mY:" + world[3], 20,
					270);
			gBuffer.drawString("TimeBetweenFrame:" + tools.timeResult + " ms", 20, 290);
			gBuffer.drawString("FPS:" + (int) tools.getFPS(), 20, 310);
		}
		if (firstInit) {
			chest.doThingOnMouseMoved(gBuffer);
			rb.doThingOnMouseMoved(gBuffer);
			bp.doThingOnMouseMoved(gBuffer);
		}
		g.drawImage(iBuffer, 0, 0, this);

	}

	/* 键盘鼠标监控 */
	public void keyPressed(KeyEvent e) {
		// System.out.println("1");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			if (d.getCannotDothing() == false && !pause)
				p.jump(300);
			break;
		case KeyEvent.VK_A:
			if (d.getCannotDothing() == false && !pause)
				p.left = true;
			break;
		case KeyEvent.VK_D:
			if (d.getCannotDothing() == false && !pause)
				p.right = true;
			break;
		case KeyEvent.VK_E:// 打开背包
			if (!pause)
				d.setDrawBackPack(!d.getDrawBackPack());
			break;
		case KeyEvent.VK_P:// 暂停
			tools.setPauseGame(!pause);
			break;
		case KeyEvent.VK_B:// Debug
			Debug = !Debug;
			if (ViewMod == true) {
				ViewMod = false;
				if (d.getDrawBackPack() == false) {
					d.setCannotDothing(false);
				}
				blockWidth = 30;
				blockHeight = blockWidth;
				playerWidth = blockWidth;
				playerHeight = playerWidth * 2;
			}
			break;
		case KeyEvent.VK_V:// 视角模式
			if (!pause && Debug) {
				if (ViewMod == false) {
					ViewMod = true;
					d.setCannotDothing(true);
				} else {
					ViewMod = false;
					if (d.getDrawBackPack() == false) {
						d.setCannotDothing(false);
					}
					blockWidth = 30;
					blockHeight = blockWidth;
					playerWidth = blockWidth;
					playerHeight = playerWidth * 2;
				}
			}
			break;
		case KeyEvent.VK_Q:// 丢弃物品
			if (!pause) {
				if (bp.bp_enity.size() >= select && bp.stackAmount.get(select - 1) >= 1) {
					bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1) - 1);// 丢物品
					// 物品掉在地上
					drop_Enity.add(new Enity(bp.bp_enity.get(select - 1).getID(), p.I + 2, p.J));
				}
			}
			break;

		case KeyEvent.VK_1:
			if (!pause)
				select = 1;
			break;
		case KeyEvent.VK_2:
			if (!pause)
				select = 2;
			break;
		case KeyEvent.VK_3:
			if (!pause)
				select = 3;
			break;
		case KeyEvent.VK_4:
			if (!pause)
				select = 4;
			break;
		case KeyEvent.VK_6:
			if (!pause)
				select = 5;
			break;
		case KeyEvent.VK_7:
			if (!pause)
				select = 7;
			break;
		case KeyEvent.VK_8:
			if (!pause)
				select = 8;
			break;
		case KeyEvent.VK_9:
			if (!pause)
				select = 9;
			break;
		case KeyEvent.VK_0:
			if (!pause)
				select = 10;
			break;
		case KeyEvent.VK_ESCAPE:
			if (d.getDrawMainMenu() == false && d.getDrawChest() == false && d.getDrawBackPack() == false
					&& d.getCannotDothing() == false) {
				pause = !pause;
				d.setDrawOptionMenu(!d.getDrawOptionMenu());
			}
			d.setDrawChest(false);
			d.setDrawBackPack(false);
			d.setCannotDothing(false);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (d.getCannotDothing() == false && !pause) {
				p.left = false;
				p.towards = 0;
			}
			break;
		case KeyEvent.VK_D:
			if (d.getCannotDothing() == false && !pause) {
				p.right = false;
				p.towards = 0;
			}
			break;
		}
		// System.out.println(player.X + " " + player.Y);
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (d.getDrawMainMenu()) {
			mm.doThingOnMouseMove(e);
		}
		if (d.getDrawOptionMenu()) {
			om.doThingOnMouseMove(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// 左键
		if (e.getButton() == 1) {
			// gBuffer.drawRect(b.getX(), b.getY(), blockWidth, blockHeight);

			block bb = tools.getPixBlock(e.getX(), e.getY());
			if (d.getDrawMainMenu() == true) {
				mm.doThingOnMousePressed(e);
			} else if (d.getDrawOptionMenu() == true) {
				om.doThingOnMousePressed(e);
			} else if (d.getCannotDothing() == false && destroy == false && bb.getImg() != null && bb != null
					&& Math.sqrt((p.I - bb.getI()) * (p.I - bb.getI()) + (p.J - bb.getJ()) * (p.J - bb.getJ())) <= 5) {// can就破坏方块
				block_ID = bb.getID();
				System.out.println("破坏物品");
				destroyBlock(bb);
			} else if (d.getDrawBackPack() == true) {// 背包打开状态
				bp.doThingOnMousePressed(e);
				rb.doThingOnMousePressed(e);
			} else if (d.getDrawChest() == true) {// 箱子打开状态
				bp.doThingOnMousePressed(e);
				chest.doThingOnMousePressed(e);
			} else if (tools.getPixNPC(mouseX, mouseY) != null
					&& Math.sqrt((p.I - bb.getI()) * (p.I - bb.getI()) + (p.J - bb.getJ()) * (p.J - bb.getJ())) <= 2) { // 攻击生物
				NPC n = tools.getPixNPC(mouseX, mouseY);
				if (n.canIntersect) {// 能被攻击
					if (bp.stackAmount.get(select - 1) > 0) {
						Enity en = bp.bp_enity.get(select - 1);

						System.out.println("攻击生物，造成伤害" + en.getAtk() + " 生命" + n.hp);
						n.hp -= en.getAtk();
					} else {
						System.out.println("攻击生物，造成伤害" + 1 + " 生命" + n.hp);
						n.hp -= 1;
					}
					n.knockBack(p.getRect());
					if (n.hp <= 0)
						npcs.remove(n);
				}
			}

		}
		// 右键
		else if (e.getButton() == 3) {
			if (d.getCannotDothing() == false) {// 背包不处于打开状态
				block bb = tools.getPixBlock(e.getX(), e.getY());
				if (bb != null && bb.getImg() != null && Math
						.sqrt((p.I - bb.getI()) * (p.I - bb.getI()) + (p.J - bb.getJ()) * (p.J - bb.getJ())) <= 5) {// 右键点击物品交互
					int skill = block.getSkill(bb.getID());
					switch (skill) {
					case 0:
						System.out.println("交互_普通物品");
						if (bb.getID() == 1) {// 泥土 种种子
							Enity en = bp.bp_enity.get(select - 1);
							if (bp.stackAmount.get(select - 1) > 0 && en.getID() == 200
									&& tools.getPosBlock(bb.getI(), bb.getJ() - 1) == null) {// 萝卜
								bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1).intValue() - 1);// 使用
								gameMap.map0[bb.getJ() - 1][bb.getI()] = 15;
							}
							else if (bp.stackAmount.get(select - 1) > 0 && en.getID() == 24
									&& tools.getPosBlock(bb.getI(), bb.getJ() - 1) == null) {// 萝卜
								bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1).intValue() - 1);// 使用
								gameMap.map0[bb.getJ() - 1][bb.getI()] = 24;
							}
						} else if (bb.getID() == 19) {// 门下部关
							gameMap.map0[bb.getJ()][bb.getI()] = 21;
							gameMap.map0[bb.getJ() - 1][bb.getI()] = 22;
						} else if (bb.getID() == 20) {// 门上部关
							gameMap.map0[bb.getJ()][bb.getI()] = 22;
							gameMap.map0[bb.getJ() + 1][bb.getI()] = 21;
						} else if (bb.getID() == 21) {// 门下部开
							gameMap.map0[bb.getJ()][bb.getI()] = 19;
							gameMap.map0[bb.getJ() - 1][bb.getI()] = 20;
						} else if (bb.getID() == 22) {// 门上部开
							gameMap.map0[bb.getJ()][bb.getI()] = 20;
							gameMap.map0[bb.getJ() + 1][bb.getI()] = 19;
						}

						break;
					case 1:
						System.out.println("交互_箱子");
						backpack.bI = bb.getI();
						backpack.bJ = bb.getJ();
						tools.readChest(bb.getI(), bb.getJ());
						d.setDrawChest(!d.getDrawChest());
						break;
					case 2:
						System.out.println("交互_熔炉");
						break;
					case 3:
						System.out.println("交互_合成台");
						rb.setSize(3);
						d.setDrawBackPack(!d.getDrawBackPack());
						break;
					case 4:
						System.out.println("交互_附魔台");
						break;
					}
				} else if (tools.getPixNPC(mouseX, mouseY) != null && tools.getPixNPC(mouseX, mouseY).canInteract) {// 如果点击NPC
					NPC n = tools.getPixNPC(mouseX, mouseY);
					ms.doThing(n);
					System.out.println("交互_NPC");
				} else if (bp.stackAmount.get(select - 1) > 0) {// 其他情况 放置物品
					Enity en = bp.bp_enity.get(select - 1);
					for (int i = 0; i < npcs.size(); i++) {
						if (bb.getRect().intersects(npcs.get(i).getRect()) && npcs.get(i).canInteract == true) {
							return;
						}
					}
					if (en.getCanPut()) {// 该物品是否可以放置

						if (bb != null && bb.getImg() == null && !bb.getRect().intersects(p.getRect()) && Math.sqrt(
								(p.I - bb.getI()) * (p.I - bb.getI()) + (p.J - bb.getJ()) * (p.J - bb.getJ())) <= 5) {// 判定是否可以放置方块
							System.out.println("放置物品");
							if (en.getID() == 23) {// 放置门
								if (gameMap.map0[bb.getJ() - 1][bb.getI()] != 0) {
									return;
								} else {
									bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1).intValue() - 1);// 使用
									gameMap.map0[bb.getJ()][bb.getI()] = 19;
									gameMap.map0[bb.getJ() - 1][bb.getI()] = 20;
								}
							}else {
								bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1).intValue() - 1);// 使用
								gameMap.map0[bb.getJ()][bb.getI()] = en.getID();
							}
						}
					} else if (en.getCanEat()) {// 该物品是否可以吃
						System.out.println("吃食物");
						eatEnity(en);
					} else {// 其他物品使用方法
						// block b=tools.getPixBlock(mouseX, mouseY);
						// npc=new shoot(p.I, p.J, b.getI(), b.getJ());
						// npcs.add(npc);
					}
				}

			} else if (d.getDrawBackPack() == true) {// 背包打开状态使用右键
				bp.doThingOnMousePressed(e);
				rb.doThingOnMousePressed(e);
			} else if (d.getDrawChest() == true) {// 箱子打开状态
				bp.doThingOnMousePressed(e);
				chest.doThingOnMousePressed(e);
			}
		}
	}

	public void eatEnity(final Enity en) {
		if (eating == false && (float) hp.getHunger() / hp.getMaxHunger() <= 0.95) {
			new Thread(new Runnable() {
				public void run() {
					eating = true;
					eatingPiece = 20;
					d.setDrawProgress(true);
					Progress.setMax(eatingPiece);
					Progress.setNow(eatingPiece);
					while (eatingPiece > 0 && eating == true) {
						eatingPiece -= 1;
						Progress.setNow(eatingPiece);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					eating = false;
					d.setDrawProgress(false);
					if (eatingPiece <= 0) {

						bp.stackAmount.set(select - 1, bp.stackAmount.get(select - 1).intValue() - 1);// 使用
						hp.setHunger(hp.getHunger() + en.getHungerHeal());// 增加饥饿度
						if (hp.getHunger() > hp.getMaxHunger()) {
							hp.setHunger(hp.getMaxHunger());
						}
						eatingPiece = 20;
					}
				}
			}).start();
		}
	}

	public static void destroyBlockDirect(int i, int j) {// 直接破坏方块
		int id = gameMap.map0[j][i];
		gameMap.map0[j][i] = 0;
		newDropEnity(block.getDropID(id), i, j, block.getDropAmount(id));
		if (block.getName(id).contains("箱子")) {// 箱子中的物品掉落
			tools.readChest(i, j);
			for (int ii = 0; ii < chest.bp_enity.size(); ii++) {
				for (int jj = 0; jj < chest.stackAmount.get(ii); jj++) {
					Enity e = new Enity(chest.bp_enity.get(ii).getID(), i, j);
					e.setBreak(chest.bp_enity.get(ii).getBreak());
					drop_Enity.add(e);
				}
				chest.stackAmount.set(ii, 0);
			}
			File f = new File(path_chest + i + "-" + j + ".dat");
			if (f.exists())
				f.delete();
		}
	}

	public void destroyBlock(final block bb) {
		bI = bb.getI();
		bJ = bb.getJ();
		int id = bb.getID();
		double percent = 0.0;
		int HP = block.getHP(bb.getID());
		Enity en = bp.bp_enity.get(select - 1);
		if (block.getHP(bb.getID()) == -1)
			return;// 无法摧毁
		if (bp.stackAmount.get(select - 1) > 0) {

			int amount = bp.stackAmount.get(select - 1);
			if (amount > 0) {
				percent = tools.getPercent(block.getType(bb.getID()), en.getType(), en.getToolType());
			} else {
				percent = 1.0;
			}
		} else {// 用手
			percent = 1.0;
		}
		block_HP = (int) (percent * HP);
		d.setDrawProgress(true);
		Progress.setMax(block_HP);
		Progress.setNow(block_HP);
		final int t_hp = HP;
		final double pp = percent;// percent到匿名类的中转
		if (destroy == false) {
			new Thread(new Runnable() {
				public void run() {
					destroy = true;

					while (block_HP > 0 && destroy == true) {
						block_HP -= 20;
						Progress.setNow(block_HP);
						double rr = (double) block_HP / (pp * t_hp);
						destroyAmount = (int) (rr * 10);// destroy图片下标
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					destroy = false;
					d.setDrawProgress(false);
					if (block_HP <= 0) {
						gameMap.map0[bJ][bI] = 0;
						if (bb.getID() == 19 || bb.getID() == 21) {// 门下部
							gameMap.map0[bJ - 1][bI] = 0;
						}
						if (bb.getID() == 20 || bb.getID() == 22) {// 门上部
							gameMap.map0[bJ + 1][bI] = 0;
						}
						if (en.getBreak() >= 1) {// 减少耐久度
							en.setBreak(en.getBreak() - 1);
						} else if (en.getBreak() < 1 && en.getBreak() != -1) {// 无耐久且是有耐久度的工具
							bp.stackAmount.set(select - 1, 0);
						}
						newDropEnity(block.getDropID(bb.getID()), bb.getI(), bb.getJ(),
								block.getDropAmount(bb.getID()));
						if (block.getName(id).contains("箱子")) {// 箱子中的物品掉落
							tools.readChest(bI, bJ);
							for (int ii = 0; ii < chest.bp_enity.size(); ii++) {
								for (int jj = 0; jj < chest.stackAmount.get(ii); jj++) {
									Enity e = new Enity(chest.bp_enity.get(ii).getID(), bI, bJ);
									e.setBreak(chest.bp_enity.get(ii).getBreak());
									drop_Enity.add(e);
								}
								chest.stackAmount.set(ii, 0);
							}
							File f = new File(path_chest + bb.getI() + "-" + bb.getJ() + ".dat");
							if (f.exists())
								f.delete();
						}
						block_HP = 0;
					}
				}
			}).start();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1 && destroy == true) {
			destroy = false;
		}
		if (e.getButton() == 3 && eating == true) {
			eating = false;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if (pause == false) {
			if (ViewMod == true) {
				if (e.getWheelRotation() == -1) {
					if (blockWidth <= 60) {
						blockWidth += 2;
						blockHeight = blockWidth;
						playerWidth = blockWidth;
						playerHeight = playerWidth * 2;
					}
				}
				if (e.getWheelRotation() == 1) {
					if (blockWidth >= 10) {
						p.mX = 0;
						blockWidth -= 2;
						blockHeight = blockWidth;
						playerWidth = blockWidth;
						playerHeight = playerWidth * 2;
					}
				}
			} else if (ViewMod == false) {
				destroy = false;
				eating = false;
				if (e.getWheelRotation() == 1) {

					if (select == 10) {
						select = 1;
					} else {
						select += 1;
					}
				}
				if (e.getWheelRotation() == -1) {
					if (select == 1) {
						select = 10;
					} else {
						select -= 1;
					}
				}
			}
		}
	}
}
