package org.qualityunit.model;

import java.util.Objects;

public class FoldingBike extends Bike {

    private Integer wheelSize;
    private Integer gearsNumber;

    public FoldingBike(String brand, Integer weight, Boolean lightsAvailable, String color, Integer price, Integer wheelSize, Integer gearsNumber) {
        super(BikeType.F_BIKE, brand, weight, lightsAvailable, color, price);
        this.wheelSize = wheelSize;
        this.gearsNumber = gearsNumber;
    }

    public Integer getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(Integer wheelSize) {
        this.wheelSize = wheelSize;
    }

    public Integer getGearsNumber() {
        return gearsNumber;
    }

    public void setGearsNumber(Integer gearsNumber) {
        this.gearsNumber = gearsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FoldingBike that = (FoldingBike) o;
        return Objects.equals(wheelSize, that.wheelSize) &&
                Objects.equals(gearsNumber, that.gearsNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wheelSize, gearsNumber);
    }

    @Override
    public String toString() {
        return getType().getTypeName() + " " + brand() + " with " + gearsNumber + " gear(s) and " + super.toString();
    }

    @Override
    public String toWritableForm() {
        return getType().getTypeName() + " " + brand() + ";" + " " + getWheelSize() + "; " + getGearsNumber() + "; " + getWeight() + "; " + isLightsAvailable() + "; " + color() + "; " + getPrice() + "\n";
    }
}
