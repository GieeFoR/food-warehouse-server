package foodwarehouse.core.product;

import foodwarehouse.core.maker.Maker;

public record Product(
        int productId,
        Maker maker,
        String name,
        String category,
        boolean needColdStorage,
        float buyPrice,
        float sendPrice) {
}
