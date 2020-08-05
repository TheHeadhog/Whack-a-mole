package igrica;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

@SuppressWarnings("serial")
public class Basta extends Panel implements Runnable {

	private int brojVrsta, brojKolona;
	private int kolicinaPovrca;
	private int intervalCekanja;
	private int brojKoraka;
	private Rupa[][] rupe;
	Label labela;
	Button dugme;
	Checkbox poljeZaTezinu[];
	public Thread nit;   //promeni u private
	
	public int getKolicinaPovrca() {
		return kolicinaPovrca;
	}

	public void setKolicinaPovrca(int kolicinaPovrca) {
		this.kolicinaPovrca = kolicinaPovrca;
	}

	public int getBrojKoraka() {
		return brojKoraka;
	}

	public void setBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (Rupa[] rupas : rupe) {
			for (Rupa rupa : rupas) {
				rupa.setBrKoraka(brojKoraka);
			}	
		}
	}
	
	public void setIntervalCekanja(int intervalCekanja) {
		this.intervalCekanja = intervalCekanja;
	}

	public Basta(int brojVrsta, int brojKolona) {
		super();
		this.brojVrsta = brojVrsta;
		this.brojKolona = brojKolona;
		kolicinaPovrca=100;
		rupe= new Rupa[brojVrsta][brojKolona];
	}

	public void initRupa() {
		for (int i = 0; i < brojVrsta; i++)
			for (int j = 0; j < brojKolona; j++)
			{
				rupe[i][j]=new Rupa(this);
				rupe[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (!(((Rupa)e.getSource()).getZivotinja()==null)) {
							((Rupa)e.getSource()).zgaziRupu();
						}
						
					}
				});
				this.add(rupe[i][j]);
			}
	
	}

	public void pojediPovrce() {
		kolicinaPovrca--;
		labela.setText("Povrce: "+kolicinaPovrca);
	}
	@Override
	public void paint(Graphics g) {
		setBackground(Color.GREEN);
	}
	@Override
	public void run() {
		int ovajinterval=intervalCekanja;
		Random rand = new Random(System.nanoTime());
		try {
			while(!nit.isInterrupted()) {
				if (kolicinaPovrca<=0) prekiniNit();
				int vrst=rand.nextInt(4);
				int kol=rand.nextInt(4);
				if(rupe[vrst][kol].jePokrenuta()) continue;
				else {
					rupe[vrst][kol].setZivotinja(new Krtica(rupe[vrst][kol]));
					rupe[vrst][kol].stvoriNit();
					rupe[vrst][kol].pokreniNit();
					Thread.sleep(ovajinterval);
					int umanjenje=ovajinterval/100;
					if(umanjenje==0) umanjenje=1;
					ovajinterval-=umanjenje;
				}
				
			}
		}
		catch (InterruptedException e) {
			
		}
		finally {
			for (Rupa[] rupas : rupe) {
				for (Rupa rupa : rupas) {
					if (rupa.jePokrenuta()) rupa.prekiniNit();
				}
			}
			labela.setText("Povrce: "+kolicinaPovrca);
			dugme.setLabel("Kreni");
			for (Checkbox checkbox : poljeZaTezinu) {
				checkbox.setEnabled(true);
			}
		}
	}

	public void prekiniNit() {
		if (nit!=null) nit.interrupt();
	}
	public void pokreniNit() {
		if (nit!=null) nit.start();
	}
	public void stvoriNit() {
		nit= new Thread(this);
	}
	
}
