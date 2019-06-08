import java.io.Serializable;

public class Obj implements Serializable {
	private static final long serialVersionUID = 1L;
	int x;
	String y;
	
	@Override
	public String toString() {
		return x + y;
	}
}