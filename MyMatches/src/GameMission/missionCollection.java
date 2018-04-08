package GameMission;

import GameGraphics.mainFrame;
import GameTool.tools;
//收集任务
public class missionCollection extends missionStandard {
	boolean accept = false;// 是否已经接受任务
	int id;

	public missionCollection(){
	}

	public void setEnityID(int i){
		id=i;
	}
	public void doMission() {
		try {
			if (doThing == true && (doAmount == -1 || doAmount > 0)) {
				if (accept == false && n.getName().equals(npc_Public)) {// 区分大小写名字
					accept = true;
					saySomething(publicDialog);
				} else if (accept == true && n.getName().equals(npc_Finish) && tools.getEnityOnHand().getID() == id) {// 区分大小写名字
					mainFrame.bp.stackAmount.set(mainFrame.select-1,mainFrame.bp.stackAmount.get(mainFrame.select-1)-1);
					saySomething(finishDialog);
					if (doAmount != -1) {
						doAmount--;
					}
					setFinish(true);//任务至少完成了一次
					accept = false;
				}
				doThing = false;
			}
		} catch (Exception e) {
		}
	}
}
