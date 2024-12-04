package com.ns01.ns01.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodReceipeModel{

 
public String Name;
public String url;
public String Description;
public String Author;
public List<String> Ingredients;
public List<String> Method;

public String getUrl() {
    return url;
}
public void setUrl(String url) {
    this.url = url;
}
public String getName() {
    return Name;
}
public void setName(String name) {
    Name = name;
}
public String getDescription() {
    return Description;
}
public void setDescription(String description) {
    Description = description;
}
public String getAuthor() {
    return Author;
}
public void setAuthor(String author) {
    Author = author;
}
public List<String> getIngredients() {
    return Ingredients;
}
public void setIngredients(List<String> ingredients) {
    Ingredients = ingredients;
}
public List<String> getMethod() {
    return Method;
}
public void setMethod(List<String> method) {
    Method = method;
}
@Override
public String toString() {
    return "FoodReceipeModel [Name=" + Name + ", url=" + url + ", Description=" + Description + ", Author=" + Author
            + ", Ingredients=" + Ingredients + ", Method=" + Method + "]";
}








}