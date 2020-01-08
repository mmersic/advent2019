package com.mersic.day14;

class Reactant {
    public String name;
    public int    quantity;

    public Reactant(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String toString() {
        return "name: " + this.name + " q: " + this.quantity;
    }
}
