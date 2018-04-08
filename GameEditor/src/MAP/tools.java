package MAP;
import java.awt.*;
import java.awt.image.*;

import javax.swing.ImageIcon;
public class tools implements gameConfig{
	/* 使图片白色部分透明 */
	public static Image makeColorTransparent(Image im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	public static Image[] getResImg() {
		int n=18;
		Image i[]=new Image[n];
		for(int j=0;j<n;j++){
			 i[j]=tools.makeColorTransparent(new ImageIcon("./images/"+(j+1)+".png").getImage(),Color.white);
		}
		return i;
	}
}

