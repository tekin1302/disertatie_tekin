package ro.tekin.disertatie.service;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tekin.disertatie.bean.MessBean;
import ro.tekin.disertatie.bean.OrganigramBean;
import ro.tekin.disertatie.bean.TSStatisticsBean;
import ro.tekin.disertatie.entity.*;
import ro.tekin.disertatie.exception.OrganigramNotValidException;
import ro.tekin.disertatie.repository.TRepository;
import ro.tekin.disertatie.security.TPasswordEncoder;
import ro.tekin.disertatie.util.TConstants;
import ro.tekin.disertatie.util.TContext;
import ro.tekin.disertatie.util.TUtils;
import ro.tekin.disertatie.util.paging.TPager;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by tekin on 2/11/14.
 */
@Service
public class TServiceImpl implements TService {

    @Autowired
    private TRepository repository;

    public User getUserById(Integer userId) {
        return repository.getUserById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public void saveException(TException exception) {
        repository.saveException(exception);
    }

    @Override
    public TException getExceptionById(Integer id) {
        return repository.getExceptionById(id);
    }

    @Override
    @Transactional
    public void register(Company company) {
        User user = company.getUser();
        user.setRole(TConstants.ROLE_COMPANY);
        user.setActive(true);
        user.setPassword(TPasswordEncoder.getInstance().encodePassword(user.getPassword(), null));

        repository.saveUser(user);
        repository.saveCompany(company);
    }

    @Override
    public List<TException> getAllExceptions() {
        return repository.getAllExceptions();
    }

    @Override
    public List<TException> getExceptions(TPager pager) {
        int first = (pager.getPage() - 1) * pager.getPerPage();
        long total = repository.countExceptions();
        pager.setRecords((int) total);
        pager.setTotalPages(total % pager.getPerPage() == 0 ? (int) total / pager.getPerPage() : (int) total / pager.getPerPage() + 1);
        return repository.getExceptions(first, pager);
    }

    @Override
    public Company getCompanyForUpdate(Integer userId) {
        Company company = repository.getCompanyByUserId(userId);
        company.getUser().setPassword(null);
        return company;
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        User user = company.getUser();
        if (user.getPassword() != null && user.getPassword().trim().length() > 0) {
            user.setPassword(TPasswordEncoder.getInstance().encodePassword(user.getPassword(), null));
        } else {
            user.setPassword(repository.getUserById(user.getId()).getPassword());
        }

        repository.saveUser(user);
        repository.saveCompany(company);
    }

    @Override
    public void saveTFile(TFile tFile) {
        repository.saveTFile(tFile);
    }

    @Override
    public TFile getTFile(Integer id) {
        return repository.getTFile(id);
    }

    @Override
    public Employee getEmployee(Integer id) {
        return repository.getEmployee(id);
    }

    @Override
    public List<Employee> getEmployeesWithPager(TPager pager) {
        int first = (pager.getPage() - 1) * pager.getPerPage();
        long total = repository.countEmployees();
        pager.setRecords((int) total);
        pager.setTotalPages(total % pager.getPerPage() == 0 ? (int) total / pager.getPerPage() : (int) total / pager.getPerPage() + 1);

        return repository.getEmployees(first, pager.getPerPage(), TUtils.getAuthenticatedUser().getCompany());
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        User user = employee.getUser();
        user.setRole(TConstants.ROLE_USER);
        user.setActive(true);
        user.setPassword(TPasswordEncoder.getInstance().encodePassword(user.getPassword(), null));

        Company company = new Company();
        company.setId(TUtils.getAuthenticatedUser().getCompany());
        employee.setCompany(company);
        employee.setActive(true);
        repository.saveUser(user);

        repository.saveEmployee(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        User user = employee.getUser();

        if (user.getPassword() != null && user.getPassword().trim().length() > 0) {
            user.setPassword(TPasswordEncoder.getInstance().encodePassword(user.getPassword(), null));
        } else {
            user.setPassword(repository.getUserById(user.getId()).getPassword());
        }

        repository.saveUser(user);

        repository.saveEmployee(employee);
    }

    @Override
    public Employee getEmployeeByUserId(Integer id) {
        return repository.getEmployeeByUserId(id);
    }

    @Override
    public List<Position> getPositionsForCompany(Integer companyId) {
        return repository.getPositionsForCompany(companyId);
    }

    @Override
    public void savePosition(Position position) {
        Company company = new Company();
        company.setId(TUtils.getAuthenticatedUser().getCompany());
        position.setCreatedBy(company);
        position.setActive(true);
        repository.savePosition(position);
    }

    @Override
    public Company getCompanyByUserId(Integer id) {
        return repository.getCompanyByUserId(id);
    }

    @Override
    public Position getPosition(Integer id) {
        Integer createdBy = null;
        if (TUtils.getAuthenticatedUser().getRole() == TConstants.ROLE_COMPANY) {
            createdBy =  TUtils.getAuthenticatedUser().getCompany();
        }
        return repository.getPosition(id, createdBy);
    }

    @Override
    public void updatePosition(Position position) {
        repository.savePosition(position);
    }

    @Override
    public void deletePosition(Integer id) {
        Position position = this.getPosition(id);
        position.setActive(false);
        repository.savePosition(position);
    }

    @Override
    @Transactional
    public void saveOrganigramElements(OrganigramBean[] organigramBeans) throws OrganigramNotValidException {
        Organigram organigram = repository.getOrganigramByCompanyId(TUtils.getAuthenticatedUser().getCompany());
        repository.deleteOrganigramElements(organigram.getId());

        for (OrganigramBean bean : organigramBeans) {
            if (bean == null) {
                throw new OrganigramNotValidException();
            }
            OrganigramElement elem = new OrganigramElement();
            elem.setDown(new Employee(bean.getEmployeeIdDown()));
            elem.setUp(new Employee(bean.getEmployeeIdUp()));
            elem.setOrganigram(organigram);
            repository.saveOrganigramElement(elem);
        }
    }

    @Override
    public void saveOrganigram(Organigram organigram) {
        repository.saveOrganigram(organigram);
    }

    @Override
    public Organigram getOrganigramForCurrentCompany() {
        return repository.getOrganigramByCompanyId(TUtils.getAuthenticatedUser().getCompany());
    }

    @Override
    public List<OrganigramElement> getOrganigramElements(Integer organigramId) {
        return repository.getOrganigramElements(organigramId);
    }

    @Override
    public void deleteAllChatRooms() {
        repository.deleteAllChatRooms(TUtils.getAuthenticatedUser().getCompany());
    }

    @Override
    public void saveChatRoom(ChatRoom chatRoom) {
        repository.saveChatRoom(chatRoom);
    }

    @Override
    public void saveChatMember(ChatMember cm) {
        repository.saveChatMember(cm);
    }

    @Override
    @Transactional
    public void createDefaultChatRooms() {
        Organigram organigram = this.getOrganigramForCurrentCompany();
        if (organigram == null) throw new RuntimeException("Nu exista organigrama!");
        List<OrganigramElement> organigramElementList = this.getOrganigramElements(organigram.getId());

        Map<Employee, ArrayList<Employee>> map = new HashMap<Employee, ArrayList<Employee>>();

        for (OrganigramElement organigramElement : organigramElementList) {
            ArrayList<Employee> values = map.get(organigramElement.getUp());
            if (values == null) {
                values = new ArrayList<Employee>();
                map.put(organigramElement.getUp(), values);
            }
            values.add(organigramElement.getDown());
        }
        this.deleteAllChatRooms();

        int count = 0;
        for (Employee key : map.keySet()) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setDateCreated(new Date());
            chatRoom.setCompany(organigram.getCompany());
            chatRoom.setName("Chat " + (++count));
            this.saveChatRoom(chatRoom);

            ChatMember chatMember = new ChatMember();
            chatMember.setEmployee(key);
            chatMember.setChatRoom(chatRoom);
            this.saveChatMember(chatMember);

            List<Employee> subs = map.get(key);
            if (subs != null && subs.size() > 0) {
                for (Employee employee : subs) {
                    ChatMember cm = new ChatMember();
                    cm.setEmployee(employee);
                    cm.setChatRoom(chatRoom);
                    this.saveChatMember(cm);
                }
            }
        }
    }

    @Override
    public List<TFile> getProfilePictures() {
        return repository.getProfilePictures();
    }

    @Override
    public void saveMessage(Integer chatId, String mess) {
        TMessage tMessage = new TMessage();
        tMessage.setChatRoom(new ChatRoom(chatId));
        tMessage.setDate(new Date());
        tMessage.setEmployee(new Employee(TUtils.getAuthenticatedUser().getEmployee()));
        tMessage.setText(mess);
        repository.saveTMessage(tMessage);
//        TContext.getInstance().setNewMessageForChat(chatId);
        List<Thread> threads = TContext.getInstance().getThreads(chatId);
        if (threads != null && threads.size() > 0) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        return repository.getAllChatRooms(TUtils.getAuthenticatedUser().getEmployee());
    }

    @Override
    public Employee getCurrentEmployee() {
        return repository.getEmployee(TUtils.getAuthenticatedUser().getEmployee());
    }

    @Override
    public List<MessBean> getMessage(Integer chatRoomId, Integer last) {
        /*List<MessBean> result = null;
        TContext.getInstance().setNewMessage(chatRoomId, true);

        for (int i=0; i<50; i++) {
            if (TContext.getInstance().hasNewMessage(chatRoomId)) {
                result = repository.getMessage(last, chatRoomId);
                if (result != null) {
                    return result;
                } else {
                    TContext.getInstance().setNewMessage(chatRoomId, false);
                }
            }
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }

        }*/
        List<MessBean> result = repository.getMessage(last, chatRoomId);
        if (result != null && result.size() != 0) {
            return result;
        }
        // nu am mesaje. astept sa vina
        TContext.getInstance().addMsgThread(chatRoomId, Thread.currentThread());
        try {
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            return repository.getMessage(last, chatRoomId);
        }

        return null;

    }

    @Override
    public Activity getActivity(Integer id) {
        return repository.getActivity(id);
    }

    @Override
    public List<Activity> getActivitiesWithPager(TPager pager) {
        int first = (pager.getPage() - 1) * pager.getPerPage();
        long total = repository.countActivities(TUtils.getAuthenticatedUser().getCompany());
        pager.setRecords((int) total);
        pager.setTotalPages(total % pager.getPerPage() == 0 ? (int) total / pager.getPerPage() : (int) total / pager.getPerPage() + 1);

        return repository.getActivities(first, pager.getPerPage(), TUtils.getAuthenticatedUser().getCompany());
    }

    @Override
    public void saveActivity(Activity activity) {
        activity.setCompany(new Company(TUtils.getAuthenticatedUser().getCompany()));
        activity.setActive(true);
        repository.saveActivity(activity);
    }

    @Override
    public void updateActivity(Activity activity) {
        repository.saveActivity(activity);
    }

    @Override
    public void deleteActivity(Integer id) {
        Activity activity = repository.getActivity(id);
        if (activity != null) {
            activity.setActive(false);
            repository.saveActivity(activity);
        }
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivitiesByEmployeeId(Integer employeeId) {
        return repository.getEmployeeActivitiesByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivitiesByActivityId(Integer activityId) {
        return repository.getEmployeeActivitiesByActivityId(activityId);
    }

    @Override
    @Transactional
    public void saveEmployeeActivity(List<EmployeeActivity> employeeActivities) {
        Integer activityId = null;
        if (employeeActivities == null || employeeActivities.size() == 0) return;
        else activityId = employeeActivities.get(0).getActivity().getId();

        Map<Integer, EmployeeActivity> map = new HashMap<Integer, EmployeeActivity>();
        List<EmployeeActivity> existing = repository.getEmployeeActivitiesByActivityId(activityId);
        if (existing != null) {
            for (EmployeeActivity ea : existing) {
                map.put(ea.getEmployee().getId(), ea);
            }
        }
        for (EmployeeActivity employeeActivity : employeeActivities) {
            if (employeeActivity.getEmployee() != null && employeeActivity.getEmployee().getId() != null) {
                employeeActivity.setId(null);
                EmployeeActivity temp = map.get(employeeActivity.getEmployee().getId());
                if (temp != null) {
                    employeeActivity.setId(temp.getId());
                    map.remove(employeeActivity.getEmployee().getId());
                }
                repository.saveEmployeeActivity(employeeActivity);
            }
        }
        if (map.size() > 0) {
            for (Integer key : map.keySet()) {
                repository.deleteEmployeeActivitiesById(map.get(key).getId());
            }
        }
    }

    @Override
    public void deleteEmployeeActivity(Integer id) {
        repository.deleteEmployeeActivitiesById(id);
    }

    @Override
    public List<Timesheet> getTimesheetsWithPager(TPager pager, Integer empActivityId) {
        int first = (pager.getPage() - 1) * pager.getPerPage();
        long total = repository.countTimesheets(empActivityId);
        pager.setRecords((int) total);
        pager.setTotalPages(total % pager.getPerPage() == 0 ? (int) total / pager.getPerPage() : (int) total / pager.getPerPage() + 1);

        return repository.getTimesheets(first, pager.getPerPage(), empActivityId);
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivitiesWithPager(TPager pager) {
        int first = (pager.getPage() - 1) * pager.getPerPage();
        long total = repository.countEmployeeActivities(TUtils.getAuthenticatedUser().getEmployee());
        pager.setRecords((int) total);
        pager.setTotalPages(total % pager.getPerPage() == 0 ? (int) total / pager.getPerPage() : (int) total / pager.getPerPage() + 1);

        return repository.getEmployeeActivities(first, pager.getPerPage(), TUtils.getAuthenticatedUser().getEmployee());
    }

    @Override
    public void saveTimesheet(Timesheet timesheet) {
        repository.saveTimesheet(timesheet);
    }

    @Override
    public void deleteTimesheet(Integer id) {
        repository.deleteTimesheet(id);
    }

    @Override
    public Timesheet getTimesheet(Integer id) {
        return repository.getTimesheet(id);
    }

    @Override
    public byte[] generateReport() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        Map<String, Float[]> result = new LinkedHashMap<String, Float[]>();

        List<EmployeeActivity> employeeActivities = repository.getEmployeeActivities();

        for (EmployeeActivity ea : employeeActivities) {
            List<Timesheet> timesheets = repository.getTimesheets(ea.getId());
            for (Timesheet ts : timesheets) {
                Float[] effort = result.get(ts.getEmployeeActivity().getEmployee().getName());
                if (effort == null) {
                    effort = new Float[] {0f, 0f};
                    result.put(ts.getEmployeeActivity().getEmployee().getName(), effort);
                }
                if (ts.getHours() != null) effort[0] += ts.getHours();
                effort[1] += ts.getHours() * ea.getActivity().getValue();
            }
        }

        int rownum = 0;

        // capul de tabel

        int cellnum = 0;
        HSSFRow row = sheet.createRow(rownum++);
        HSSFCell cell = row.createCell(cellnum++);
        cell.setCellValue("NUME ANGAJAT");

        cell = row.createCell(cellnum++);
        cell.setCellValue("ORE LUCRATE");

        cell = row.createCell(cellnum++);
        cell.setCellValue("VENIT DIN ACTIVITATI");

        for (String key : result.keySet()) {
            Float[] data = result.get(key);
            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            cell.setCellValue(key);

            cell = row.createCell(cellnum++);
            cell.setCellValue(data[0]);

            cell = row.createCell(cellnum++);
            cell.setCellValue(data[1]);
        }
        byte[] bytes = null;
      try {
            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();
            workbook.write(out);
            out.close();
            bytes = out.toByteArray();
            System.out.println("Excel written successfully..");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public List<TSStatisticsBean> getTSStatistics(Integer activityId) {
        List<TSStatisticsBean> result = new ArrayList<TSStatisticsBean>();

        List<EmployeeActivity> employeeActivities = repository.getEmployeeActivitiesByActivityId(activityId);

        for (EmployeeActivity ea : employeeActivities) {
            TSStatisticsBean bean = new TSStatisticsBean();
            bean.setEmployeeName(ea.getEmployee().getName());
            bean.setStart(ea.getStart().getTime());
            bean.setEnd(ea.getEnd().getTime());

            result.add(bean);
        }

        return result;
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = repository.getEmployee(id);
        employee.setActive(false);
        repository.saveEmployee(employee);
    }

    @Override
    public List<Employee> getEmployeesForCompany() {
        return repository.getAllEmployeesForCompany(TUtils.getAuthenticatedUser().getCompany());
    }

    @Override
    public void savePhone(Phone phone) {
        repository.savePhone(phone);
    }

    @Override
    public Category getCategoryByName(String categName) {
        return repository.getCategoryByName(categName);
    }

    @Override
    public void saveCategory(Category category) {
        repository.saveCategory(category);
    }

    @Override
    public Phone getPhoneByURL(String modelURL) {
        return repository.getPhoneByURL(modelURL);
    }

    @Override
    public List<Phone> getPhones(Integer page) {
        int start = page * 30;
        return repository.getPhones(start, 10);
    }

    @Override
    public Long countPhones() {
        return repository.countPhones();
    }
}
