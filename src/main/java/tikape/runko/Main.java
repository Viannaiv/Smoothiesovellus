package tikape.runko;

import java.util.*;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.dao.RaakaAineDao;
import tikape.runko.dao.SmoothieDao;
import tikape.runko.dao.SmoothieRaakaAineDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Smoothie;
import tikape.runko.domain.SmoothieRaakaAine;

public class Main {
//    Remember to trim names

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie.db");
        database.init();

        RaakaAineDao raDao = new RaakaAineDao(database);
        SmoothieDao sDao = new SmoothieDao(database);
        SmoothieRaakaAineDao sraDao = new SmoothieRaakaAineDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", sDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/raakaaineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raDao.findAll());
            
            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/raakaaineet", (req, res) -> { 
            String nimi = req.queryParams("nimi").trim();
            RaakaAine ra = new RaakaAine(-1, nimi);
            System.out.println("Lisätään raaka-aine " + nimi);
            raDao.saveOrUpdate(ra);
            
            res.redirect("/raakaaineet");
            return "";
        });
        
        Spark.get("/raakaaine/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            HashMap map = new HashMap<>();
            map.put("raakaaine", raDao.findOne(id));
            map.put("tilasto", sraDao.smoothiesContainingRaakaAine(id));
            
            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiet", (req, res) -> { 
            HashMap map = new HashMap<>();
            map.put("smoothiet", sDao.findAll());
            map.put("raakaaineet", raDao.findAllAlphabetically());
            
            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/raakaaine/delete", (req, res) -> { 
            int id = Integer.parseInt(req.queryParams("id"));
            System.out.println("Poistetaan raakaaine " + id);
            
            raDao.delete(id);
            sraDao.deleteWithRaakaAineID(id);
            
            res.redirect("/raakaaineet");
            return "";
        });
        
        Spark.post("/smoothie/delete", (req, res) -> { 
            int id = Integer.parseInt(req.queryParams("id"));
            System.out.println("Poistetaan smooothie " + id);
            
            sDao.delete(id);
            sraDao.deleteWithSmoothieID(id);
            
            res.redirect("/smoothiet");
            return "";
        });
        
        Spark.post("/smoothie/smoothieraakaaine/delete", (req, res) -> {
            int rID = Integer.parseInt(req.queryParams("raakaAineID"));
            int sID = Integer.parseInt(req.queryParams("smoothieID"));
            System.out.println("Poistetaan raaka-aine " + rID + " smoothiesta " + sID);
            
            sraDao.delete(rID, sID);
 
            res.redirect("/smoothie/" + sID);
            return "";
        });
        
        Spark.post("/create/smoothie", (req, res) -> {
            String nimi = req.queryParams("nimi").trim();
            Smoothie s = new Smoothie(-1, nimi);
            System.out.println("Luodaan smoothie " + nimi);
            sDao.saveOrUpdate(s);
            
            res.redirect("/smoothiet");
            return "";
        });
        
        Spark.post("/smoothiet/create/smoothieraakaaine", (req, res) -> {
            int smoothieID = Integer.parseInt(req.queryParams("smoothieID"));
            int raakaAineID = Integer.parseInt(req.queryParams("raakaAineID"));
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String maara = req.queryParams("maara").trim();
            String ohje = req.queryParams("ohje").trim();
            System.out.println("Luodaan smoothieraaka-aine " + raakaAineID +
                     " smoothieen " + smoothieID);
            
            SmoothieRaakaAine sra = new SmoothieRaakaAine(raakaAineID, smoothieID,
                    jarjestys, maara, ohje);
            sraDao.saveOrUpdate(sra);
            
            res.redirect("/smoothiet");
            return "";
        });
        
        Spark.post("/smoothie/create/smoothieraakaaine", (req, res) -> {
            int smoothieID = Integer.parseInt(req.queryParams("smoothieID"));
            int raakaAineID = Integer.parseInt(req.queryParams("raakaAineID"));
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String maara = req.queryParams("maara").trim();
            String ohje = req.queryParams("ohje").trim();
            
            System.out.println("Luodaan smoothieraaka-aine " + raakaAineID +
                    " smoothieen " + smoothieID);
            
            SmoothieRaakaAine sra = new SmoothieRaakaAine(raakaAineID, smoothieID,
                    jarjestys, maara, ohje);
            sraDao.saveOrUpdate(sra);
            
            res.redirect("/smoothie/" + smoothieID);
            return "";
        });
        
        Spark.get("/smoothie/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            HashMap map = new HashMap<>();
            map.put("smoothie", sDao.findOne(id));
            map.put("raakaaineet", raDao.findAll());
            map.put("smoothiet", sDao.findAll());
            map.put("raakaaineetjarjestetty", sraDao.RaakaAineListInOrderForSmoothie(id));
            
            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
        
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
