package Zrayouil.controle_project;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Zrayouil.controle_project.Service;

public class Empolye {
    private String nom;
    private String prenom;
    private Date date;
    private String service;
    private String photo;

    public  Empolye(){ }
    public Empolye(String nom, String prenom, String date, String service, String photo) {
        this.nom = nom;
        this.prenom = prenom;
        // Convert the date string to a Date object
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.service = service;
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
