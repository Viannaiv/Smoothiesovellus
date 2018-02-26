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

public class Main {
//    Remember to trim names

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie.db");
        database.init();

        RaakaAineDao raDao = new RaakaAineDao(database);
        SmoothieDao sDao = new SmoothieDao(database);
        SmoothieRaakaAineDao sraDao = new SmoothieRaakaAineDao(database);
        
//        tarvitaan / /smoothie /raakaaineet smoothie+smoothieid ja 
//          smoothiet ja raakaaine jossa tilasto /delete/ useampi.

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
            RaakaAine ra = new RaakaAine(0, nimi);
            System.out.println("Lisätään raaka-aine " + nimi);
            raDao.saveOrUpdate(ra);
            
            res.redirect("/raakaaineet");
            return "";
        });
        
        Spark.post("/raakaaine/delete", (req, res) -> { 
            int id = Integer.parseInt(req.queryParams("id"));
            System.out.println("Poistetaan raakaaine " + id);
            
            raDao.delete(id);
            sraDao.deleteWithRaakaAineID(id);
            
            res.redirect("/raakaaineet");
            return "";
        });
        
        
        

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
