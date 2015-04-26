package ro.tekin.disertatie.repository;

import ro.tekin.disertatie.bean.MessBean;
import ro.tekin.disertatie.entity.*;
import ro.tekin.disertatie.util.paging.TPager;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tekin on 2/11/14.
 */
public interface TRepository {
    User getUserById(Integer userId);

    User findUserByEmail(String email);

    void saveException(TException exception);

    TException getExceptionById(Integer id);

    void saveUser(User user);

    List<TException> getAllExceptions();

    List<TException> getExceptions(int first, TPager pager);

    long countExceptions();

    void saveCompany(Company company);

    Company getCompanyById(Integer companyId);


    Company getCompanyByUserId(Integer userId);

    void saveTFile(TFile logo);

    TFile getTFile(Integer id);

    Employee getEmployee(Integer id);

    long countEmployees();

    List<Employee> getEmployees(int first, int maxResults, Integer companyId);

    void saveEmployee(Employee employee);

    Employee getEmployeeByUserId(Integer userId);

    List<Position> getPositionsForCompany(Integer companyId);

    void savePosition(Position position);

    Position getPosition(Integer id, Integer createdBy);

    List<Employee> getAllEmployeesForCompany(Integer companyId);

    void saveOrganigram(Organigram organigram);

    Organigram getOrganigramByCompanyId(Integer company);

    void saveOrganigramElement(OrganigramElement elem);

    void deleteOrganigramElements(Integer id);

    List<OrganigramElement> getOrganigramElements(Integer organigramId);

    void deleteAllChatRooms(Integer companyId);

    void saveChatRoom(ChatRoom chatRoom);

    void saveChatMember(ChatMember cm);

    List<TFile> getProfilePictures();

    void saveTMessage(TMessage tMessage);

    List<ChatRoom> getAllChatRooms(Integer user);

    List<MessBean> getMessage(Integer last, Integer chatRoom);

    Activity getActivity(Integer id);
    long countActivities(Integer companyId);
    List<Activity> getActivities(int first, int maxResults, Integer companyId);

    void saveActivity(Activity activity);

    List<EmployeeActivity> getEmployeeActivitiesByEmployeeId(Integer employeeId);

    List<EmployeeActivity> getEmployeeActivitiesByActivityId(Integer activityId);

    void deleteEmployeeActivitiesByActivityId(Integer id);

    void saveEmployeeActivity(EmployeeActivity employeeActivity);

    EmployeeActivity getEmployeeActivitiesById(Integer id);

    void deleteEmployeeActivitiesById(Integer id);

    List<EmployeeActivity> getEmployeeActivities(int first, Integer perPage, Integer employee);
    List<EmployeeActivity> getEmployeeActivities();

    long countEmployeeActivities(Integer employee);

    long countTimesheets(Integer empActivityId);

    List<Timesheet> getTimesheets(int first, Integer perPage, Integer empActivityId);
    List<Timesheet> getTimesheets(Integer empActivityId);

    void saveTimesheet(Timesheet timesheet);

    void deleteTimesheet(Integer id);

    Timesheet getTimesheet(Integer id);

    List<Timesheet> getTimesheetsByActivity(Integer activityId);

    void savePhone(Phone phone);

    Category getCategoryByName(String categName);

    void saveCategory(Category category);

    Phone getPhoneByURL(String modelURL);

    List<Phone> getPhones(int start, int i);

    Long countPhones();
}
