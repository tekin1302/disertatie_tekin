package ro.tekin.disertatie.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by tekin on 3/17/14.
 */
public class FileUploadDTO {
    private CommonsMultipartFile fileData0;

    public CommonsMultipartFile getFileData0() {
        return fileData0;
    }

    public void setFileData0(CommonsMultipartFile fileData0) {
        this.fileData0 = fileData0;
    }
}
