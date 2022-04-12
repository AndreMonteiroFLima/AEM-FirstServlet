package com.adobe.aem.guides.wknd.core.models;


import com.google.gson.Gson;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Model(adaptables = Resource.class)
public class Ticket {


    private int id;
    private String title;
    private String description;
    private String price;
    private static List<Ticket> tickets = new ArrayList<Ticket>();
    static int lastId = 0;
    static {
        tickets.add(new Ticket("Ticket 1", "This is the first ticket", "10.00"));
    }

    public static void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public static List<Ticket> getTickets() {
        System.out.println(tickets);
        return tickets;
    }

    public static void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public Ticket(String title, String description, String price) {
        this.id = ++lastId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(title, ticket.title) && Objects.equals(description, ticket.description) && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price);
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "title=" + title + ", description='" + description + ", price='" + price +'}';
    }
}
