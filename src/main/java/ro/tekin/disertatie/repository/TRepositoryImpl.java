package ro.tekin.disertatie.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.tekin.disertatie.bean.MessBean;
import ro.tekin.disertatie.entity.*;
import ro.tekin.disertatie.util.paging.TPager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tekin on 2/11/14.
 */
@Repository
public class TRepositoryImpl implements TRepository {

    @PersistenceContext
    transient EntityManager entityManager;

    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveException(TException exception) {
        if (exception.getId() != null) {
            entityManager.merge(exception);
        } else {
            entityManager.persist(exception);
        }
    }

    @Override
    public TException getExceptionById(Integer id) {
        return entityManager.find(TException.class, id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public List<TException> getAllExceptions() {
        return entityManager.createQuery("select e from TException e", TException.class).getResultList();
    }

    @Override
    public List<TException> getExceptions(int first, TPager pager) {
        StringBuilder sb = new StringBuilder("select e from TException e");
        if (pager.getSidx() != null && pager.getSord() != null) {
            sb.append(" order by ").append(pager.getSidx()).append(" ").append(pager.getSord());
        }
        return entityManager.createQuery(sb.toString(), TException.class).setFirstResult(first).setMaxResults(pager.getPerPage()).getResultList();
    }

    @Override
    public long countExceptions() {
        return entityManager.createQuery("select count(e) from TException e", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public void saveCompany(Company company) {
        if (company.getId() == null) {
            entityManager.persist(company);
        } else {
            entityManager.merge(company);
        }
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        return entityManager.find(Company.class, companyId);
    }

    @Override
    public Company getCompanyByUserId(Integer userId) {
        TypedQuery<Company> query = entityManager.createQuery("select c from Company c where c.user.id=:userId", Company.class);
        query.setParameter("userId", userId);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveTFile(TFile logo) {
        if (logo.getId() == null) {
            entityManager.persist(logo);
        } else {
            entityManager.merge(logo);
        }
    }

    @Override
    public TFile getTFile(Integer id) {
        return entityManager.find(TFile.class, id);
    }

    @Override
    public Employee getEmployee(Integer id) {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where id=:id and e.active=true", Employee.class);
        query.setParameter("id", id);
        List<Employee> employees = query.getResultList();
        if (employees != null && employees.size() == 1) {
            return employees.get(0);
        }
        return null;
    }

    @Override
    public long countEmployees() {
        return entityManager.createQuery("select count(e) from Employee e", Long.class).getSingleResult();
    }

    @Override
    public List<Employee> getEmployees(int first, int maxResults, Integer companyId) {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where active=true and e.company.id = :companyId", Employee.class);
        query.setParameter("companyId", companyId);
        return query.setFirstResult(first).setMaxResults(maxResults).getResultList();
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
        } else {
            entityManager.merge(employee);
        }
    }

    @Override
    public Employee getEmployeeByUserId(Integer userId) {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where user.id = :userId and active=true", Employee.class);
        query.setParameter("userId", userId);
        List<Employee> employees = query.getResultList();
        if (employees != null && employees.size() > 0) {
            return employees.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Position> getPositionsForCompany(Integer companyId) {
        TypedQuery<Position> query = entityManager.createQuery("select p from Position p where p.createdBy.id = :companyId and active=true", Position.class);
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void savePosition(Position position) {
        if (position.getId() == null) {
            entityManager.persist(position);
        } else {
            entityManager.merge(position);
        }
    }

    @Override
    public Position getPosition(Integer id, Integer createdBy) {
        StringBuilder sb = new StringBuilder("select p from Position p where id=:id");
        if (createdBy != null) {
            sb.append(" and createdBy.id = :createdBy");
        }
        TypedQuery<Position> query = entityManager.createQuery(sb.toString(), Position.class);
        query.setParameter("id", id);
        if (createdBy != null) {
            query.setParameter("createdBy", createdBy);
        }

        List<Position> positions = query.getResultList();
        if(positions != null && positions.size() == 1) {
            return positions.get(0);
        }
        return  null;
    }

    @Override
    public List<Employee> getAllEmployeesForCompany(Integer companyId) {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.company.id = :companyId and e.active=true", Employee.class);
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void saveOrganigram(Organigram organigram) {
        if (organigram.getId() == null) {
            entityManager.persist(organigram);
        } else {
            entityManager.merge(organigram);
        }
    }

    @Override
    public Organigram getOrganigramByCompanyId(Integer company) {
        TypedQuery<Organigram> query = entityManager.createQuery("select o from Organigram o where o.company.id = :company", Organigram.class);
        query.setParameter("company", company);
        List<Organigram> result = query.getResultList();
        if (result != null && result.size() == 1) {
            return result.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void saveOrganigramElement(OrganigramElement elem) {
        if (elem.getId() == null) {
            entityManager.persist(elem);
        } else {
            entityManager.merge(elem);
        }
    }

    @Override
    @Transactional
    public void deleteOrganigramElements(Integer id) {
        Query query = entityManager.createQuery("delete from OrganigramElement o where o.organigram.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<OrganigramElement> getOrganigramElements(Integer organigramId) {
        TypedQuery<OrganigramElement> query = entityManager.createQuery("select o from OrganigramElement o where o.organigram.id = :organigramId", OrganigramElement.class);
        query.setParameter("organigramId", organigramId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteAllChatRooms(Integer companyId) {
        Query query = entityManager.createQuery("delete from ChatRoom o where o.company.id = :id");
        query.setParameter("id", companyId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void saveChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getId() == null) {
            entityManager.persist(chatRoom);
        } else {
            entityManager.merge(chatRoom);
        }
    }

    @Override
    @Transactional
    public void saveChatMember(ChatMember cm) {
        if (cm.getId() == null) {
            entityManager.persist(cm);
        } else {
            entityManager.merge(cm);
        }
    }

    @Override
    public List<TFile> getProfilePictures() {
        List<TFile> result = new ArrayList<TFile>();
        List<TFile> temp = new ArrayList<TFile>();

        TypedQuery<TFile> query = entityManager.createQuery("select t from Employee e left join e.photo t where t.resized = false", TFile.class);

        temp = query.getResultList();
        if (temp != null && temp.size() > 0) {
            result.addAll(temp);
        }

        query = entityManager.createQuery("select t from Company e left join e.logo t where t.resized = false", TFile.class);

        temp = query.getResultList();
        if (temp != null && temp.size() > 0) {
            result.addAll(temp);
        }

        return result;
    }

    @Override
    @Transactional
    public void saveTMessage(TMessage tMessage) {
        if (tMessage.getId() == null) {
            entityManager.persist(tMessage);
            entityManager.flush();
        } else {
            entityManager.merge(tMessage);
            entityManager.flush();
        }
    }

    @Override
    public List<ChatRoom> getAllChatRooms(Integer user) {
        TypedQuery<ChatRoom> query = entityManager.createQuery("select c from ChatMember cm left join cm.chatRoom c where cm.employee.id = :emplId", ChatRoom.class);
        query.setParameter("emplId", user);
        return query.getResultList();
    }

    @Override
    public List<MessBean> getMessage(Integer last, Integer chatRoom) {
        Query query = entityManager.createQuery("select t.id, e.name, t.text from TMessage t left join t.employee e where t.chatRoom.id = :chatRoom and t.id > :last");
        query.setParameter("chatRoom", chatRoom);
        query.setParameter("last", last);

        List<Object[]> result = query.getResultList();
        List<MessBean> messages = null;

        if (result != null && result.size() > 0) {
            messages = new ArrayList<MessBean>();
            for (Object[] row : result) {
                messages.add(new MessBean(
                        (Integer)row[0],
                        (String) row[1],
                        (String) row[2]
                ));
            }
        }
        return messages;
    }

    @Override
    public Activity getActivity(Integer id) {
        TypedQuery<Activity> query = entityManager.createQuery("select e from Activity e where active = true and id=:id", Activity.class);
        query.setParameter("id", id);
        List<Activity> list = query.getResultList();

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public long countActivities(Integer companyId) {
        TypedQuery<Long> query = entityManager.createQuery("select count(e) from Activity e where e.company.id = :companyId and active=true", Long.class);
        query.setParameter("companyId", companyId);
        return query.getSingleResult();
    }

    @Override
    public List<Activity> getActivities(int first, int maxResults, Integer companyId) {
        TypedQuery<Activity> query = entityManager.createQuery("select e from Activity e where active=true and e.company.id = :companyId", Activity.class);
        query.setParameter("companyId", companyId);
        return query.setFirstResult(first).setMaxResults(maxResults).getResultList();
    }

    @Override
    @Transactional
    public void saveActivity(Activity activity) {
        if (activity.getId() == null) {
            entityManager.persist(activity);
        } else {
            entityManager.merge(activity);
        }
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivitiesByEmployeeId(Integer employeeId) {
        TypedQuery<EmployeeActivity> query = entityManager.createQuery("select e from EmployeeActivity e where e.employee.id=:employeeId", EmployeeActivity.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivitiesByActivityId(Integer activityId) {
        TypedQuery<EmployeeActivity> query = entityManager.createQuery("select e from EmployeeActivity e where e.activity.id=:activityId", EmployeeActivity.class);
        query.setParameter("activityId", activityId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteEmployeeActivitiesByActivityId(Integer id) {
        Query query = entityManager.createQuery("delete from EmployeeActivity o where o.activity.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void saveEmployeeActivity(EmployeeActivity employeeActivity) {
        if (employeeActivity.getId() != null) {
            entityManager.merge(employeeActivity);
        } else {
            entityManager.persist(employeeActivity);
        }
    }

    @Override
    public EmployeeActivity getEmployeeActivitiesById(Integer id) {
        return entityManager.find(EmployeeActivity.class, id);
    }

    @Override
    @Transactional
    public void deleteEmployeeActivitiesById(Integer id) {
        Query query = entityManager.createQuery("delete from EmployeeActivity o where o.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
     public List<EmployeeActivity> getEmployeeActivities(int first, Integer maxResults, Integer employeeId) {
        TypedQuery<EmployeeActivity> query = entityManager.createQuery("select e from EmployeeActivity e where e.employee.id = :employeeId", EmployeeActivity.class);
        query.setParameter("employeeId", employeeId);
        return query.setFirstResult(first).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<EmployeeActivity> getEmployeeActivities() {
        TypedQuery<EmployeeActivity> query = entityManager.createQuery("select e from EmployeeActivity e", EmployeeActivity.class);
        return query.getResultList();
    }

    @Override
    public long countEmployeeActivities(Integer employeeId) {
        TypedQuery<Long> query = entityManager.createQuery("select count(e) from EmployeeActivity e where e.employee.id = :employeeId", Long.class);
        query.setParameter("employeeId", employeeId);
        return query.getSingleResult();
    }

    @Override
    public long countTimesheets(Integer empActivityId) {
        TypedQuery<Long> query = entityManager.createQuery("select count(e) from Timesheet e where e.employeeActivity.id = :empActivityId", Long.class);
        query.setParameter("empActivityId", empActivityId);
        return query.getSingleResult();
    }

    @Override
    public List<Timesheet> getTimesheets(int first, Integer maxResults, Integer empActivityId) {
        TypedQuery<Timesheet> query = entityManager.createQuery("select e from Timesheet e where e.employeeActivity.id = :empActivityId", Timesheet.class);
        query.setParameter("empActivityId", empActivityId);
        return query.setFirstResult(first).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<Timesheet> getTimesheets(Integer empActivityId) {
        TypedQuery<Timesheet> query = entityManager.createQuery("select e from Timesheet e where e.employeeActivity.id = :empActivityId", Timesheet.class);
        query.setParameter("empActivityId", empActivityId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void saveTimesheet(Timesheet timesheet) {
        if (timesheet.getId() != null) {
            entityManager.merge(timesheet);
        } else {
            entityManager.persist(timesheet);
        }
    }

    @Override
    @Transactional
    public void deleteTimesheet(Integer id) {
        Query query = entityManager.createQuery("delete from Timesheet o where o.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Timesheet getTimesheet(Integer id) {
        return entityManager.find(Timesheet.class, id);
    }

    @Override
    public List<Timesheet> getTimesheetsByActivity(Integer activityId) {
        TypedQuery<Timesheet> query = entityManager.createQuery("select e from Timesheet e where e.employeeActivity.activity.id = :activityId", Timesheet.class);
        query.setParameter("activityId", activityId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void savePhone(Phone phone) {
        if (phone.getId() != null) {
            entityManager.merge(phone);
        } else {
            entityManager.persist(phone);
        }
    }

    @Override
    public Category getCategoryByName(String categName) {
        TypedQuery<Category> query = entityManager.createQuery("select e from Category e where e.name = :name", Category.class);
        query.setParameter("name", categName);

        List<Category> categories = query.getResultList();
        if (categories != null && categories.size() > 0) {
            return categories.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void saveCategory(Category category) {
        if (category.getId() != null) {
            entityManager.merge(category);
        } else {
            entityManager.persist(category);
        }
    }

    @Override
    public Phone getPhoneByURL(String url) {
        TypedQuery<Phone> query = entityManager.createQuery("select e from Phone e where e.url = :url", Phone.class);
        query.setParameter("url", url);

        List<Phone> phones = query.getResultList();
        if (phones != null && phones.size() > 0) {
            return phones.get(0);
        }
        return null;
    }

    @Override
    public List<Phone> getPhones(int start, int max) {
        return entityManager.createQuery("select e from Phone e", Phone.class).setFirstResult(start).setMaxResults(max).getResultList();
    }

    @Override
    public Long countPhones() {
        return entityManager.createQuery("select count(e) from Phone e", Long.class).getSingleResult();

    }
}
