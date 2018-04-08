package PLAYER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import GameGraphics.mainFrame;
import GameLogic.Enity;
import GameLogic.shapedRecipe;
import GameResource.gameConfig;

public class recipeBar extends backpack implements gameConfig {

	public recipeBar(player p, int y, int Imgsize_backpack, int barSize) {
		super(p, y, Imgsize_backpack, barSize, barSize + 1);
	}

	public void setSize(int barSize) {
		column = barSize;
		line = barSize + 1;
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
					if (num == column * line - 1 || (num + 1) % line != 0 && stackAmount.get(num) > 0) {// 点击位置不为空位
						enityOnMouse = true;
						tempBP = this;
						temp_Enity = bp_enity.get(num);
						temp_Stack = stackAmount.get(num);
						temp_num = num;
						stackAmount.set(num, 0);
						if (num == column * line - 1) {// 消耗
							for (int i = 0; i < bp_enity.size(); i++) {
								if (stackAmount.get(i) > 0) {
									stackAmount.set(i, stackAmount.get(i) - 1);
								}
							}
						}
					}
					/* 合成检测 */
					judge();
				} else if (enityOnMouse == true) {
					if ((num + 1) % line != 0) {

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

						/* 合成检测 */
						judge();
					}
				}
			}

		} else if (e.getButton() == 3) {// 右键
			if (mouseX >= x && mouseX <= x + line * Imgsize_backpack && mouseY >= y
					&& mouseY <= y + (column) * Imgsize_backpack) {

				int mouseOX = mouseX - x;
				int mouseOY = mouseY - y;
				int num = mouseOY / Imgsize_backpack * line + mouseOX / Imgsize_backpack;
				if (enityOnMouse == false) {
					if (stackAmount.get(num) > 0 && (num == column * line - 1 || (num + 1) % line != 0)) {
						enityOnMouse = true;
						tempBP = this;
						int half = stackAmount.get(num) / 2;
						if (half == 0)
							half = 1;// 取半为0
						temp_Enity = bp_enity.get(num);
						temp_num = num;
						if (num == column * line - 1) {// 消耗
							temp_Stack = stackAmount.get(num);
							stackAmount.set(num, 0);
							for (int i = 0; i < bp_enity.size(); i++) {
								if (stackAmount.get(i) > 0) {
									stackAmount.set(i, stackAmount.get(i) - 1);
								}
							}
						} else {
							temp_Stack = half;
							stackAmount.set(num, stackAmount.get(num) - half);
						}
					}
				} else if (enityOnMouse == true) {// 右键放下一个物品
					if ((num + 1) % line != 0) {
						if (stackAmount.get(num) < bp_enity.get(num).getStackAmount()
								&& (bp_enity.get(num).getID() == temp_Enity.getID() || stackAmount.get(num) <= 0)) {
							bp_enity.set(num, temp_Enity);
							stackAmount.set(num, stackAmount.get(num) + 1);
							temp_Stack -= 1;
							if (temp_Stack == 0)
								enityOnMouse = false;
						}
						/* 合成检测 */
						judge();
					}
				}

			}
		}
	}

	public void judge() {
		/* 合成检测 */
		int aim[][] = new int[column][column];
		int arrayI, arrayJ;
		for (int i = 0; i < bp_enity.size(); i++) {
			arrayI = i / line;
			arrayJ = i % line;
			if (arrayI < column && arrayJ < column && stackAmount.get(i) > 0) {
				aim[arrayI][arrayJ] = bp_enity.get(i).getID();
			}
		}

		File file = new File(recipePath);
		File[] tempList = file.listFiles();
		FileInputStream fis;
		DataInputStream dis;
		shapedRecipe s;
		int amount = 0;
		int standard[][] = new int[3][3];
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				try {
					fis = new FileInputStream(tempList[i]);
					dis = new DataInputStream(fis);
					for (int ii = 0; ii < 9; ii++) {
						standard[ii / 3][ii % 3] = dis.readInt();
					}
					amount = dis.readInt();
					// System.out.println(Integer.valueOf(tempList[i].getName().split(".recipe")[0]));
					s = new shapedRecipe(standard, aim, Integer.valueOf(tempList[i].getName().split(".recipe")[0]),
							amount);
					if (s.matchRecipe()) {// 生成
						bp_enity.set(column * line - 1, s.returnRecipeEnity());
						stackAmount.set(column * line - 1, s.returnRecipeAmount());
						break;
					} else {
						stackAmount.set(column * line - 1, 0);
					}
					dis.close();
					fis.close();
				} catch (Exception e1) {
				}
			}
		}

		/* 合成检测 */
	}

	public void draw(Graphics g) {
		int a = 0;
		g.drawImage(pic_Gui, mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2) - 20, y - 20,
				40 + line * Imgsize_backpack, column * Imgsize_backpack + 40, null);
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < line; j++) {
				if (a == column * line - 1 || (a + 1) % line != 0) {
					g.drawImage(pic_Rect,
							mainFrame.panelWidth / 2 - Imgsize_backpack * (line / 2) + j * Imgsize_backpack,
							y + i * Imgsize_backpack, Imgsize_backpack, Imgsize_backpack, null);
				}
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
			g.drawImage(temp_Enity.getImg(), mainFrame.mouseX, mainFrame.mouseY, Imgsize_backpack - 20,
					Imgsize_backpack - 20, null);
			g.drawString(String.valueOf(temp_Stack), mainFrame.mouseX + Imgsize_backpack - 25,
					mainFrame.mouseY + Imgsize_backpack - 15);
		}
	}
}
