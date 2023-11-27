package Factory;

import java.util.ArrayList;
import java.util.List;
import Models.Battery;
import Models.Component;
import Models.ElectricEngine;
import Models.MovementCaptor;
import Models.Product;
import Models.RemoteControlledCar;
import Models.RobotFollower;
import Models.SecurityAlarm;
import Models.SurveillanceDrone;

public class ProductFactory {
    ArrayList<Component> productComponents;

    public List<Product> createProducts(List<Product> filteredProducts, ArrayList<Component> components) {
        ArrayList<Product> optimizedProducts = new ArrayList<>();

        for (Product product : filteredProducts) { // Bouclez sur les produits filtrés
            List<String> requiredComponents = product.getRequiredComponants();
            this.productComponents = new ArrayList<Component>();

            if (product.checkRequiredComponents(components)) {
                for (String id : requiredComponents) {
                    components.stream()
                            .filter(composant -> composant.getId().equals(id))
                            .findFirst()
                            .ifPresent(composant -> {
                                productComponents.add(composant);
                                components.remove(composant);
                            });
                }

                switch (product.getNom()) {
                    case "Battery":
                        optimizedProducts.add(new Battery("Battery", "C", 5, 3, productComponents));
                        break;
                    case "MovementCaptor":
                        optimizedProducts.add(new MovementCaptor("MovementCaptor", "B", 10, 3, productComponents));
                        break;
                    case "ElectricEngine":
                        optimizedProducts.add(new ElectricEngine("ElectricEngine", "A", 5, 3, productComponents));
                        break;
                    case "SurveillanceDrone":
                        optimizedProducts
                                .add(new SurveillanceDrone("SurveillanceDrone", "E", 60, 12, productComponents));
                        break;
                    case "RemoteControlledCar":
                        optimizedProducts
                                .add(new RemoteControlledCar("RemoteControlledCar", "B", 30, 5, productComponents));
                        break;
                    case "RobotFollower":
                        optimizedProducts.add(new RobotFollower("RobotFollower", "B", 40, 6, productComponents));
                        break;
                    case "SecurityAlarm":
                        optimizedProducts.add(new SecurityAlarm("SecurityAlarm", "C", 20, 4, productComponents));
                        break;
                    default:
                        throw new IllegalArgumentException("Nom de produit inconnu: " + product.getNom());
                }
            }
        }
        return optimizedProducts;
    }
}
