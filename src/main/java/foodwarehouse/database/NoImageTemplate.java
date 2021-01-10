package foodwarehouse.database;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class NoImageTemplate {

    public static String image() {
        String result = "";
        Path fileName = Path.of("C:\\Users\\GieeF\\IdeaProjects\\projekt-java-server\\src\\main\\java\\foodwarehouse\\database\\image.txt");
        try {
            result = Files.readString(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
