package authoring.model;

public class EnemyData extends MachineData {
	private int killReward;
	
	public int getKillReward(){
		return killReward;
	}
	public void setKillReward(int killReward){
		this.killReward = killReward;
	}
}