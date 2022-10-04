package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dng.NoteBooksDevelopers.DAO.DAO;
import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;

import java.io.IOException;

@WebServlet(name = "ImgServlet", urlPatterns = { "/img/*" })
public class ImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long devId=0;
        String devIdStr;
        if (  (devIdStr = request.getParameter("devid")) != null){
            devId = Long.valueOf(devIdStr);
        }

        System.out.println("request for img! devid = "+devId);
        NotebookDeveloper item = DAO.getById(devId);
        byte[] content = item.getPhoto();
        response.setContentType("image/jpeg");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
