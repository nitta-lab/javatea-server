package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Question;
import org.nittalab.javateaserver.models.User;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.nittalab.javateaserver.repositories.QuestionRepository;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.nittalab.javateaserver.util.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("categories/general/questions")
@Component
public class CategoryGeneralResource {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryGeneralResource(CategoryRepository categoryRepository, QuestionRepository questionRepository,  UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    // 【全般】の質問を取得
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getGeneralQuestions(@QueryParam("uid") String uid, @QueryParam("token") String token) {
        // 認証
        User requester = authenticate(uid, token);

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : categoryRepository.getGeneralQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        return visibleQuestions;
    }

    // 【全般】の質問追加
    @Path("/{qid}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addGeneralQuestion(@PathParam("qid") String qid) {
        Question question = questionRepository.getQuestion(qid);
        if(question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された質問IDが存在しません")
                            .build());
        }
        categoryRepository.addGeneralQuestion(question);
    }

    @Path("categories/general/keyWords/questions")

    // 【全般】の質問を取得
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getGeneralKeyWordsQuestions(@QueryParam("uid") String uid, @QueryParam("token") String token, @QueryParam("keyWords") List<String> keyWords) {
        // 認証
        User requester = authenticate(uid, token);

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : categoryRepository.getGeneralQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        // タグで絞り込み
        Set<Question> visibleKeyWordsQuestions = new HashSet<>();
        for (Question question : visibleQuestions) {
            List<String> tags = question.getTags();
            for (String keyWord :  keyWords) {
                if(keyWord.isEmpty()) {
                    continue;
                }
                if(keyWord.contains(" ") || keyWord.contains("\t") || keyWord.contains("\n") || keyWord.contains("　")) {
                    continue;
                }
                if (tags.contains(keyWord)) {
                    visibleKeyWordsQuestions.add(question);
                }
                if (question.getTitle().contains(keyWord)) {
                    visibleKeyWordsQuestions.add(question);
                }
            }
        }

        return visibleKeyWordsQuestions;
    }

    /**
     * リクエストしてきたユーザーが本人かどうかをtokenで確認する
     * (QuestionResourceの各エンドポイントと同じ認証パターン)
     */
    private User authenticate(String requesterUid, String token) {
        User requester = userRepository.getUser(requesterUid);

        // 404 ユーザが存在しません
        if (requester == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 403 認証失敗
        if (token == null || !token.equals(requester.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        return requester;
    }
}
