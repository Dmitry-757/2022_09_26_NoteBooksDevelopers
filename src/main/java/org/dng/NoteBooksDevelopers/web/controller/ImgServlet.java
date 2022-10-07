package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dng.NoteBooksDevelopers.DAO.DAO;
import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ImgServlet", urlPatterns = { "/img/*" })
//@WebServlet(name = "ImgServlet", value = "/img")
public class ImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long devId=0;
        String devIdStr;
        if (  (devIdStr = request.getParameter("devid")) != null){
            devId = Long.parseLong(devIdStr);
        }

//        System.out.println("request for img! devid = "+devId);
        NotebookDeveloper item = DAO.getDeveloperById(devId);
        Optional<byte[]> contentO = item.getPhotoOpt();
        //byte[] content = item.getPhoto();
        response.setContentType("image/jpeg");
        response.setContentLength(contentO.orElse(new byte[0]).length);
        response.getOutputStream().write(contentO.orElse(new byte[0]));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
