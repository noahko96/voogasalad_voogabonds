package authoring.authoring_interfaces;

import javafx.geometry.Point2D;

/**
 * This interface is responsible for providing the controller access to the tower's traits entered in by the user.
 * @author Niklas Sjoquist
 * @author Christopher Lu
 * 
 */
public interface IEnemy {

        /**
         * Gets the speed of an enemy. 
         * @return This value describes how fast the enemy moves along its path.
         */
        public double getSpeed();

        /**
         * Gets the spawn point of an enemy. 
         * @return This will be the point at which this enemy enters the map.
         */
        public Point2D getSpawnPoint();

        /**
         * Gets the end point of an enemy. 
         * @return This will be the point at which this enemy will move towards to exit the map.
         */
        public Point2D getEndPoint();

}