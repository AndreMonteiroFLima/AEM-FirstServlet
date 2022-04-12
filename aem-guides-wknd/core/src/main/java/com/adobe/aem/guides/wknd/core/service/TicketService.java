package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.exception.TicketException;
import com.adobe.aem.guides.wknd.core.models.Ticket;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TicketService {

   void persist(Ticket ticket) throws TicketException;

   String getTickets();

   void delete(int ticketId) throws TicketException;

   String getTicket(String ticketId) throws TicketException;

}
