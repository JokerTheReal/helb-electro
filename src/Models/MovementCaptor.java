package Models;

import java.util.ArrayList;

public class MovementCaptor extends Product {

    public MovementCaptor(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "azure", productComponents);
        this.addRequiredComponant("C2");
    }

    public String getColorOfComposant() {
        Component component = super.getComposant("C2");
        if (component != null) {
            return ((ComponentC2) component).color;
        }

        return "";
    }

    public int getDistance() {
        Component component = super.getComposant("C2");
        if (component != null) {
            return ((ComponentC2) component).distance;
        }

        return 0;
    }
}
