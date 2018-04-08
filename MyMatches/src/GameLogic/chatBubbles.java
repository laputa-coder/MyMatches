package GameLogic;

import java.awt.Color;
import java.awt.Graphics;

import GameGraphics.mainFrame;
import GameResource.gameConfig;
import GameTool.tools;

public class chatBubbles implements gameConfig{
	int x=0,y=0;
	int width=0;
	int height=0;
	NPC npc;
	long delay;
	String dialog;
	public boolean drawBubble=true;
	
	public chatBubbles(NPC n,long delay,String d){/*NPC 消失延迟 显示文本*/
		/*NPC头顶中点*/
		npc=n;
		this.delay=delay;
		this.dialog=d;
		width=tools.length(d)*7;
		height=40;
		start();
	}
	public void start(){
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
				}
				drawBubble=false;
			}
		}).start();
	}
	public void draw(Graphics g){
		
		if(drawBubble==true){
			g.setColor(Color.black);
			this.x=npc.x+mainFrame.playerWidth/2;
			this.y=npc.y-50;
			g.drawImage(pic_chatBubble, x-(width+10)/2, y, width+10, height, null);
			g.drawString(dialog, x-width/2+5, y+height/2);
		}
	}
}
