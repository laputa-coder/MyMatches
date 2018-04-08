package GameMission;

import GameLogic.NPC;
import GameResource.gameConfig;

public class mission implements gameConfig, Runnable {
	missionCollection m0 = new missionCollection();
	missionCollection m1 = new missionCollection();
	boolean onChat = false;// 是否正在对话
	NPC n = null;

	public String[] getArray(String... strings) {
		return strings;
	}

	public void INIT() {
		m0.setPublicNPC("NPC");
		m0.setFinishNPC("hahaha");
		m0.setPublicDialog(getArray("你好，你的第一个任务是做一个橡木板", "做好了交给hahaha哦"));
		m0.setFinishDialog(getArray("谢谢你的橡木板"));
		m0.setEnityID(9);
		m0.setDoAmount(2);

		m1.setPublicNPC("hahaha");
		m1.setFinishNPC("hahaha");
		m1.setPublicDialog(getArray("你好", "给我做1个合成台"));
		m1.setFinishDialog(getArray("谢谢你的合成台", "去东方找人吧"));
		m1.setEnityID(12);
		m1.setDoAmount(1);
	}

	public mission() {
		INIT();
		new Thread(this).start();
	}

	public void doThing(NPC n) {
		if (onChat == false) {
			this.n = n;
			onChat = true;
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (n != null && onChat == true) {
					if (m0.getFinish() == false) {
						m0.doThing(n);
					} else if (m0.getFinish() == true) {// 任务0已经完成
						m1.doThing(n);
					}
					onChat = false;
				}
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
	}
}
