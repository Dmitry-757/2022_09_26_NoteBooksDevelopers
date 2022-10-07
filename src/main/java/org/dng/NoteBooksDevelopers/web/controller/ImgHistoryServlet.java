package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dng.NoteBooksDevelopers.DAO.DAO;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ImgHistoryServlet", urlPatterns = { "/historyImg/*" })
public class ImgHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long historyId=0;
        String historyIdStr;
        if (  (historyIdStr = request.getParameter("historyId")) != null){
            historyId = Long.parseLong(historyIdStr);
        }


        List<byte[]> photoList = DAO.getHistoryPhotosByDevHistoryId(historyId);
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
