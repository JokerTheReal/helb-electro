package Optimization;

import java.util.*;
import Models.Product;

public class DiversityOptimisation implements OptimisationStartegy {
    private Map<String, Integer> productBuildCount = new HashMap<>();

    @Override
    public List<Product> getTopProducts(List<Product> products) {
        List<Product> availableProducts = new ArrayList<>(products);
        List<Product> chosenProducts = new ArrayList<>();

        availableProducts.sort(Comparator.comparingInt(product -> productBuildCount.getOrDefault(product.getNom(), 0)));

        for (Product product : availableProducts) {
            chosenProducts.add(product);

            productBuildCount.put(product.getNom(), productBuildCount.getOrDefault(product.getNom(), 0) + 1);

            if (chosenProducts.size() == products.size()) {
                break;
            }
        }

        return chosenProducts;
    }
}
