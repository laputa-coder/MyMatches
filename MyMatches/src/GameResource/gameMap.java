package GameResource;

import java.awt.Image;

public class gameMap implements gameConfig {
	public static int map0[][];

	public static Image getMapIcon(int num) {
		if (num > 0)
			return ic[num - 1];
		else
			return null;
	}
}
