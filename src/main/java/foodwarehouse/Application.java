package foodwarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SpringBootApplication
public class Application {
    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
/*        Properties props = new Properties();
        props.put("user", "java");
        props.put("password", "java");
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.1.37:1521/orcl", props);
            System.out.println("Successfully connected!");
            try {
                jdbcTemplate.query("SELECT * FROM uzytkownik", (rs)-> {
                    System.out.println(rs.toString());});
                    connection.close();
                System.out.println("Successfully disconnected");
            } catch (SQLException e) {
                System.out.println("Failed to disconnect: " + e);
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect: " + e);
        }*/
    }

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
/*        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/university_management?serverTimezone=Europe/Warsaw");
        dataSource.setUsername("university_management");
        dataSource.setPassword("password");*/

        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@//192.168.1.110:1521/orcl");
        dataSource.setSchema("university_management");
        dataSource.setUsername("university_management");
        dataSource.setPassword("password");
        //dataSource.setFastConnectionFailoverEnabled(true);

//        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//        dataSource.setUrl("jdbc:oracle:thin:@//192.168.1.37:1521/orcl");
//        dataSource.setUsername("java");
//        dataSource.setPassword("java");



//        datasource.url= jdbc:oracle:thin:"@//192.168.1.37:1521/orcl";
//        datasource.username="java";
//        datasource.password="java";
//        datasource.driver-class-name=oracle.jdbc.OracleDriver;

        return dataSource;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
