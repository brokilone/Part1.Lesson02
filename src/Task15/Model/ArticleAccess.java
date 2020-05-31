package Task15.Model;

import Task15.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * AccessLevel
 * created by Ksenya_Ushakova at 31.05.2020
 */
public enum ArticleAccess {
    OPEN(Main.getAllUsers()), AVAILABLE_TO_LIST(new ArrayList<>()),
    AVAILABLE_TO_AUTHORIZED_USERS(Main.getOnlineUsers()),
    AVAILABLE_TO_AUTHORS(Main.getAllAuthors());

    private List<User> list;

    ArticleAccess( List<User> list) {
        this.list = list;
    }


}
