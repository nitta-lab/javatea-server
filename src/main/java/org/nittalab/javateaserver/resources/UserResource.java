package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import org.nittalab.javateaserver.models.FriendPair;
//import org.nittalab.javateaserver.models.UserDTO;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.nittalab.javateaserver.models.User;
//import org.nittalab.javateaserver.services.FriendService;
//import org.nittalab.javateaserver.utils.Base64Decode;
//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;

//import static io.micrometer.common.util.StringUtils.isBlank;
//import java.util.ArrayList;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Path("/users")
@Component
public class UserResource {

// 先輩のものにあったので一応載せています
//public class UserResource implements ApplicationContextAware {
//    private org.springframework.context.ApplicationContext applicationContext;

//    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) { //インスタンスを作るときに呼び出されるメソッドであるコンストラクタを書く
        this.userRepository = userRepository;
    }

    //@Path("/{uid}/..")などパスを指定する

    //新規アカウントを登録、ここ名前の付け方良くないかも
    @PUT
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createUser(@PathParam("uid") String uid, @FormParam("name") String name, @FormParam("pw") String pw) {

        // 400 リクエスト方式が不正
        if (isBlank(uid) || isBlank(name) || isBlank(pw)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("ユーザーID,ニックネームおよびパスワードを入力してください")
                            .build());
        }

//        // 409 ユーザーIDが重複していないかか調べる
//        ArrayList<String> uidLists = userRepository.getAllUsers();
//        for (String uidList : uidLists) {
//            if (uidList != null && uidList.equals(uid)) {
//                throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity("ユーザが重複しています").build());
//            }
//        }


        //取得
        User user = userRepository.getUser(uid);

        // 404 ユーザが存在しません
        //存在チェック
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

//        // 200 正常にユーザID登録
        User newUser = userRepository.createUser(uid, name, pw);

//        user.setUid(uid);

        // ここ作った返答いる？
        return Response.status(Response.Status.CREATED)
                .entity(newUser)
                .build();
//        return Response.ok().build();
    }



    //ログイン
    @POST
    @Path("/{uid}/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@PathParam("uid") String uid, @FormParam("pw") String pw) {

        // 400 不正なリクエスト
        if (uid == null || uid.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("uid は必須です")
                            .build());
        }

        if (pw == null || pw.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("pw は必須です")
                            .build());
        }

        //存在チェック
        User user = userRepository.getUser(uid);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

//        //パスワードチェック 適切なステータスはなんですか？
//        if (!pw.equals(user.getPassword())) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.UNAUTHORIZED)
//                            .entity("パスワードが間違っています")
//                            .build()
//            );
//        }

        //200 トークン発行成功
        String token = user.getToken();
        return Response.ok(token).build();

    }



    //ユーザの大学の取得
    @GET
    @Path("/{uid}/university")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUniversity(@PathParam("uid") String uid, @QueryParam("token") String token) {
        //取得
        User user = userRepository.getUser(uid);

        // 404 ユーザが存在しません
        //存在チェック
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200　自分の大学返す　
        // まだgetUniversityはnullで返してる状態
//        return Response.ok(user.getUniversity(), MediaType.TEXT_PLAIN).build();

        return null;


    }

    //ユーザの大学の登録
    @PUT
    @Path("/{uid}/university")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateUniversity(@PathParam("uid") String uid, @FormParam("university") String university, @FormParam("token") String token) {

        // 400 リクエスト方式が不正です
        if(university == null || university.isBlank()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        User user = userRepository.getUser(uid);
        // 404 ユーザが存在しません
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

//        // 200　正常に自分の大学登録
//        // setUniversityはまだ
//        user.setUniversity(university);
        return Response.ok().build();

    }


    //ユーザの学部の取得
    @GET
    @Path("/{uid}/faculty")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFaculty(@PathParam("uid") String uid, @QueryParam("token") String token) {
        //取得
        User user = userRepository.getUser(uid);

        // 404 ユーザが存在しません
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学部返す
        // まだgetFacultyはnullで返してる状態
//        return Response.ok(user.getFaculty(), MediaType.TEXT_PLAIN).build();

        return null;


    }


    //ユーザの学部の登録
    @PUT
    @Path("/{uid}/faculty")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateFaculty(@PathParam("uid") String uid, @FormParam("faculty") String faculty, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(faculty == null || faculty.isBlank()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        // 404 ユーザが存在しません
        User user = userRepository.getUser(uid);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学部登録
//        //ニックネームアップデート→ここも学部に変える
//        user.setFaculty(faculty);
        return Response.ok().build();
    }


    //ユーザの学科の取得
    @GET
    @Path("/{uid}/department")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDepartment(@PathParam("uid") String uid, @QueryParam("token") String token) {

        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学科を返す
        // まだgetDepartmentはnullで返してる状態
//        return Response.ok(user.getDepartment(), MediaType.TEXT_PLAIN).build();

        return null;


    }

    //ユーザの学科の登録
    @PUT
    @Path("/{uid}/department")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateDepartment(@PathParam("uid") String uid, @FormParam("department") String department, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(department == null || department.isBlank()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        // 404 ユーザが存在しません
        User user = userRepository.getUser(uid);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学科登録
        //ニックネームアップデート→ここも学部に変える
//        user.setDepartment(department);
        return Response.ok().build();
    }


    //ユーザの学年の取得
    @GET
    @Path("/{uid}/grade")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getGrade(@PathParam("uid") String uid, @QueryParam("token") String token) {
        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学年を返す
        // まだgetFacultyはnullで返してる状態
//        return Response.ok(user.getGrade(), MediaType.TEXT_PLAIN).build();

        return null;
    }

    //ユーザの学年の登録
    @PUT
    @Path("/{uid}/grade")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateGrade(@PathParam("uid") String uid, @FormParam("grade") String grade, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(grade == null || grade.isBlank()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        // 404 ユーザが存在しません
        User user = userRepository.getUser(uid);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分の学年登録
        //ニックネームアップデート→ここも学年に変える
//        user.setGrade(grade);
        return Response.ok().build();
    }



    //アカウントのニックネームの取得 ok
    @GET
    @Path("/{uid}/name")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getName(@PathParam("uid") String uid, @QueryParam("token") String token) {

        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
        if (user == null) {
            throw new WebApplicationException(
                    Response.Status.NOT_FOUND
            );
        }

        // 401 認証エラー
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        // 200 自分のニックネームを返す
//        return Response.ok(user.getName(), MediaType.TEXT_PLAIN).build();

        return null;
    }
}
