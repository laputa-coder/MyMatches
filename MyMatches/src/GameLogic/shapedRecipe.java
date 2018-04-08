package GameLogic;

public class shapedRecipe {
	int standard[][] = { 
			{ 1, 1, 0, 0}, 
			{ 0, 0, 0, 1}, 
			{ 0, 1, 0, 1} };
	int aim[][] = { 
			{ 0, 1, 1, 0, 0}, 
			{ 0, 0, 0, 0, 1}, 
			{ 0, 0, 1, 0, 1} };
	int id;
	int amount;
	public shapedRecipe(int[][] standard, int[][] aim,int id,int amount) {
		this.standard= standard;
		this.aim= aim;
		this.id=id;
		this.amount=amount;
	}
	private int[][] analyzeAim(int aim[][]) {
		int result[][];
		int Ymin = 100, Ymax = -1;
		int Xmin = -1, Xmax = -1;
		for (int i = 0; i < aim.length; i++) {// ÁĞÊı
			for (int j = 0; j < aim[i].length; j++) {
				if (aim[i][j] != 0) {
					if (j < Ymin)
						Ymin = j;
					break;
				}
			}
			for (int j = aim[i].length - 1; j >= 0; j--) {
				if (aim[i][j] != 0) {
					if (j > Ymax)
						Ymax = j;
					break;
				}
			}
		}

		for (int i = 0; i < aim.length; i++) {
			for (int j = 0; j < aim[i].length; j++) {
				if (aim[i][j] != 0) {
					Xmin = i;
					break;
				}

			}
			if (Xmin != -1)
				break;
		}
		for (int i = aim.length - 1; i >= 0; i--) {
			for (int j = 0; j < aim[i].length; j++) {
				if (aim[i][j] != 0) {
					Xmax = i;
					//System.out.println("Xmax:" + Xmax);
					break;
				}
			}
			if (Xmax != -1) {
				break;
			}
		}
//		System.out.println("length:" + aim.length);
//		System.out.println("X:" + Xmin + " " + Xmax);
//		System.out.println("Y:" + Ymin + " " + Ymax);
		
		if(Xmax==-1&&Ymax==-1){
			result = new int[0][0];
		}else{
			result = new int[Xmax - Xmin + 1][Ymax - Ymin + 1];
		}
		
		for (int i = Xmin; i <= Xmax; i++) {
			for (int j = Ymin; j <= Ymax; j++) {
				result[i - Xmin][j - Ymin] = aim[i][j];
			}
		}
		return result;
	}

	public boolean matchRecipe() {
		boolean result = true;
		int a[][] = analyzeAim(aim);
		int s[][] = analyzeAim(standard);
		if (a.length==0||a.length * a[0].length != s.length * s[0].length) {
			result = false;
			return result;
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != s[i][j]) {
					result = false;
					return result;
				}
			}
		}
		return result;
	}

	public int returnRecipeAmount() {
		return amount;
	}

	public Enity returnRecipeEnity() {
		return new Enity(id, 0, 0);
	}
}