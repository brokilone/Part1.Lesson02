package Task15.Model;

import Task15.Dao.ArticleAccess.ArticleAccessDaoImpl;
import Task15.Model.UserInfo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * AccessLevel
 * created by Ksenya_Ushakova at 31.05.2020
 */
public enum ArticleAccess {

    OPEN,
    AVAILABLE_TO_AUTHORS,
    AVAILABLE_TO_LIST;

    private List<User> list = new ArrayList<>();

    @Override
    public String toString() {
        switch (this) {
            case OPEN:
                return "open";
            case AVAILABLE_TO_AUTHORS:
                return "available to authors";
            case AVAILABLE_TO_LIST:
                return "available to list";
        }
        return null;
    }

    public static ArticleAccess getByName(String name){
        switch (name) {
            case "open":
                return OPEN;
            case "available to authors":
                return AVAILABLE_TO_AUTHORS;
            case "available to list":
                return AVAILABLE_TO_LIST;
        }
        return null;
    }

    public void setList(){
        switch (this) {
            case OPEN:
                list = new ArticleAccessDaoImpl().getAllUsers();
                break;
            case AVAILABLE_TO_AUTHORS:
                list = new ArticleAccessDaoImpl().getAllAuthors();
                break;
        }

    }
    public void setList(String logins[]){
        switch (this) {
            case AVAILABLE_TO_LIST:
                list = new ArticleAccessDaoImpl().getGroupByLogins(logins);
        }

    }
    public List<User> getList(){
        return list;
    }


}
