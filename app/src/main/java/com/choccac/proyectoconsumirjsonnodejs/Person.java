package com.choccac.proyectoconsumirjsonnodejs;

/**
 * Created by Neksze on 02/10/2015.
 */
public class Person {

    private String Name;
    private String City;
    private String Country;


    Person(){

    }

    Person(String Name, String City, String Country){
        this.Name = Name;
        this.City = City;
        this.Country = Country;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getName() {
        return Name;
    }
}
