package com.adobe.aem.guides.wknd.core.filters;

import com.adobe.aem.guides.wknd.core.exception.NullParamException;
import org.apache.http.NoHttpResponseException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(service = Filter.class,
        property = {
                EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        })
@ServiceDescription("Filter to Post Method in Ticket servlet")
@ServiceRanking(-700)
@ServiceVendor("Adobe")
public class ParamFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SlingHttpServletRequest request = (SlingHttpServletRequest) servletRequest;
        SlingHttpServletResponse response = (SlingHttpServletResponse) servletResponse;
        if(request.getRequestURI().contains("ticketService")){
            if(request.getMethod().equalsIgnoreCase("POST")){
                if(request.getParameter("title")==null || request.getParameter("description")==null || request.getParameter("price")==null){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide all the required parameters");
                }
            }else if(request.getMethod().equalsIgnoreCase("DELETE")){
                if(request.getParameter("ticketId")==null){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide all the required parameters");
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
