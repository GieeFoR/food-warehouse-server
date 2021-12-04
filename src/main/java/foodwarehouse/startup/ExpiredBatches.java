package foodwarehouse.startup;

import foodwarehouse.core.data.productInStorage.ProductInStorage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExpiredBatches {

    private final static List<ProductInStorage> expiredBatches = new LinkedList<>();

    public static void storeExpiredBatches(List<ProductInStorage> productsInStorages) {
        if(productsInStorages != null) expiredBatches.addAll(productsInStorages);
    }

    public static void removeExpiredBatches(ProductInStorage product) {
        expiredBatches.remove(product);
    }

    public static List<ProductInStorage> getExpiredBatches() {
        return Collections.unmodifiableList(expiredBatches);
    }

    public static void clear() {
        expiredBatches.clear();
    }
}
