package org.dng.NoteBooksDevelopers.web.controller;

import java.io.*;
import java.util.Calendar;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dng.NoteBooksDevelopers.web.controller.service.ServicesForServlets;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private ITemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    @Override
    public void init(ServletConfig servletConfig) {
        this.application = JakartaServletWebApplication.buildApplication(servletConfig.getServletContext());
//        this.templateEngine = buildTemplateEngine(this.application);
        this.templateEngine = ServicesForServlets.buildTemplateEngine(this.application);

    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        final IWebExchange webExchange = this.application.buildExchange(request, response);
//        final IWebRequest webRequest = webExchange.getRequest();

        final Writer writer = response.getWriter();
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

        ctx.setVariable("today", Calendar.getInstance());
        ctx.setVariable("ctxPath", request.getContextPath());

        templateEngine.process("startMenu", ctx, writer);

    }

    @Override
    public void destroy() {
    }
}