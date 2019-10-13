package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3,max=15,message = "Name should be between 3 and 15 characters.")
    private String name;

    @ManyToMany
    List<Cheese> cheeses;

    public int getId(){
        return id;
    }

    public void  setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addItem(Cheese item){this.cheeses.add(item);}

    public List<Cheese> getCheeses(){return cheeses;}

    public Menu(){

    }
    public Menu(String name){
        this.name = name;
    }

}