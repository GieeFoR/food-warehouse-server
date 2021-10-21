package foodwarehouse;

import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

//        try (Stream<Path> paths = Files.walk(Paths.get("src/main/java/foodwarehouse/database/createTableStatements"))) {
//            paths
//                    .filter(Files::isRegularFile)
//                    .forEach((filePath) -> {
//                        try {
//                            loadData(dataSource, filePath);
//                        } catch (SQLException | FileNotFoundException throwables) {
//                            throwables.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try (Stream<Path> paths = Files.walk(Paths.get("src/main/java/foodwarehouse/database/constraintStatements"))) {
//            paths
//                    .filter(Files::isRegularFile)
//                    .forEach((filePath) -> {
//                        try {
//                            loadData(dataSource, filePath);
//                        } catch (SQLException | FileNotFoundException throwables) {
//                            throwables.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return dataSource;
    }

//    private void loadData(DataSource dataSource, Path filePath) throws SQLException, FileNotFoundException {
//        FileInputStream file = new FileInputStream(filePath.toString());
//
//        Connection con = dataSource.getConnection();
//        Statement statement = con.createStatement();
//        statement.executeUpdate(IOUtils.toString(file, StandardCharsets.UTF_8));
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
