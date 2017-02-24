package usecases;

import authoring.authoring_interfaces.IWeapon;

public class MockWeapon implements IWeapon {
    private String myName;
    private double myDamage;
    
    public MockWeapon(String name) {
        myName = name;
    }
    
    public MockWeapon(String name, double damage) {
        myName = name;
        myDamage = damage;
    }
    
    public void setDamage(double damage) {
        myDamage = damage;
    }

    @Override
    public String getName () {
        return myName;
    }

    @Override
    public Double getEffectAmount () {
        return myDamage;
    }

    // The rest of these methods are not implemented for the sake of simplifying this use case
    // An actual weapon would implement these as well
    
    @Override
    public String getType () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getRange () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStrategy () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getFireRate () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getAccuracy () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getRank () {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
