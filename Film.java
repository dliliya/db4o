/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liliya
 */
public class Film {
    private String name, director;
    private String country;
    private int year;
    private List Actors;
    
    
    
    public Film(String name, String director, String country, int year, List Actors) {
        this.name = name;
        this.director = director;
        this.country = country;
        this.year = year;
        this.Actors = Actors;
    }
    public Film(){}
    
    public Film(String name, String director, String country, int year) {
        this(name, director, country, year, new ArrayList());
    }

    public List getActors() {
        return Actors;
    }

    public void setActors(List Actors) {
        this.Actors = Actors;
    }
    
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public String getName() {
        return name;
    }
    public String toString() {
        return name+":"+"\n"+"    director: "+director+"\n"+"    country: "+country 
                +"\n"+"    year: "+year;
    }
}
