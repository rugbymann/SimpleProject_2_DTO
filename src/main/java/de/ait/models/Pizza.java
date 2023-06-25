package de.ait.models;

public class Pizza {
    private String id;
    private String title;
    private String size;
    private double price;

    public Pizza(String id, String title, String size, double price) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.price = price;

    }



    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Пицца " + title  +
                " размер: " + size  +
                ", цена " + price +" €";
    }
}
