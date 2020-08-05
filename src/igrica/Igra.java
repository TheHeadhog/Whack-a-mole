package igrica;

import java.awt.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class Igra extends Frame implements ItemListener{
	
	public static volatile Igra igra;
	public static Igra dohvatiIgru() {
		if(igra==null) {
			synchronized (Igra.class) {
				if(igra==null)
					igra = new Igra();
			}
		}
		return igra;
	}
	
	public Basta basta = new Basta(4,4);
	Checkbox poljeZaTezinu[]=new Checkbox[3];
	Button dugme=new Button("Kreni");
	Label preostalo=new Label("Povrce: " +basta.getKolicinaPovrca());
	CheckboxGroup tezine=new CheckboxGroup();
	
	public Label getPreostalo() {
		return preostalo;
	}
	public void setPreostalo(Label preostalo) {
		this.preostalo = preostalo;
	}
	
	
	public void dodajKomponente(){
		Panel panel1 = new Panel(new GridLayout(10, 1));
		Dimension d = new Dimension(100, this.getHeight());
		panel1.setPreferredSize(d);
		Label tezinaLabel=new Label("Tezina:",Label.CENTER);
		Font fontslova=new Font(tezinaLabel.getName(),Font.BOLD,tezinaLabel.getHeight());
		tezinaLabel.setFont(fontslova);
		panel1.add(tezinaLabel);
		poljeZaTezinu[0]=new Checkbox("Lako",tezine,false);
		poljeZaTezinu[1]=new Checkbox("Srednje",tezine,true);
		poljeZaTezinu[2]=new Checkbox("Tesko",tezine,false);
		for (int i = 0; i < poljeZaTezinu.length; i++) poljeZaTezinu[i].addItemListener(this);
		for (int i = 0; i < poljeZaTezinu.length; i++) panel1.add(poljeZaTezinu[i]);
		dugme.addActionListener(new ActionListener(){
			{
		}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dugme.getLabel()=="Kreni") {
					
					basta.stvoriNit();
					basta.pokreniNit();
					basta.setKolicinaPovrca(100);
					preostalo.setText("Povrce: "+basta.getKolicinaPovrca());
					for (Checkbox checkbox : poljeZaTezinu) {
						checkbox.setEnabled(false);
					}
					dugme.setLabel("Stani");
				}
				else if (dugme.getLabel()=="Stani") {
					basta.prekiniNit();
					for (Checkbox checkbox : poljeZaTezinu) {
						checkbox.setEnabled(true);
					}
					dugme.setLabel("Kreni");
					setTezina();
				}
				
			}
		});
		panel1.add(dugme);
		preostalo.setFont(fontslova);
		panel1.add(preostalo);
		add(panel1,BorderLayout.EAST);

		basta.setLayout(new GridLayout(4, 4,20,20));
		basta.initRupa();
		basta.setBrojKoraka(8);
		basta.labela=preostalo;
		basta.dugme=dugme;
		basta.poljeZaTezinu=poljeZaTezinu;
		basta.setIntervalCekanja(750);
		add(basta);
		this.setVisible(true);

		
	}
	public Igra() {
		super("Igra");
		setSize(800,600);
		setResizable(true);
		setVisible(true);
		dodajKomponente();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {dohvatiIgru().basta.prekiniNit();dispose();}
		});
		repaint();
	}

	public static void main(String[] args) {
		dohvatiIgru();
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		setTezina();
	}
	
	private void setTezina() {
		Checkbox cekirano=tezine.getSelectedCheckbox();
		if (cekirano.equals(poljeZaTezinu[0])) {basta.setBrojKoraka(10); basta.setIntervalCekanja(1000);}
		if (cekirano.equals(poljeZaTezinu[1])) {basta.setBrojKoraka(8); basta.setIntervalCekanja(750);}
		if (cekirano.equals(poljeZaTezinu[2])) {basta.setBrojKoraka(6); basta.setIntervalCekanja(500);}
	}
}


//.(abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
