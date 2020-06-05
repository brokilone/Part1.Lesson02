package Task15.Model;

import Task15.Dao.User.UserDaoImpl;
import Task15.Model.UserInfo.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ArticleAccess
 * варианты уровня доступа к статье
 * created by Ksenya_Ushakova at 31.05.2020
 */
public enum ArticleAccess {
    //открытый/ только для авторов(юзеры с рейтингом >=0)/ по списку

    OPEN,
    AVAILABLE_TO_AUTHORS,
    AVAILABLE_TO_LIST;

    private List<User> list = new ArrayList<>();

    /**
     * Строковое представление полей объекта
     * @return String
     */
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

    /**
     * Получение объекта по строке - результату доступа к БД
     * @param name - значение из БД
     * @return ArticleAccess
     */
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

    /**
     * Установка списка пользователей, имеющих доступ
     * применимо к открытому доступу или доступу для авторов
     * @throws SQLException
     */
    public void setList() throws SQLException {
        switch (this) {
            case OPEN:
                list = new UserDaoImpl().getAllUsers();
                break;
            case AVAILABLE_TO_AUTHORS:
                list = new UserDaoImpl().getAllAuthors();
                break;
        }
    }

    /**
     /**
     * Установка списка пользователей, имеющих доступ
     * применимо к доступу по списку
     * @param logins - массив логинов пользователей
     * @throws SQLException
     */
    public void setList(String[] logins) throws SQLException {
       if (this == AVAILABLE_TO_LIST)
       list = new UserDaoImpl().getGroupByLogins(logins);
    }
    public List<User> getList(){
        return list;
    }

}
