package Models;

import java.util.ArrayList;

public class Battery extends Product {

    public Battery(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "lightgreen", productComponents);
        this.addRequiredComponant("C1");
    }

    public double getPourcentOfBattery() {
        Component component = super.getComposant("C1");
        if (component != null) {
            return ((ComponentC1) component).pourcentOfBattery;
        }

        return 0;
    }
}