package Optimization;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Models.Product;

public class EcoScoreOptimisation implements OptimisationStartegy {

    @Override
    public List<Product> getTopProducts(List<Product> products) {
        return products.stream().sorted(Comparator.comparing(Product::getEcoScore)).limit(3)
                .collect(Collectors.toList());
    }

}
