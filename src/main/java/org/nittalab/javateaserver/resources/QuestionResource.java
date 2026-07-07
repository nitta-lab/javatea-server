package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.User;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.nittalab.javateaserver.util.PermissionChecker;
import org.nittalab.javateaserver.models.Question;
import org.nittalab.javateaserver.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/questions")
@Component
public class QuestionResource {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuestionResource(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    //質問を新しく作成する
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createQuestion(
            @FormParam("title") String title,
            @FormParam("body") String body,
            @FormParam("uid") String uid,
            @FormParam("tags") List<String> tags,
            @FormParam("view-permission") String viewPermission,
            @FormParam("res-permission") String resPermission)
    {
        // 400 不正なリクエスト
        if (title == null || title.isEmpty()
                || body == null || body.isEmpty()
                || uid == null || uid.isEmpty()
                || tags == null || tags.isEmpty()
                || viewPermission == null || viewPermission.isEmpty()
                || resPermission == null || resPermission.isEmpty()) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // 201 作成成功
        return questionRepository.createQuestion(title, body, uid, tags, viewPermission, resPermission);

//        // 404 データが存在しない　→　ここではエラー404は必要ない
//        // 500 予期せぬエラー　→　ここではエラー500は必要ない
    }

    //質問情報の取得
    @Path("/{qid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestion(
            @PathParam("qid") String qid,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token)
    {
        // 認証
        User requester = authenticate(requesterUid, token);

        Question question = questionRepository.getQuestion(qid);
        // 404
        if (question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("質問が存在しません。")
                            .build()
            );
        }

        // 403 閲覧権限がない
        if (!PermissionChecker.hasPermission(
                question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("この質問を閲覧する権限がありません。")
                            .build()
            );
        }

        // 200
        return question;
    }

    //質問タイトルを取得する
    @Path("/{qid}/title")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTitle(
            @PathParam("qid") String qid,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token)
    {
        // 認証
        User requester = authenticate(requesterUid, token);

        // Repositoryから取得
        Question question = questionRepository.getQuestion(qid);
        // 404
        if (question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("質問が存在しません。")
                            .build()
            );
        }

        // 403 閲覧権限がない
        if (!PermissionChecker.hasPermission(
                question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("この質問を閲覧する権限がありません。")
                            .build()
            );
        }

        // 200 成功
        return question.getTitle();
    }

    //ユーザーIDを取得する。
    @Path("/{qid}/uid")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUid(
            @PathParam("qid") String qid,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token)
    {
        // 認証
        User requester = authenticate(requesterUid, token);

        // Repositoryから取得
        Question question = questionRepository.getQuestion(qid);
        // 404
        if (question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("質問が存在しません。")
                            .build()
            );
        }

        // 403 閲覧権限がない
        if (!PermissionChecker.hasPermission(
                question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("この質問を閲覧する権限がありません。")
                            .build()
            );
        }

        // 200 成功
        return question.getUid();
    }

    /**
     * リクエストしてきたユーザーが本人かどうかをtokenで確認する
     * (UserResourceの各エンドポイントと同じ認証パターン)
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