package usecases;

public class DefineTowerSell {
	
	private MockTower myTower;
	double myMoney;
	
	public DefineTowerSell(double money){
		double myMoney=money;
		MockTower myTower= new MockTower();
	}
	
	public void towerSell(){
		double towerValue =myTower.getCost();
		myMoney=myMoney+towerValue;
		//Update image
	}
	
}
