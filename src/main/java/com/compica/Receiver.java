package com.compica;

public class Receiver {
    private String name;
    private String address;

    public Receiver(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    // This will be displayed in the ComboBox
    @Override
    public String toString() {
        return name;
    }
}