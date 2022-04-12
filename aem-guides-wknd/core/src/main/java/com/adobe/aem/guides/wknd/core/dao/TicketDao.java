package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Ticket;

import java.util.List;

public class TicketDao {


    public void persist(Ticket ticket) {
        Ticket.addTicket(ticket);
    }

    public void delete(Ticket ticket) {
        Ticket.removeTicket(ticket);
    }

    public List<Ticket> getTickets(){
        return Ticket.getTickets();
    }

    public boolean exists(Ticket ticket) {
        return Ticket.getTickets().contains(ticket);
    }

    public Ticket getTicket(int ticketId) {
        for (Ticket ticket : Ticket.getTickets()) {
            if (ticket.getId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }
}
