package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.dao.TicketDao;
import com.adobe.aem.guides.wknd.core.exception.NullParamException;
import com.adobe.aem.guides.wknd.core.exception.TicketException;
import com.adobe.aem.guides.wknd.core.models.Ticket;
import com.adobe.aem.guides.wknd.core.service.TicketService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao = new TicketDao();

    @Override
    public void persist(Ticket ticket) throws TicketException{

        if(!ticketDao.getTickets().contains(ticket)) {
            ticketDao.persist(ticket);
        }else
            throw new TicketException("Ticket already exists");
    }

    @Override
    public String getTickets() {
        List<Ticket> tickets= ticketDao.getTickets();
        String ticketsJson = new Gson().toJson(tickets);
        return ticketsJson;
    }

    @Override
    public void delete(int ticketId) throws TicketException {
        Ticket ticketToRemove = ticketDao.getTicket(ticketId);
        if( ticketToRemove != null) {
            ticketDao.delete(ticketToRemove);
        }else
            throw new TicketException("Ticket does not exist");
    }

    @Override
    public String getTicket(String ticketId) throws TicketException {
        if(ticketId != null) {
            Ticket ticket = ticketDao.getTicket(Integer.parseInt(ticketId));
            if (ticket != null) {
                String ticketJson = new Gson().toJson(ticket);
                return ticketJson;
            } else
                throw new TicketException("Ticket does not exist");
        }else
            throw new NullParamException("TicketId is null");
    }

}
