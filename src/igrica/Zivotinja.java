package igrica;

public abstract class Zivotinja {
	protected Rupa rupa;

	public Zivotinja(Rupa rupa) {
		super();
		this.rupa = rupa;
	}
	
	public abstract void ispoljiEfekatPobegle();
	public abstract void ispoljiEfekatUdarene();
	public abstract void iscrtaj(int korak);
}
