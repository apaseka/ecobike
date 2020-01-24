package org.qualityunit.model;

import java.util.Objects;

public abstract class Bike implements Comparable<Bike>, Writable {

    private BikeType type;
    private String brand;
    private Integer weight;
    private Boolean lightsAvailable;
    private String color;
    private Integer price;

    public Bike(BikeType type, String brand, Integer weight, Boolean lightsAvailable, String color, Integer price) {
        this.type = type;
        this.brand = brand;
        this.weight = weight;
        this.lightsAvailable = lightsAvailable;
        this.color = color;
        this.price = price;
    }

    public String getBrand() {
        return brand == null ? brand : brand.toUpperCase();
    }

    public String brand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean isLightsAvailable() {
        return lightsAvailable;
    }

    public void setLightsAvailable(Boolean lightsAvailable) {
        this.lightsAvailable = lightsAvailable;
    }

    public String getColor() {
        return color == null ? color : color.toUpperCase();
    }

    public String color() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return type == bike.type &&
                Objects.equals(brand, bike.brand) &&
                Objects.equals(weight, bike.weight) &&
                Objects.equals(lightsAvailable, bike.lightsAvailable) &&
                Objects.equals(color, bike.color) &&
                Objects.equals(price, bike.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand, weight, lightsAvailable, color, price);
    }

    @Override
    public String toString() {
        return (lightsAvailable ? "head/tail light." : "no head/tail light.") + "\nPrice: " + price + " euros.";
    }

    @Override
    public int compareTo(Bike o) {
        return brand.toUpperCase().compareTo(o.brand.toUpperCase());
    }
}
