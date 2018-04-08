package PLAYER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import GameGraphics.mainFrame;
import GameLogic.Enity;
import GameResource.gameConfig;
import GameTool.tools;

public class backpack implements gameConfig {
	int y;
	int Imgsize_backpack = 50;
	public int column = 5;// 背包行数
	public int line = 10;// 列
	public ArrayList<Enity> bp_enity = new ArrayList<Enity>(column * line);// 背包内物品
	public List<Integer> stackAmount = new ArrayList<Integer>();// 背包内物品堆叠数量
	public static boolean enityOnMouse = false;// 鼠标拾起物品
	public int mouseX = 0;// 鼠标X
	public int mouseY = 0;// 鼠标Y
	/* 箱子所在位置 */
	public static int bI = 0;
	public static int bJ = 0;
	public static Enity temp_Enity;
	public static int temp_Stack;
	public static int temp_num = -1;
	public static backpack tempBP;

	public backpack(player p, int y, int Imgsize_backpack, int column, int line) {
		this.column = column;
		this.line = line;
		for (int i = 0; i < column * line; i++) {
			bp_enity.add(new Enity(1, 0, 0));
			stackAmount.add(0);
		}
		this.y = y;
		this.Imgsize_backpack = Imgsize_backpack;
	}

	public int getImgsize() {
		return Imgsize_backpack;
	}

	public void doThingOnMouseMoved(Graphics g) {
		if (mainFrame.d.getDrawBackPack() || mainFrame.d.getDrawChest()) {
			int x = mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2);
			if (mainFrame.mouseX >= x && mainFrame.mouseX <= x + line * Imgsize_backpack && mainFrame.mouseY >= y
					&& mainFrame.mouseY <= y + (column) * Imgsize_backpack) {
				int mouseOX = mainFrame.mouseX - x;
				int mouseOY = mainFrame.mouseY - y;
				int num = mouseOY / Imgsize_backpack * line + mouseOX / Imgsize_backpack;
				try {
					if (stackAmount.get(num) > 0) {
						g.setColor(Color.black);
						g.drawString(bp_enity.get(num).getName(), mainFrame.mouseX, mainFrame.mouseY - 5);
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("num越界？");
				}
			}
		}
	}

