package ro.tekin.disertatie.util;

import ro.tekin.disertatie.bean.NewMessageFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tekin on 2/11/14.
 */
public class TContext {
    private static TContext context;
    private Map<Integer, String> roleMap = null;
    private Map<NewMessageFlag, Boolean> messages = new HashMap<NewMessageFlag, Boolean>();
    private Map<Integer, List<Thread>> threadMap = new HashMap<Integer, List<Thread>>();

    private TContext() {}

    public static TContext getInstance() {
        if (context == null) {
            context = new TContext();
        }
        return context;
    }

    public Map<Integer, String> getRoleMap() {
        if (roleMap == null) {
            roleMap = new HashMap<Integer, String>();
            roleMap.put(TConstants.ROLE_ADMIN, TConstants.ROLE_ADMIN_S);
            roleMap.put(TConstants.ROLE_USER, TConstants.ROLE_USER_S);
            roleMap.put(TConstants.ROLE_COMPANY, TConstants.ROLE_COMPANY_S);
        }
        return roleMap;
    }

    public synchronized void setNewMessage(Integer chatRoomId, Boolean flag) {
        this.messages.put(new NewMessageFlag(chatRoomId, TUtils.getAuthenticatedUser().getEmployee()), flag);
    }

    public synchronized Boolean hasNewMessage(Integer chatRoomId) {
        return Boolean.TRUE.equals(messages.get(new NewMessageFlag(chatRoomId, TUtils.getAuthenticatedUser().getEmployee())));
    }

    public void setNewMessageForChat(Integer chatId) {
        for (NewMessageFlag key : this.messages.keySet()) {
            if (key.getChatRoomId().equals(chatId)) {
                this.messages.put(key, true);
            }
        }
    }

    public synchronized void addMsgThread(Integer chatId, Thread thread) {
        List<Thread> threads = this.threadMap.get(chatId);

        if (threads == null) {
            threads = new ArrayList<Thread>();
        }

        threads.add(thread);
        this.threadMap.put(chatId, threads);
    }

    public synchronized List<Thread> getThreads(Integer chatId) {
        return this.threadMap.get(chatId);
    }
}
