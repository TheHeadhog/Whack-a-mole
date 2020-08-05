package igrica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Rupa extends Canvas implements Runnable{
	protected Basta basta;
	private Zivotinja zivotinja;
	private int brKoraka;
	private int trenutniBrKoraka=1;
	private Thread nit;
	private boolean active=false;
	
	public Zivotinja getZivotinja() {
		return zivotinja;
	}

	public void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}
	
	public int getBrKoraka() {
		return brKoraka;
	}

	public void setBrKoraka(int brKoraka) {
		this.brKoraka = brKoraka;
	}

	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		zivotinja=null;
	}

	public void zgaziRupu() {
		if (zivotinja!=null) zivotinja.ispoljiEfekatUdarene();
	}
	
	public void stvoriNit() {
		nit=new Thread(this);
	}
	public boolean jePokrenuta() {
		return active;
	}
	
	public synchronized void pokreniNit() {
		try {
			if (jePokrenuta()) throw new Exception();
			active=true;
			nit.start();
		}
		catch(Exception e)
		{
			System.err.println("Vec je pokrenuta nit!");
		}
		
	}
	public synchronized void prekiniNit() {
		zivotinja=null;
		nit.interrupt();
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(new Color(130, 40, 40));
		if (zivotinja !=null) {
			zivotinja.iscrtaj(trenutniBrKoraka);
		}
	}
	
	@Override
	public void run() {
		//repaint();
		trenutniBrKoraka=0;
		try {
			while (!nit.isInterrupted()){
				Thread.sleep(100);
				zivotinja.iscrtaj(trenutniBrKoraka);
				trenutniBrKoraka++;
				if(trenutniBrKoraka>brKoraka) {
					Thread.sleep(2000);
					if (zivotinja!=null) zivotinja.ispoljiEfekatPobegle();
					break;
				}
			}
		}
		catch (InterruptedException e) { 
		}
		active=false;
		nit=null;
		repaint();
	}
}
