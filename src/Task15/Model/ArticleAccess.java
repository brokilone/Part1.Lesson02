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
    //открытый/ только для авторов(юзеры с рейтингом >=0)

    OPEN,
    AVAILABLE_TO_AUTHORS;



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
        }
        return null;
    }

    /**
     * Получение объекта по строке - результату доступа к БД
     * @param name - значение из БД
     * @return ArticleAccess
     */
    public static Task15.Model.ArticleAccess getByName(String name){
        switch (name) {
            case "open":
                return OPEN;
            case "available to authors":
                return AVAILABLE_TO_AUTHORS;
        }
        return null;
    }


}
