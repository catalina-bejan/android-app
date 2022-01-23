package com.example.museumhelper;

public class ArtMovement {
    long id;
    String name;

    public ArtMovement(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ArtMovement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
