package foodwarehouse.startup;

import foodwarehouse.core.data.productInStorage.ProductInStorage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope("application")
public class ExpiredBatches {

    List<ProductInStorage> expiredBatches = new LinkedList<>();

    public void storeExpiredBatches(ProductInStorage productInStorage) {
        expiredBatches.add(productInStorage);
    }

    public List<ProductInStorage> getExpiredBatches() {
        return Collections.unmodifiableList(expiredBatches);
    }
}
