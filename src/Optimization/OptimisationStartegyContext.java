package Optimization;

import Models.Component;
import Models.ProductData;
import Models.Product;
import java.util.ArrayList;
import java.util.List;
import Factory.ProductFactory;

public class OptimisationStartegyContext {

    private ProductFactory productFactory;
    OptimisationStartegy optimisationFilter;

    public OptimisationStartegyContext() {
        this.productFactory = new ProductFactory();
    }

    public List<Product> optimize(String choice, ArrayList<Component> components) {
        List<Product> products = ProductData.getInstance().getProducts();

        switch (choice) {
            case "Time":
                optimisationFilter = new TimeOptimisation();
                break;
            case "Price":
                optimisationFilter = new PriceOptimisation();
                break;
            case "Eco-score":
                optimisationFilter = new EcoScoreOptimisation();
                break;
            case "Mix":
                optimisationFilter = new DiversityOptimisation();
                break;
            default:
                throw new IllegalArgumentException("Filter not recognized: " + choice);
            // Factory doit créer et pour ça elle récupère la strategy
        }

        List<Product> filteredProducts = optimisationFilter.getTopProducts(products); // Utiliser le filtre ici
        return productFactory.createProducts(filteredProducts, components);
    }
}