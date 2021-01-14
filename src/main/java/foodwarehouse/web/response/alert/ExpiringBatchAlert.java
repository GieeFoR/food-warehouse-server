package foodwarehouse.web.response.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.productInStorage.ProductInStorage;

import java.text.SimpleDateFormat;


public record ExpiringBatchAlert(
        @JsonProperty(value = "content", required = true)           String content,
        @JsonProperty(value = "batch_id", required = true)          int batchId,
        @JsonProperty(value = "product_id", required = true)        int productId) {

    public static ExpiringBatchAlert fromProductInStorage(ProductInStorage expiredBatch) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ExpiringBatchAlert(
                String.format("Kończy się termin przydatności do spożycia produktu \"%s\". " +
                        "Dnia %s mija data ważności partii produktu o numerze %d oraz id %d.",
                        expiredBatch.batch().product().name(),
                        sdf.format(expiredBatch.batch().eatByDate()),
                        expiredBatch.batch().batchNumber(),
                        expiredBatch.batch().batchId()),
                expiredBatch.batch().batchId(),
                expiredBatch.batch().product().productId());
    }
}
