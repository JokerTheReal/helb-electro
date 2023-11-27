package Models;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    private List<Product> products;

    private static ProductData instance;

    private ProductData() {
        products = new ArrayList<>();

        // Ajoutez vos produits ici
        products.add(new Battery("Battery", "C", 5, 3, null));
        products.add(new MovementCaptor("MovementCaptor", "B", 10, 3, null));
        products.add(new ElectricEngine("ElectricEngine", "A", 15, 3, null));
        products.add(new SecurityAlarm("SecurityAlarm", "C", 20, 4, null));
        products.add(new RemoteControlledCar("RemoteControlledCar", "B", 30, 5, null));
        products.add(new RobotFollower("RobotFollower", "B", 40, 6, null));
        products.add(new SurveillanceDrone("SurveillanceDrone", "E", 60, 12, null));
    }

    public static ProductData getInstance() {
        if (instance == null) {
            instance = new ProductData();
        }
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }
}
