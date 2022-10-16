package org.dng.NoteBooksDevelopers.web.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dng.NoteBooksDevelopers.DAO.DAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ImgDetailedModelsServlet", urlPatterns = { "/detailedModelDescImg/*" })
public class ImgDetailedModelsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long detailedModelId=0;
        String detailedModelIdStr;
        if (  (detailedModelIdStr = request.getParameter("detailedModelId")) != null){
            detailedModelId = Long.parseLong(detailedModelIdStr);
        }

        int photoId;
        String photoIdStr;
        if (  (photoIdStr = request.getParameter("photoId")) != null){
            photoId = Integer.parseInt(photoIdStr);

            if (photoId>0) {
                List<byte[]> photoList = DAO.getDetailedModelsPhotosByDetailedModelsId(detailedModelId);
                Optional<byte[]> contentO = Optional.ofNullable(photoList.get(photoId - 1));
                //byte[] content = item.getPhoto();
                response.setContentType("image/jpeg");
                response.setContentLength(contentO.orElse(new byte[0]).length);
                response.getOutputStream().write(contentO.orElse(new byte[0]));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
