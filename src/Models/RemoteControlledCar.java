package Models;

import java.util.ArrayList;

public class RemoteControlledCar extends Product {

    public RemoteControlledCar(String nom, String ecoScore, int prix, int duree,
            ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "plum", productComponents);
        this.addRequiredComponant("C1");
        this.addRequiredComponant("C3");

    }

}
