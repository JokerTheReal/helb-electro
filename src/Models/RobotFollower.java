package Models;

import java.util.ArrayList;

public class RobotFollower extends Product {

    public RobotFollower(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "bisque", productComponents);
        this.addRequiredComponant("C2");
        this.addRequiredComponant("C3");

    }

}
