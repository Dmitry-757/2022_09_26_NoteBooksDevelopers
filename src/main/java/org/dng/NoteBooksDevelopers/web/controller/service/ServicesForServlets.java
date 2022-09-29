package org.dng.NoteBooksDevelopers.web.controller.service;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;

public class ServicesForServlets {

    //Create TemplateEngine
    public static ITemplateEngine buildTemplateEngine(final IWebApplication application) {
        final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "home" to "/templates/home.html"
//        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("utf-8");

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

}
