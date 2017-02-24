package utility;
/**
 * 
 * @author owenchung
 *
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1, T2> {
	T1 myP1; 
	T2 myP2;
	
	public T1 getMyP1() {
		return myP1;
	}

	public T2 getMyP2() {
		return myP2;
	}

	Pair(T1 p1, T2 p2) {
		myP1 = p1;
		myP2 = p2;
	}
	
	
	
}
