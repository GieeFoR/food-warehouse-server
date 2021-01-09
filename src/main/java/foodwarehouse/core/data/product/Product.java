package foodwarehouse.core.data.product;

import foodwarehouse.core.data.maker.Maker;

import java.sql.Blob;

public record Product(
        int productId,
        Maker maker,
        String name,
        String shortDesc,
        String longDesc,
        String category,
        boolean needColdStorage,
        float buyPrice,
        float sellPrice,
        String image) {
}
