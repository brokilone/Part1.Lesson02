package Task15.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Commentator
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Commentator {
    List<Comment> listOfComments = new ArrayList<>();

    int writeComment(Article article, String content);
    boolean editComment(int id, String content);
    boolean deleteComment(int id);
    void rateComment(int id, boolean up);

}
