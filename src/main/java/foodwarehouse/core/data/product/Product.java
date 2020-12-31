package foodwarehouse.core.data.product;

import foodwarehouse.core.data.maker.Maker;

public record Product(
        int productId,
        Maker maker,
        String name,
        String category,
        boolean needColdStorage,
        float buyPrice,
        float sendPrice) {
}
