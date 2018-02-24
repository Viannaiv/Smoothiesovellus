package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();
//laita tänne sqlitekomennot niin toimii.
        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        
//        Luodaan tarvittavat SQLITE komennot
//        lista.add("CREATE TABLE RaakaAine(id integer PRIMARY KEY, nimi varchar(200));");
//        lista.add("CREATE TABLE Smoothie (id integer PRIMARY KEY, nimi varchar(200));");
//        lista.add("CREATE TABLE SmoothieRaakaAine (raaka_aine_id integer, smoothie_id integer,"
//                + " jarjestys integer, maara varchar(100), ohje varchar(200), "
//                + "FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine(id), "
//                + "FOREIGN KEY (smoothie_id) REFERENCES Smoothie(id));");
//        Lisää tieto tauluihin testaamiseksi
        
        

        return lista;
    }
}
