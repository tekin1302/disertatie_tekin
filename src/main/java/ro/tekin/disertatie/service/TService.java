package ro.tekin.disertatie.service;

import ro.tekin.disertatie.bean.MessBean;
import ro.tekin.disertatie.bean.OrganigramBean;
import ro.tekin.disertatie.bean.TSStatisticsBean;
import ro.tekin.disertatie.entity.*;
import ro.tekin.disertatie.exception.OrganigramNotValidException;
import ro.tekin.disertatie.util.paging.TPager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by tekin on 2/11/14.
 */
public interface TService {

    // USER
    User getUserById(Integer userId);

    User findUserByEmail(String email);

    void saveException(TException exception);

    TException getExceptionById(Integer id);

    List<TException> getAllExceptions();

    List<TException> getExceptions(TPager pager);

    // COMPANY

    void register(Company company);
    Company getCompanyForUpdate(Integer userId);

    void updateCompany(Company company);

    Company getCompanyByUserId(Integer id);

    // FILE
    void saveTFile(TFile tFile);

    TFile getTFile(Integer id);

    // EMPLOYEE
    Employee getEmployee(Integer id);

    List<Employee> getEmployeesWithPager(TPager pager);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    Employee getEmployeeByUserId(Integer id);

    void deleteEmployee(Integer id);

    List<Employee> getEmployeesForCompany();

    // POSITION

    List<Position> getPositionsForCompany(Integer company);

    void savePosition(Position position);

    Position getPosition(Integer id);

    void updatePosition(Position position);

    void deletePosition(Integer id);

    void saveOrganigramElements(OrganigramBean[] organigramBeans) throws OrganigramNotValidException;

    void saveOrganigram(Organigram organigram);

    Organigram getOrganigramForCurrentCompany();

    List<OrganigramElement> getOrganigramElements(Integer id);

    void deleteAllChatRooms();

    void saveChatRoom(ChatRoom chatRoom);

    void saveChatMember(ChatMember cm);

    void createDefaultChatRooms();

    List<TFile> getProfilePictures();

    void saveMessage(Integer chatId, String mess);

    List<ChatRoom> getAllChatRooms();

    Employee getCurrentEmployee();

    List<MessBean> getMessage(Integer id, Integer last);

    // ACTIVITY
    Activity getActivity(Integer id);

    List<Activity> getActivitiesWithPager(TPager pager);

    void saveActivity(Activity activity);

    void updateActivity(Activity activity);

    void deleteActivity(Integer id);

    List<EmployeeActivity> getEmployeeActivitiesByEmployeeId(Integer employeeActivityId);

    List<EmployeeActivity> getEmployeeActivitiesByActivityId(Integer activityId);

    void saveEmployeeActivity(List<EmployeeActivity> employeeActivity);

    void deleteEmployeeActivity(Integer id);

    List<Timesheet> getTimesheetsWithPager(TPager pager, Integer empActivityId);

    List<EmployeeActivity> getEmployeeActivitiesWithPager(TPager pager);

    void saveTimesheet(Timesheet timesheet);

    void deleteTimesheet(Integer id);

    Timesheet getTimesheet(Integer id);

    byte[] generateReport();

    List<TSStatisticsBean> getTSStatistics(Integer activityId);

    void savePhone(Phone phone);

    Category getCategoryByName(String categName);

    void saveCategory(Category category);

    Phone getPhoneByURL(String modelURL);

    List<Phone> getPhones(Integer page);

    Long countPhones();
}
