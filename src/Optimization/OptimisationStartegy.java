package Optimization;

import Models.Product;
import java.util.List;

public interface OptimisationStartegy {
    List<Product> getTopProducts(List<Product> products);
}
