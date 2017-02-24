package authoring.authoring_interfaces;

/**
 * Represents any machine that can be created in the game, such as towers and enemies, and provides controller access to common instance variables. 
 * @author Niklas Sjoquist
 *
 */
public interface IMachine {

    /**
     * Gets the name of an enemy.
     */
    public String getName();

    /**
     * Gets the health of an enemy.
     * This value will decrement when the enemy is hit by a weapon, and will cause the enemy to die when it drops to 0 or below.
     */
    public double getHealth();

    /**
     * Gets the weapon of the machine. 
     * This object will be used to attack other machines, i.e., enemies or towers.
     */
    public IWeapon getWeapon();

    /**
     * Gets the type of the machine. 
     * This object will include information such as ground/air, healing/destructive, etc.
     */
    public String getType();

    /**
     * Gets the image path of the machine. 
     * This will be its visual representation in the game.
     */
    public String getImage();

    /**
     * Gets the sound path of the machine. 
     * This will be the noise that this machine makes in the game.
     */
    public String getSound();
    
}