	public void doThingOnMousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		int x = mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2);
		if (e.getButton() == 1) {
			if (mouseX >= x && mouseX <= x + line * Imgsize_backpack && mouseY >= y
					&& mouseY <= y + (column) * Imgsize_backpack) {

				int mouseOX = mouseX - x;
				int mouseOY = mouseY - y;
				int num = mouseOY / Imgsize_backpack * line + mouseOX / Imgsize_backpack;
				if (enityOnMouse == false) {
					if (stackAmount.get(num) > 0) {// 点击位置不为空位
						enityOnMouse = true;
						tempBP = this;
						temp_Enity = bp_enity.get(num);
						temp_Stack = stackAmount.get(num);
						temp_num = num;
						stackAmount.set(num, 0);
					}
				} else if (enityOnMouse == true) {
					enityOnMouse = false;
					if (bp_enity.get(num).getID() == temp_Enity.getID()
							&& stackAmount.get(num) + temp_Stack <= temp_Enity.getStackAmount()) {

						bp_enity.set(num, temp_Enity);
						stackAmount.set(num, stackAmount.get(num) + temp_Stack);
					} else if (bp_enity.get(num).getID() == temp_Enity.getID()
							&& stackAmount.get(num) + temp_Stack > temp_Enity.getStackAmount()
							&& stackAmount.get(num) != temp_Enity.getStackAmount()
							&& temp_Stack != temp_Enity.getStackAmount()) {
						bp_enity.set(num, temp_Enity);
						temp_Stack = stackAmount.get(num) + temp_Stack - temp_Enity.getStackAmount();
						stackAmount.set(num, temp_Enity.getStackAmount());

						enityOnMouse = true;
					} else {
						// 交换
						if (temp_num != -1 && tempBP.stackAmount.get(temp_num) > 0 && stackAmount.get(num) <= 0) {
							bp_enity.set(num, temp_Enity);
							stackAmount.set(num, temp_Stack);
						} else if (temp_num != -1 && stackAmount.get(num) <= 0) {
							tempBP.bp_enity.set(temp_num, bp_enity.get(num));
							tempBP.stackAmount.set(temp_num, stackAmount.get(num));
							bp_enity.set(num, temp_Enity);
							stackAmount.set(num, temp_Stack);
						} else if (temp_num != -1 && !(tempBP instanceof recipeBar)
								&& bp_enity.get(num).getID() != temp_Enity.getID()) {
							Enity tempE;
							int tempS;
							tempE = temp_Enity;
							tempS = temp_Stack;
							tempBP.bp_enity.set(temp_num, bp_enity.get(num));
							tempBP.stackAmount.set(temp_num, stackAmount.get(num));
							bp_enity.set(num, tempE);
							stackAmount.set(num, tempS);
						} else if (temp_num != -1 && tempBP instanceof recipeBar
								&& temp_num != tempBP.column * tempBP.line - 1
								&& bp_enity.get(num).getID() != temp_Enity.getID()) {
							Enity tempE;
							int tempS;
							tempE = temp_Enity;
							tempS = temp_Stack;
							tempBP.bp_enity.set(temp_num, bp_enity.get(num));
							tempBP.stackAmount.set(temp_num, stackAmount.get(num));
							bp_enity.set(num, tempE);
							stackAmount.set(num, tempS);
						} else {
							Enity tempE;
							int tempS;
							tempE = temp_Enity;
							tempS = temp_Stack;
							temp_num = -1;
							temp_Enity = bp_enity.get(num);
							temp_Stack = stackAmount.get(num);
							bp_enity.set(num, tempE);
							stackAmount.set(num, tempS);
							if (temp_Stack != 0)
								enityOnMouse = true;
						}

						temp_num = -1;// 刷新
					}
					mainFrame.rb.judge();
					tools.writeChest(bI, bJ);
				}

			}
		} else if (e.getButton() == 3) {// 右键
			if (mouseX >= x && mouseX <= x + line * Imgsize_backpack && mouseY >= y
					&& mouseY <= y + (column) * Imgsize_backpack) {

				int mouseOX = mouseX - x;
				int mouseOY = mouseY - y;
				int num = mouseOY / Imgsize_backpack * line + mouseOX / Imgsize_backpack;
				if (enityOnMouse == false) {
					if (stackAmount.get(num) > 0) {// 点击位置不为空位
						enityOnMouse = true;
						tempBP = this;
						int half = stackAmount.get(num) / 2;
						if (half == 0)
							half = 1;// 取半为0
						temp_Enity = bp_enity.get(num);
						temp_Stack = half;
						temp_num = num;
						stackAmount.set(num, stackAmount.get(num) - half);
					}
				} else if (enityOnMouse == true) {// 右键放下一个物品
					if (stackAmount.get(num) < bp_enity.get(num).getStackAmount()
							&& (bp_enity.get(num).getID() == temp_Enity.getID() || stackAmount.get(num) <= 0)) {
						bp_enity.set(num, temp_Enity);
						stackAmount.set(num, stackAmount.get(num) + 1);
						temp_Stack -= 1;
						if (temp_Stack == 0)
							enityOnMouse = false;
					}
					mainFrame.rb.judge();
				}
			}
		}
	}

	public boolean addItem(Enity enity) {// 加入背包
		boolean result = false;
		for (int i = 0; i < bp_enity.size(); i++) {
			if (bp_enity.get(i).getID() == enity.getID()
					&& stackAmount.get(i) < new Enity(enity.getID(), 0, 0).getStackAmount() && stackAmount.get(i) > 0) {
				stackAmount.set(i, new Integer(stackAmount.get(i).intValue() + 1));
				result = true;// 加入成功
				break;
			} else if (stackAmount.get(i) <= 0) {
				bp_enity.set(i, enity);
				stackAmount.set(i, new Integer(stackAmount.get(i).intValue() + 1));
				result = true;// 加入成功
				break;
			} else if (i == bp_enity.size() - 1) {// 循环结束 没有找到有合适物品的位置
				result = false;// 加入失败
				break;
			}

		}
		return result;
	}

	public void draw(Graphics g) {
		int a = 0;
		g.drawImage(pic_Gui, mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2) - 20, y - 20,
				40 + line * Imgsize_backpack, column * Imgsize_backpack + 40, null);
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < line; j++) {
				g.drawImage(pic_Rect, mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2) + j * Imgsize_backpack,
						y + i * Imgsize_backpack, Imgsize_backpack, Imgsize_backpack, null);
				if (a < bp_enity.size()) {
					if (stackAmount.get(a) > 0) {
						g.drawImage(bp_enity.get(a).getImg(),
								mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2) + j * Imgsize_backpack + 10,
								y + i * Imgsize_backpack + 10, Imgsize_backpack - 20, Imgsize_backpack - 20, null);
						g.setColor(Color.BLACK);
						g.drawString(
								String.valueOf(stackAmount.get(a)), mainFrame.panelWidth / 2
										- Imgsize_backpack * (line / 2) + (j + 1) * Imgsize_backpack - 15,
								y + (i + 1) * Imgsize_backpack - 5);
					}
					a++;
				}
			}
		}
		if (enityOnMouse) {
			g.setColor(Color.black);
			g.drawImage(temp_Enity.getImg(), mainFrame.mouseX, mainFrame.mouseY, Imgsize_backpack - 20,
					Imgsize_backpack - 20, null);
			g.drawString(String.valueOf(temp_Stack), mainFrame.mouseX + Imgsize_backpack - 25,
					mainFrame.mouseY + Imgsize_backpack - 15);
		}
	}
}
