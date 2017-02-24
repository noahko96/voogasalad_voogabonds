package usecases;

/**
 * This class shows the use case of defining weapon damage. 
 * The user would input a value for the damage, 
 * then the frontend would create a MockWeapon object, 
 * which would be sent to the backend through the controller.
 * 
 * @author Niklas Sjoquist
 * 
 */
public class DefineWeaponDamage {
    private MockWeapon myWeapon;

    public DefineWeaponDamage(double damage) {
        myWeapon = new MockWeapon("Gun");
        myWeapon.setDamage(damage);
    }
    
}
