package io.khasang.eshop.model;

//POJO
public class Cat implements CatInterface {

    private String name;

    public Cat() {

    }

    public Cat(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }
}
