package foodwarehouse.startup;

import foodwarehouse.core.data.product.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class RunningOutProducts {

    private final static List<Product> runningOutProducts = new LinkedList<>();

    public static void storeRunningOutProducts(List<Product> products) {
        runningOutProducts.addAll(products);
    }

    public static void removeRunningOutProducts(Product product) {
        runningOutProducts.remove(product);
    }

    public static List<Product> getRunningOutProducts() {
        return Collections.unmodifiableList(runningOutProducts);
    }

    public static void clear() {
        runningOutProducts.clear();
    }
}
