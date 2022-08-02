package databaseRepository;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    private PropertiesConfiguration databaseProperties = new PropertiesConfiguration();

    public DatabaseManager(){
        this.setUpDatabase();
    }

    private void setUpDatabase() {
        try{
            databaseProperties.load("database.properties");
            String username = databaseProperties.getString("database.username");
            String password = databaseProperties.getString("database.password");
            String host = databaseProperties.getString("database.host");
            String port = databaseProperties.getString("database.port");
            String databaseName = databaseProperties.getString("database.databaseName");

            String connectionUrl = host + ":" + port + "/" + databaseName;

            connection = DriverManager.getConnection(connectionUrl, username, password);

        } catch (SQLException | ConfigurationException exception){
            System.out.println("Error occurred during connection to mysql");
            exception.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }


}
