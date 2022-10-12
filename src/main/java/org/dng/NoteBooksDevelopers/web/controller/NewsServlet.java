package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dng.NoteBooksDevelopers.DAO.DAO;
import org.dng.NoteBooksDevelopers.web.controller.service.ServicesForServlets;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "NewsServlet", value = "/news")
public class NewsServlet extends HttpServlet {

    //*** for using templateEngine
    private ITemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    @Override
    public void init(ServletConfig servletConfig) {
        this.application = JakartaServletWebApplication.buildApplication(servletConfig.getServletContext());
        this.templateEngine = ServicesForServlets.buildTemplateEngine(this.application);
    }
    //********************************


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        final IWebExchange webExchange = this.application.buildExchange(request, response);
//        final IWebRequest webRequest = webExchange.getRequest();

        final Writer writer = response.getWriter();
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

        ctx.setVariable("ctxPath", request.getContextPath());

        ctx.setVariable("newsMap", DAO.getNews());

        templateEngine.process("News", ctx, writer);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
