package foodwarehouse.database.statements;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class ReadStatement {

    public static String readInsert(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/java/foodwarehouse/database/statements/insert/"+fileName);
        return IOUtils.toString(file, StandardCharsets .UTF_8);
    }

    public static String readUpdate(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/java/foodwarehouse/database/statements/update/"+fileName);
        return IOUtils.toString(file, StandardCharsets .UTF_8);
    }

    public static String readDelete(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/java/foodwarehouse/database/statements/delete/"+fileName);
        return IOUtils.toString(file, StandardCharsets .UTF_8);
    }

    public static String readSelect(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/java/foodwarehouse/database/statements/select/"+fileName);
        return IOUtils.toString(file, StandardCharsets .UTF_8);
    }
}
