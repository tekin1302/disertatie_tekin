package ro.tekin.disertatie.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by diana on 7/13/14.
 */
public class TestPhone {
    private static String url = "http://www.emag.ro/telefon-mobil-lg-nexus-5-d821-4g-32gb-white-80009/pd/DW5G4BBBM/";
//    private static String url = "http://www.gsmarena.com/samsung_galaxy_s5_mini-6252.php";

    public static void main1(String[] args) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements elems = doc.select(".price");
        for(int i=0; i<elems.size(); i++) {
            Element elem = elems.get(i);
            System.out.println(elem.data());
        }
    }
    public static void main(String[] args) throws Exception {
        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();

        // Create a method instance.
//        GetMethod method = new GetMethod(url);
        GetMethod getPicMethod = null;

        // Provide custom retry handler is necessary
//        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//                new DefaultHttpMethodRetryHandler(3, false));
            /*client.executeMethod(method);

            Document doc = Jsoup.parse(method.getResponseBodyAsStream(), "", "");
            String price = doc.select("#offer-price-stock .prices").get(0).getElementsByClass("money-int").get(0).text();
            System.out.println("Price: " + price);

            String pictureUrl = doc.select("#product-pictures-picture .poza-mare-produs .poza_produs").get(0).attr("src");
*/
//            getPicMethod = new GetMethod("http://www.soyunalbondiga.com/wp-content/uploads/2013/05/obiwan.jpg");

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("D:/te.jpg")));
            byte[] arr = getPhonePhoto("http://www.soyunalbondiga.com/wp-content/uploads/2013/05/obiwan.jpg");
            out.write(arr, 0, arr.length);

        System.out.println("arr.length: " + arr.length);
            out.close();

        try {
            // Url con la foto
            URL url = new URL(
                    "http://cdn2.gsmarena.com/vv/bigpic/samsung-ace-style.jpg");

            // establecemos conexion
            URLConnection urlCon = url.openConnection();

            // Sacamos por pantalla el tipo de fichero
            System.out.println(urlCon.getContentType());

            // Se obtiene el inputStream de la foto web y se abre el fichero
            // local.
            InputStream is = urlCon.getInputStream();


            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(new File("D:/tes.jpg")));

            // Lectura de la foto de la web y escritura en fichero local
            byte[] array = new byte[1000]; // buffer temporal de lectura.
            int leido = is.read(array);
            int t = 0;
            while (leido > 0) {
                t += leido;
                fos.write(array, 0, leido);
                leido = is.read(array);
            }

            System.out.println("t: " + t);
            // cierre de conexion y fichero.
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
