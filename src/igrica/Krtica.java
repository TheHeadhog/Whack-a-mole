package igrica;

import java.awt.*;
import java.awt.Graphics;


public class Krtica extends Zivotinja {

	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void ispoljiEfekatPobegle() {
		rupa.setZivotinja(null);
		rupa.basta.pojediPovrce();
	}
	
	@Override
	public void ispoljiEfekatUdarene() {
		rupa.prekiniNit();
	}
	
	public void iscrtaj(int korak) {
		int sizeW= rupa.getWidth()*korak/rupa.getBrKoraka();
		int sizeH= rupa.getHeight()*korak/rupa.getBrKoraka();
		Graphics g = rupa.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillOval(rupa.getWidth()/2-sizeW/2, rupa.getHeight()/2-sizeH/2, sizeW, sizeH);
	}

	
}
