package Models;

import java.util.ArrayList;

public class SurveillanceDrone extends Product {

    public SurveillanceDrone(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "hotpink", productComponents);
        this.addRequiredComponant("C1");
        this.addRequiredComponant("C2");
        this.addRequiredComponant("C3");
    }

}
