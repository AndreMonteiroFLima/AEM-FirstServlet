package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.exception.NullParamException;
import com.adobe.aem.guides.wknd.core.exception.TicketException;
import com.adobe.aem.guides.wknd.core.models.Ticket;
import com.adobe.aem.guides.wknd.core.service.TicketService;
import com.adobe.aem.guides.wknd.core.service.impl.TicketServiceImpl;
import com.drew.lang.annotations.NotNull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;


import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

/** Servlet that writes some sample content into the response. It is mounted for
* all resources of a specific Sling resource type. The
* {@link SlingAllMethodsServlet} shall be used for HTTP methods that are
* idempotent. For write operations use the {@link SlingAllMethodsServlet}.
*/


@Component(immediate = true, service = Servlet.class, property = {
        SLING_SERVLET_METHODS + "=" + "POST",
        SLING_SERVLET_METHODS + "=" + "GET",
        SLING_SERVLET_METHODS + "=" + "DELETE",
        SLING_SERVLET_PATHS + "=" + "/bin/keepalive/ticketService",
        SLING_SERVLET_EXTENSIONS + "=" + "txt", SLING_SERVLET_EXTENSIONS + "=" + "json"})

@ServiceDescription("Ticket Service All")
public class TicketServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private TicketService ticketService = new TicketServiceImpl();

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        String ticketsJson=null;
        try {
            ticketsJson=ticketService.getTicket(req.getParameter("ticketId"));
        } catch (TicketException | NullParamException e) {
            ticketsJson = ticketService.getTickets();
        }
        if(!ticketsJson.isEmpty() && !ticketsJson.isBlank()) {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(ticketsJson);
        }
        else {
            resp.setStatus(404);
            resp.getWriter().write("Error while getting the tickets");
        }

    }

    @Override
    protected void doPost(final SlingHttpServletRequest request,final SlingHttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        Ticket ticket = new Ticket(title, description, price);
        try {
            ticketService.persist(ticket);
            response.setStatus(201);
            response.getWriter().write("Ticket created successfully: \n" + ticket.toJson());
        } catch (TicketException e) {
            response.setStatus(400);
            response.getWriter().write("Error while persisting the ticket");
        }
    }

    @Override
    protected void doDelete(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        String ticketId = request.getParameter("ticketId");
        try {
            ticketService.delete(Integer.parseInt(ticketId));
            response.setStatus(200);
            response.getWriter().write("Ticket deleted successfully: \n" + ticketId);
        } catch (TicketException e) {
            response.setStatus(404);
            response.getWriter().write("Error while deleting the ticket");
        }
    }
}
