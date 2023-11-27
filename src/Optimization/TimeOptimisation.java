package Optimization;

import Models.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TimeOptimisation implements OptimisationStartegy {

    @Override
    public List<Product> getTopProducts(List<Product> products) {
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparingInt(Product::getDuration))
                .collect(Collectors.toList());

        List<Product> topProducts = new ArrayList<>();
        Product lastAdded = null;
        int sameDurationCount = 0;

        for (Product product : sortedProducts) {
            if (lastAdded == null || product.getDuration() != lastAdded.getDuration()) {
                topProducts.add(product);
                lastAdded = product;
                sameDurationCount = 1; // Reset the count when a product with different duration is added
            } else if (product.getDuration() == lastAdded.getDuration() && sameDurationCount < 2) {
                topProducts.add(product);
                sameDurationCount++;
            }
            if (topProducts.size() == 3) {
                break;
            }
        }
        return topProducts;
    }
}
