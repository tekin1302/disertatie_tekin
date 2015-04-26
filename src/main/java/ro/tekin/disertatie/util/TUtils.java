package ro.tekin.disertatie.util;

import com.sun.mail.iap.ByteArray;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.tekin.disertatie.security.TUserDetails;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

/**
 * Created by tekin on 2/20/14.
 */
public class TUtils {
    private static Properties props;

    public static String getProperty(String propName){
        if (props == null) {
            InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");
            Properties props = new Properties();
            try {
                props.load(inStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props.getProperty(propName);
    }

    public static String getExtension(String originalFilename, boolean includeDot) {
        if(originalFilename == null || originalFilename.trim().length() == 0){
            return null;
        }
        String[] parts = originalFilename.split("\\.");
        if(parts != null && parts.length > 1){
            if(includeDot){
                return "." + parts[parts.length - 1];
            }else{
                return parts[parts.length - 1];
            }

        }else{
            return null;
        }
    }

    public static byte[] resizeImage(byte[] bytes, int newWidth, String pathname) {

        /*BufferedImage image = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bais = new ByteArrayInputStream(bytes);
            image = ImageIO.read(bais);
            BufferedImage scaledImage = Scalr.resize(image, newWidth);
            String extension = getExtension(pathname, false);
            ImageIO.write(scaledImage, extension, baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return bytes;
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            }catch (Exception e2) {
                    e2.printStackTrace();
             }
        }*/
        BufferedImage image = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bais = new ByteArrayInputStream(bytes);
            image = ImageIO.read(bais);

            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
                    : image.getType();

            BufferedImage imageR = resizeImage(image, type, newWidth);
            String extension = getExtension(pathname, false);
            ImageIO.write(imageR, extension, baos);

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return bytes;
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            }catch (Exception e2) {
                    e2.printStackTrace();
             }
        }
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int newWidth) {
        int newHeight = (originalImage.getHeight() * newWidth) / originalImage.getWidth();

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }

    public static TUserDetails getAuthenticatedUser() {
        return (TUserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static String encode64(byte[] image, String mimeType) {
        if (image != null && image.length > 0) {
            return "data:" + mimeType + ";base64," + Base64.encode(image);
        } else {
            return null;
        }
    }

    public static boolean isImage(byte[] bytes) {
        boolean valid = false;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            BufferedImage image= ImageIO.read(bais);
            valid = (image != null);
        } catch(IOException ex) {
            // nu vreau sa umple logurile cu erori de genul asta
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                }
            }
        }
        return valid;
    }
}
