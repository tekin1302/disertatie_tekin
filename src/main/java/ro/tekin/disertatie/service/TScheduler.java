package ro.tekin.disertatie.service;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.tekin.disertatie.entity.Category;
import ro.tekin.disertatie.entity.Phone;
import ro.tekin.disertatie.entity.PhoneAttribute;
import ro.tekin.disertatie.entity.TFile;
import ro.tekin.disertatie.util.TUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by diana on 4/13/14.
 */
@Component
public class TScheduler {
    @Autowired
    private TService service;

    @Scheduled(fixedRate=60000)
    public void resizeImages() {
        List<TFile> images = service.getProfilePictures();
        if (images != null && images.size() > 0) {
            for (TFile f : images) {
                f.setResized(true);
                byte[] resized = TUtils.resizeImage(f.getData(), 600, f.getName());
                f.setData(resized);
                f.setSize((long)f.getData().length);
                service.saveTFile(f);
            }
        }
    }

//    @Scheduled(fixedRate=50000)

}
