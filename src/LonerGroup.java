import java.util.List;

public class LonerGroup {

	private Loaner loaner1;
	private Loaner loaner2;
	private Loaner loaner3;
	
	public LonerGroup (Loaner loaner1, Loaner loaner2, Loaner loaner3) {
		this.loaner1 = loaner1;
		this.loaner2 = loaner2;
		this.loaner3 = loaner3;
	}
	
	public LonerGroup (List<Loaner> loaner) {
		this.loaner1 = loaner.get(0);
		this.loaner2 = loaner.get(1);
		this.loaner3 = loaner.get(2);
	}
	
	public Loaner getLoaner(int position) {
		switch(position){
			case 1 : return loaner1;
			case 2 : return loaner2;
			case 3 : return loaner3;
			default : return null;
		}
	}
}
