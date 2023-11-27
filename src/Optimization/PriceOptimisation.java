package Optimization;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import Models.Product;

public class PriceOptimisation implements OptimisationStartegy {

    @Override
    public List<Product> getTopProducts(List<Product> products) {
        return products.stream().sorted(Comparator.comparingInt(Product::getPrice).reversed()).limit(2)
                .collect(Collectors.toList());
    }

}