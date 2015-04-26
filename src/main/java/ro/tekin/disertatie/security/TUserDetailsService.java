package ro.tekin.disertatie.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ro.tekin.disertatie.entity.Company;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.User;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TConstants;
import ro.tekin.disertatie.util.TContext;

/**
 * Created by tekin on 2/11/14.
 */
public class TUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(TUserDetailsService.class);

    @Autowired
    private TService service;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = null;
        username = username.trim();

        logger.debug("loadUserByUsername( " + username + ")");
        try {
            user = service.findUserByEmail(username);
        } catch (Exception e) {
            logger.error("", e);
        }
        if (user != null) {
            TUserDetails authUser = new TUserDetails();
            authUser.setPassword(user.getPassword());
            authUser.setEnabled(true);
            authUser.setUserId(user.getId());
            authUser.setUsername(user.getEmail());
            authUser.setRole(user.getRole());
            authUser.addAuthority(TContext.getInstance().getRoleMap().get(user.getRole()));
            if (user.getRole().equals(TConstants.ROLE_USER)) {
                Employee employee = service.getEmployeeByUserId(user.getId());
                authUser.setCompany(employee.getCompany().getId());
                authUser.setEmployee(employee.getId());
            } else if (user.getRole().equals(TConstants.ROLE_COMPANY)) {
                Company company  = service.getCompanyByUserId(user.getId());
                authUser.setCompany(company.getId());
            }
            logger.debug("User login");
            return authUser;
        } else {
            logger.debug("Username not found.");
            throw new UsernameNotFoundException("");
        }
    }
}
