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
public class Actor {
    private String name, sex;
    private String country, city;
    private int age, number_of_films;
    private boolean has_oskar; 
    private List films;

    public Actor(List films, String name, String sex, String country, String city, int age, int number_of_films, boolean has_oskar) {
        this.name = name;
        this.sex = sex;
        this.country = country;
        this.city = city;
        this.age = age;
        this.number_of_films = number_of_films;
        this.has_oskar = has_oskar;
        this.films = films;
    }
    
    public Actor(String name, String sex, String country, String city, int age, int number_of_films, boolean has_oskar) {
        this(new ArrayList(), name, sex, country, city, age, number_of_films, has_oskar);
    }
    
    public Actor(){}
    
    public String toString() {
        return name+":"+"\n"+"    sex: "+sex+"\n"+"    country: "+country 
                +"\n"+"    city: "+city+"\n"+"    age: "+age+"\n"
                +"    number_of_films: "+number_of_films 
                +"\n"+"    has_oskar: "+has_oskar;
    }
     
    public List getFilms() {
        return films;
    }

    public void setFilms(List films) {
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
   
}
