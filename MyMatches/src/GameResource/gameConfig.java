package GameResource;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

import GameTool.tools;

//import GameTool.*;
public interface gameConfig {// 游戏静态数据
	// 窗口标题
	String title = "Matches' Game";
	// String mapPath = "./map/map.map";
	// String blockPath= "./data/blocks/";
	// String enityPath= "./data/enity/";

	String mapPath = "./data/map/map.map";
	String dropEnityPath = "./data/map/dropEnity.dat";
	String backPackPath = "./data/map/backPack.dat";
	String path_playerData = "./data/map/player.dat";
	String path_chest = "./data/map/chest/";
	String blockPath = "./data/blocks/";
	String enityPath = "./data/enity/";
	String recipePath= "./data/recipe/";  

	Image ic[] = tools.getResImg();
	Image icP[] = tools.getPlayerImg();

	Image pic_HP = new ImageIcon("./images/display/HP.png").getImage();
	Image pic_HUNGER = new ImageIcon("./images/display/HUNGER.png").getImage();
	Image pic_Button = new ImageIcon("./images/display/button.png").getImage();
	Image pic_Progress = tools.makeColorTransparent(new ImageIcon(
			"./images/display/Progress.png").getImage(), Color.white);
	Image pic_destroyImg[] = tools.getDestroyImg();
	Image pic_Rect = tools.makeColorTransparent(new ImageIcon(
			"./images/display/Rect.png").getImage(), Color.white);
	Image pic_Select = tools.makeColorTransparent(new ImageIcon(
			"./images/display/Select.png").getImage(), Color.white);
	Image pic_Gui = tools.makeColorTransparent(new ImageIcon(
			"./images/display/gui.png").getImage(), Color.white);
	Image pic_moon = new ImageIcon("./images/moon.png").getImage();
	Image pic_sun = new ImageIcon("./images/sun.png").getImage();
	Image pic_dayNight = new ImageIcon("./images/daynight.png").getImage();
	Image pic_shadow = tools.getShadowImg();
	Image pic_white = new ImageIcon(
			"./images/env/white.png").getImage();
	Image pic_chatBubble = new ImageIcon(
			"./images/env/chatBubble.png").getImage();
	Image pic_LOGO = new ImageIcon(
			"./images/env/LOGO.png").getImage();
}