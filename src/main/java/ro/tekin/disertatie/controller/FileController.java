package ro.tekin.disertatie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.tekin.disertatie.bean.FileUploadDTO;
import ro.tekin.disertatie.entity.TFile;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tekin.omer on 2/5/14.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private TService service;

    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    public Integer createFile(FileUploadDTO fileUploadDTO) {

        if (fileUploadDTO.getFileData0() != null && fileUploadDTO.getFileData0().getSize() > 0) {
            TFile tFile = new TFile();

//            byte[] compressed = TUtils.resizeImage(fileUploadDTO.getFileData0().getBytes(), 600, fileUploadDTO.getFileData0().getOriginalFilename());

//            tFile.setData(compressed);
            tFile.setData(fileUploadDTO.getFileData0().getBytes());
            tFile.setName(fileUploadDTO.getFileData0().getOriginalFilename());
            tFile.setSize(fileUploadDTO.getFileData0().getSize());
            try {
                tFile.setMimeType(Files.probeContentType(Paths.get(tFile.getName())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            service.saveTFile(tFile);

            return tFile.getId();
        }
        return null;
    }

    @RequestMapping (value = "/img/{id}", method = RequestMethod.GET)
    public void getImage(@PathVariable("id") Integer imgId, HttpServletResponse response)  {

        TFile tFile = service.getTFile(imgId);

        if (tFile.getMimeType() != null) {
            response.setContentType(tFile.getMimeType());
        }
        response.setHeader("Content-length", "" + tFile.getSize());
        OutputStream os = null;
        try{
            os = response.getOutputStream();

            os.write(tFile.getData());
            os.flush();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
            } catch (Exception e) {
                logger.error("EROARREEEEE");
//                logger.error("", e);
            }
        }
    }
}

