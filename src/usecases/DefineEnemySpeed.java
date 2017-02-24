package usecases;

public class DefineEnemySpeed {
	
	public DefineEnemySpeed(String name, double speed){
		MockEnemy enemy = new MockEnemy(name);
		enemy.setSpeed(speed);
	}

}
