package GameMission;

import GameGraphics.mainFrame;
import GameLogic.NPC;

public abstract class missionStandard {
	String publicDialog[] = { "你好，你的第一个任务是做一个橡木板", "做好了交给我哦" };// 任务发布的对话文本
	String finishDialog[] = { "谢谢你的橡木板" };// 任务完成的对话文本
	NPC n = null;
	String npc_Public=null;
	String npc_Finish=null;
	public boolean finish=false;//是否至少完成过一次
	int doAmount = -1;// 任务可执行次数 -1为重复 为0就不能做
	public boolean doThing = false;// 触发条件
	public abstract void doMission();
	
	public void setFinish(boolean b){
		finish=b;
	}
	public boolean getFinish(){
		return finish;
	}
	public void setDoAmount(int i){//设置任务可完成次数 -1为重复完成
		doAmount=i;
	}
	public void setPublicNPC(String n){//设置任务发布NPC
		npc_Public=n;
	}
	public void setFinishNPC(String n){//设置任务完成NPC
		npc_Finish=n;
	}
	public void setPublicDialog(String s[]){
		publicDialog=s;
	}
	public void setFinishDialog(String s[]){
		finishDialog=s;
	}
	public void saySomething(String a[]) throws InterruptedException {
		for (int i = 0; i < a.length; i++) {
			mainFrame.newChatBubble(n, 2000, a[i]);
			Thread.sleep(2000);
		}
	}
	public NPC getNPC() {
		return n;
	}
	public void setNPC(NPC nn) {
		n = nn;
	}
	public void doThing(NPC n) {//任务入口
		if (doThing == false) {
			doThing = true;
			setNPC(n);
			doMission();
		}
	}
}
