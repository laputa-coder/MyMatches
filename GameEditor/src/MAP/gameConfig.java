package MAP;


import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public interface gameConfig {//游戏静态数据
	//窗口标题
	String title="地图编辑器";
	String path = "./data/map/map.map";  
	String blockPath= "./data/blocks/";  
	String enityPath= "./data/enity/";
	String recipePath= "./data/recipe/";  

	
	//游戏方块大小
	int blockWidth=30;
	int blockHeight=30;
	//窗口大小
	int panelWidth=blockWidth*1024;
	int panelHeight=blockWidth*256;
	
	//NPC大小
	int playerWidth=30;
	int playerHeight=60;
	
	Image ic[]=tools.getResImg();

	Image Rect=tools.makeColorTransparent(new ImageIcon("./images/display/Rect.png").getImage(),Color.white);
}