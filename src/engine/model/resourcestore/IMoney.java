package engine.model.resourcestore;
/**
 * Wrapper class for in game "money"
 *
 */
public interface IMoney {
	public int getValue();
	public void updateValue(int deltaValue);
	public boolean isLessThan(IMoney money);
}
