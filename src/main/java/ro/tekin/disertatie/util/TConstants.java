package ro.tekin.disertatie.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tekin on 2/11/14.
 */
public interface TConstants {
    int ROLE_ADMIN = 0;
    int ROLE_USER = 1;
    int ROLE_COMPANY = 2;

    String ROLE_ADMIN_S = "ROLE_ADMIN";
    String ROLE_USER_S = "ROLE_USER";
    String ROLE_COMPANY_S = "ROLE_COMPANY";

    DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    Integer DEFAULT_RESULTS_PER_PAGE = 15;
}
