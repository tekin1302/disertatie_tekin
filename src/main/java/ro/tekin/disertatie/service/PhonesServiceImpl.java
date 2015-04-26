package ro.tekin.disertatie.service;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tekin.disertatie.entity.Category;
import ro.tekin.disertatie.entity.Phone;
import ro.tekin.disertatie.entity.PhoneAttribute;
import ro.tekin.disertatie.entity.TFile;
import ro.tekin.disertatie.util.TUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tekin.omer on 7/14/2014.
 */
@Component
public class PhonesServiceImpl implements PhonesService{

    private static String EMAG_URL = "http://www.emag.ro/telefon-mobil-lg-nexus-5-d821-4g-32gb-white-80009/pd/DW5G4BBBM/";
    private static String GSM_ARENA_URL = "http://www.gsmarena.com/";

    @Autowired
    private TService service;

    @Override
    public void getPhones() throws Exception {

        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));


        try {
            List<String> brandURLS = getPhoneBrandURLS(client, method);

            for (String brandURL : brandURLS) {
                List<String> modelURLs = getModelURLS(client, method, GSM_ARENA_URL + brandURL);
                for (String modelURL : modelURLs) {
                    Phone phone = service.getPhoneByURL(modelURL);
                    if (phone != null) {
                        continue;
                    }
                    try {
                        phone = getPhoneDetails(client, method, GSM_ARENA_URL + modelURL);
                        phone.setUrl(modelURL);
                        service.savePhone(phone);
                    } catch (Exception e) {
                        System.out.println("Nu s-au putut luat detaliile pentru " + GSM_ARENA_URL + modelURL);
                        e.printStackTrace();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }

    private Phone getPhoneDetails(HttpClient client, GetMethod method, String modelURL) throws Exception {
        method.setURI(new URI(modelURL));
        client.executeMethod(method);

        Document doc = Jsoup.parse(method.getResponseBodyAsStream(), "UTF-8", "");

        String photoURL = doc.select("#specs-cp-pic img").get(0).attr("src");
        byte[] photoBytes = getPhonePhoto(photoURL);

        List<PhoneAttribute> phoneAttributes = new ArrayList<PhoneAttribute>();

        Elements tables = doc.select("#specs-list table");

        for (int t=0; t < tables.size(); t++) {
            Element table = tables.get(t);
            String categName = table.getElementsByTag("th").get(0).text();

            Category category = service.getCategoryByName(categName);
            if (category == null) {
                category = new Category();
                category.setName(categName.toUpperCase());
                service.saveCategory(category);
            }

            Elements cols = table.getElementsByTag("td");

            for (int i=0; i < cols.size() / 2; i++) {
                PhoneAttribute phoneAttribute = new PhoneAttribute();
                phoneAttribute.setCategory(category);
                phoneAttribute.setName(cols.get(i * 2).text());
                phoneAttribute.setValue(cols.get(i * 2 + 1).text());
                phoneAttributes.add(phoneAttribute);
            }
        }



        Phone phone = new Phone();
        phone.setName(doc.select("#ttl h1").get(0).text());

        TFile photo = new TFile();
        photo.setData(photoBytes);
        photo.setMimeType("image/jpg");
        photo.setName(phone.getName());
        photo.setSize((long)photoBytes.length);

        phone.setPhoto(photo);
        phone.setAttributeList(phoneAttributes);

        return phone;
    }

    private static byte[] getPhonePhoto(String photoURL) throws Exception {
            URL url = new URL(photoURL);

            URLConnection urlCon = url.openConnection();

            InputStream pictureInput = urlCon.getInputStream();
            byte[] buff = new byte[1024];

            ByteArrayOutputStream out = null;

            try {
                out = new ByteArrayOutputStream();

                int size = -1;
                while((size = pictureInput.read(buff)) != -1) {
                    out.write(buff, 0, size);
                }

            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                    if (pictureInput != null) {
                        pictureInput.close();
                    }
                } catch (Exception e) {}
                return out.toByteArray();
            }
    }

    private static List<String> getModelURLS(HttpClient client, GetMethod method, String brandURL) throws Exception {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Nu e nimic important!");
        }
        List<String> result = new ArrayList<String>();

        method.setURI(new URI(brandURL));

        client.executeMethod(method);

        Document doc = Jsoup.parse(method.getResponseBodyAsStream(), "UTF-8", "");
        Elements models = doc.select("#main .makers ul li a");

        for (int i=0; i<models.size(); i++) {
            result.add(models.get(i).attr("href"));
        }

        // check for more pages
        Elements pages = doc.select(".nav-pages a:last-of-type");
        /*if (pages != null && pages.size() > 0 && pages.get(0).attr("title").equals("Next page")) {
            result.addAll(getModelURLS(client, method, GSM_ARENA_URL + pages.get(0).attr("href")));
        }*/
        return result;
    }

    private static List<String> getPhoneBrandURLS(HttpClient client, GetMethod method) throws Exception {
        List<String> result = new ArrayList<String>();

        method.setURI(new URI(GSM_ARENA_URL));
        client.executeMethod(method);

        Document doc = Jsoup.parse(method.getResponseBodyAsStream(), "UTF-8", "");
        Elements brands = doc.select("#brandmenu ul li a");

        for (int i=0; i<brands.size(); i++) {
            result.add(brands.get(i).attr("href"));
        }

        return result;
    }

}
