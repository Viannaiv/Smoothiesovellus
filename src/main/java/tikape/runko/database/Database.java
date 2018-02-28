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
//laita tänne sqlitekomennot niin toimii. muuta postgre muotoon lopuksi.
        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        
//        Luodaan tarvittavat SQLITE komennot
                
        lista.add("CREATE TABLE RaakaAine(id integer PRIMARY KEY, nimi varchar(30));");
//        lista.add("CREATE TABLE Smoothie (id integer PRIMARY KEY, nimi varchar(30));");
//        lista.add("CREATE TABLE SmoothieRaakaAine (raaka_aine_id integer, smoothie_id integer,"
//                + " jarjestys integer, maara varchar(20), ohje varchar(150), "
//                + "FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine(id), "
//                + "FOREIGN KEY (smoothie_id) REFERENCES Smoothie(id));");

//        Lisää tieto tauluihin testaamiseksi
        
//        lista.add("INSERT INTO Smoothie (id, nimi) VALUES (1, 'Avokado-kiivismoothie');");
//        lista.add("INSERT INTO RaakaAine (id, nimi) VALUES (1, 'Avokado');");
//        lista.add("INSERT INTO RaakaAine (id, nimi) VALUES (2, 'Kiivi');");
//        lista.add("INSERT INTO RaakaAine (id, nimi) VALUES (3, 'Omenamehu');");
//        lista.add("INSERT INTO RaakaAine (id, nimi) VALUES (4, 'Hunaja');");
//        lista.add("INSERT INTO SmoothieRaakaAine "
//                + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES "
//                + "(1, 1, 1, '1 kpl', 'Halkaise avokado ja poista kivi');");
//        lista.add("INSERT INTO SmoothieRaakaAine "
//                + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES "
//                + "(2, 1, 2, '3 kpl', 'Kuori');");
//        lista.add("INSERT INTO SmoothieRaakaAine "
//                + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES "
//                + "(3, 1, 3, '2 - 3 dl', '');");
//        lista.add("INSERT INTO SmoothieRaakaAine "
//                + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES "
//                + "(4, 1, 4, '2 rkl', '');");
//        

        return lista;
    }
}
