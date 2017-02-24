package usecases;

public class DefineLoss {
    
    private int lives;
    
    public DefineLoss(){
    }
    
    public int checkLives(){
        return lives;
    }
    
    public boolean playerLose(){
        if(checkLives() == 0){
            return true;
        }
        else
            return false;
    }
    
    public void lostLife(MockEnemy enemy){
        if(enemy.atEnd())
            lives = lives-1;
        else
        {}
    }
}       
