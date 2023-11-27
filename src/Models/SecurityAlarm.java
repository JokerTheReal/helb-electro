package Models;

import java.util.ArrayList;

public class SecurityAlarm extends Product {

    public SecurityAlarm(String nom, String ecoScore, int prix, int duree, ArrayList<Component> productComponents) {
        super(nom, ecoScore, prix, duree, "aqua", productComponents);
        this.addRequiredComponant("C1");
        this.addRequiredComponant("C2");
    }

}
