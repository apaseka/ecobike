package org.qualityunit.model;

public enum BikeType {

    F_BIKE("FOLDING BIKE", false),
    S_ELEC("SPEEDELEC", true),
    E_BIKE("E-BIKE", true),
    ;
    private String typeName;
    private boolean electric;

    BikeType(String typeName, boolean electric) {
        this.typeName = typeName;
        this.electric = electric;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isElectric() {
        return electric;
    }

    public void setElectric(boolean electric) {
        this.electric = electric;
    }
}
