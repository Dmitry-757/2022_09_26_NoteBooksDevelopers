package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;
import org.dng.NoteBooksDevelopers.web.controller.service.ServicesForServlets;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.List;

@WebServlet("/main")
public class StartServlet extends HttpServlet {
    private ITemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    @Override
    public void init(ServletConfig servletConfig) {
        this.application = JakartaServletWebApplication.buildApplication(servletConfig.getServletContext());
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

        List<NotebookDeveloper> devList = ServicesForServlets.getDevelopersList();
//        for (NotebookDeveloper d:devList) {
//            System.out.println(d.getName()+"  id = "+d.getId());
//        }

        ctx.setVariable("today", Calendar.getInstance());
        ctx.setVariable("ctxPath", request.getContextPath());
        ctx.setVariable("devList", devList);

        templateEngine.process("start", ctx, writer);
    }

}
