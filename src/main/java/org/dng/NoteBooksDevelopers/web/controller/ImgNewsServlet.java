package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dng.NoteBooksDevelopers.DAO.DAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ImgNewsServlet", urlPatterns = { "/newsImg/*" })
public class ImgNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long newsId=0;
        String newsIdStr;
        if (  (newsIdStr = request.getParameter("newsId")) != null){
            newsId = Long.parseLong(newsIdStr);
        }


        List<byte[]> photoList = DAO.getShortNewsPhotosById(newsId);
        Optional<byte[]> contentO = Optional.ofNullable(photoList.get(0));
        //byte[] content = item.getPhoto();
        response.setContentType("image/jpeg");
        response.setContentLength(contentO.orElse(new byte[0]).length);
        response.getOutputStream().write(contentO.orElse(new byte[0]));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
