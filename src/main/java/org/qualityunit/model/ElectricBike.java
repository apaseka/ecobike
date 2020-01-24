package org.qualityunit.model;

import java.util.Objects;

public class ElectricBike extends Bike {

    private Integer maxSpeed;
    private Integer batteryCapacity;

    public ElectricBike(BikeType type, String brand, Integer weight, Boolean lightsAvailable, String color, Integer price, Integer maxSpeed, Integer batteryCapacity) {
        super(type, brand, weight, lightsAvailable, color, price);
        this.maxSpeed = maxSpeed;
        this.batteryCapacity = batteryCapacity;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ElectricBike that = (ElectricBike) o;
        return Objects.equals(maxSpeed, that.maxSpeed) &&
                Objects.equals(batteryCapacity, that.batteryCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxSpeed, batteryCapacity);
    }

    @Override
    public String toString() {
        return getType().getTypeName() + " " + brand() + " with " +
                batteryCapacity + " mAh battery and " + super.toString();
    }

    @Override
    public String toWritableForm() {
        return getType().getTypeName() + " " + brand() + ";" + " " + getMaxSpeed() + "; " + getWeight() + "; " + isLightsAvailable() + "; " + getBatteryCapacity() + "; " + color() + "; " + getPrice() + "\n";
    }
}
