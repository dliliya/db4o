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
public class Actor extends Person{
    private int number_of_films, year_oskar;
    private boolean has_oskar; 
    private List films;
   
    public Actor(List films_,String name, String sex, String country, int age, int countOfChildren, int number_of_films_, boolean has_oskar_,int year_oskar_) {
        super(name, sex, country, age, countOfChildren);
        number_of_films = number_of_films_;
        has_oskar = has_oskar_;
        films = films_;
        year_oskar = year_oskar_;
    }
    public Actor(String name, String sex, String country, int age, int countOfChildren, int number_of_films_, boolean has_oskar_,int year_oskar_) {
        this(new ArrayList(),name, sex, country, age, countOfChildren, number_of_films_, has_oskar_,year_oskar_);        
    }
    public Actor(String name, String sex, String country, int age, int countOfChildren, int number_of_films_, boolean has_oskar_) {
        this(new ArrayList(),name, sex, country, age, countOfChildren, number_of_films_, has_oskar_,1000);        
    }
    public Actor(List films_,String name, String sex, String country, int age, int countOfChildren, int number_of_films_, boolean has_oskar_) {
        this(films_,name, sex, country, age, countOfChildren, number_of_films_, has_oskar_,1000);     
    }  
    public Actor(){
        super("", "", "", 0, 0);
    }
    
    public String toString() {
        return super.getName()+":"+"\n"+"    sex: "+sex+"\n"+"    country: "+country
                +"\n"+"    age: "+age+"\n"
                +"    number_of_films: "+number_of_films 
                +"\n"+"    has_oskar: "+has_oskar+"\n"
                +"    year_oskar: "+year_oskar+"\n"
                +"    countOfChildren: "+countOfChildren;
    }

    public List getFilms() {
        return films;
    }

   
    public void setFilms(List films) {
        this.films = films;
    }

    public int getNumber_of_films() {
        return number_of_films;
    }

    public void setNumber_of_films(int number_of_films) {
        this.number_of_films = number_of_films;
    }

    public boolean isHas_oskar() {
        return has_oskar;
    }

    public void setHas_oskar(boolean has_oskar) {
        this.has_oskar = has_oskar;
    }

    public int getYear_oskar() {
        return year_oskar;
    }

    public void setYear_oskar(int year_oskar) {
        this.year_oskar = year_oskar;
    }
   
}
