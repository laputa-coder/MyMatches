package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameTool.tools;

public class Light {
	public int amount=50;//光线数量
	public int X=0;//光源X
	public int Y=0;//光源Y
	public void drawLine(int x,int y,Graphics g){//光线终点
		double k=0;
		int b;
		k=(double)(y-Y)/(double)(x-X);
		if(Math.abs(k)>5){
			b=X;
			if(y>Y){
				int i;
				//System.out.println(x-X);
				for(i=0;i<y-Y;i++){
					if(tools.intersectJudge(new Rectangle((int)(i/k)+b, i+Y, 1, 1))==true){
						break;
					}
				}
				if(i>=y-Y){
					g.setColor(Color.blue);
					g.drawLine(X, Y, x, y);
				}else{
					g.setColor(Color.blue);
					g.drawLine(X, Y, (int)(i/k)+b, i+Y);
				}
			}else{
				int i;
				for(i=0;i>y-Y;i--){
					//System.out.println(i+X+" "+(int)(k*i)+b);
					if(tools.intersectJudge(new Rectangle((int)(i/k)+b, i+Y, 1, 1))==true){
						//System.out.println(1);
						break;
					}
				}
				if(i<=y-Y){
					g.setColor(Color.blue);
					g.drawLine(X, Y, x, y);
					
				}else{
					g.setColor(Color.blue);
					g.drawLine(X, Y, (int)(i/k)+b, i+Y);
				}
			}
		}else{
			b=Y;
			if(x>X){
				int i;
				//System.out.println(x-X);
				for(i=0;i<x-X;i++){
					if(tools.intersectJudge(new Rectangle(i+X, (int)(k*i)+b, 1, 1))==true){
						break;
					}
				}
				if(i>=x-X){
					g.setColor(Color.blue);
					g.drawLine(X, Y, x, y);
				}else{
					g.setColor(Color.blue);
					g.drawLine(X, Y, i+X, (int)(k*i)+b);
				}
			}else{
				int i;
				for(i=0;i>x-X;i--){
					//System.out.println(i+X+" "+(int)(k*i)+b);
					if(tools.intersectJudge(new Rectangle(i+X, (int)(k*i+b), 1, 1))==true){
						//System.out.println(1);
						break;
					}
				}
				if(i<=x-X){
					g.setColor(Color.blue);
					g.drawLine(X, Y, x, y);
					
				}else{
					g.setColor(Color.blue);
					g.drawLine(X, Y, i+X, (int)(k*i+b));
				}
			}
		}
	}
	public void draw(Graphics g){
		drawLine(X-20, Y-200, g);
		drawLine(X-100 ,Y-100, g);
		drawLine(X-20, Y+200, g);
		drawLine(X-100 ,Y+100, g);
		drawLine(X+20, Y-200, g);
		drawLine(X+100 ,Y-100, g);
		drawLine(X+20, Y+200, g);
		drawLine(X+100 ,Y+100, g);
		//drawLine(X-200, Y+200, g);
		//drawLine(X-200, Y-200, g);
	}
}
