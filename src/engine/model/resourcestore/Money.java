package engine.model.resourcestore;

/**
 * An implementation of the IMoney interface, one kind of currency.
 * @author owenchung
 *
 */
public class Money implements IMoney, Comparable<Money>{
	private int myValue;
	
	
	public Money(int value) {
		// TODO Auto-generated constructor stub
		myValue = value;
	}

	@Override
	public int getValue() {
		return myValue;
	}

	@Override
	public void updateValue(int deltaValue) {
		myValue = myValue + deltaValue;
		
	}
	@Override
	public boolean equals(Object obj){
		return myValue == ((Money) obj).getValue();
		
	}

	@Override
	public int compareTo(Money money) {
		return myValue - money.getValue();
	}
	@Override
	public boolean isLessThan(IMoney money){
		return (myValue - money.getValue()) < 0;
			
	}

}
