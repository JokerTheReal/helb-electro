package Models;

import java.util.ArrayList;

public class ElectricEngine extends Product {

    public ElectricEngine(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "lightsalmon", productComponents);
        this.addRequiredComponant("C3");
    }

    public int getPower() {
        Component component = super.getComposant("C3");
        if (component != null) {
            return ((ComponentC3) component).power;
        }

        return 0;
    }
}
