package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws Exception{
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = postgreLauseet();

        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }
            
            st.close(); // Always a good idea to close these when they are no longer needed.

        } catch (Throwable t) {
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
                
        lista.add("CREATE TABLE RaakaAine (id SERIAL PRIMARY KEY, nimi varchar(30));");
        lista.add("CREATE TABLE Smoothie (id SERIAL PRIMARY KEY, nimi varchar(30));");
        lista.add("CREATE TABLE SmoothieRaakaAine (raaka_aine_id integer, smoothie_id integer,"
                + " jarjestys integer, maara varchar(30), ohje varchar(100), "
                + "FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine(id), "
                + "FOREIGN KEY (smoothie_id) REFERENCES Smoothie(id));");

        return lista;
    }
}
