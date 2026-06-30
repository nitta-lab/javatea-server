package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import org.nittalab.javateaserver.models.FriendPair;
//import org.nittalab.javateaserver.models.UserDTO;
import org.nittalab.javateaserver.repositories.TimetableRepository;
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

import java.time.LocalDate;

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
    private final TimetableRepository timetableRepository;

    @Autowired
    public UserResource(UserRepository userRepository, TimetableRepository timetableRepository) {
        //インスタンスを作るときに呼び出されるメソッドであるコンストラクタを書く
        this.userRepository = userRepository;
        this.timetableRepository = timetableRepository;
    }

    //@Path("/{uid}/..")などパスを指定する
    // 実際はおそらく叩かないけども、確認用で置いてるもの(叩けてしまうとセキュリティの問題出てしまう)
    // パスワードやtokenについて、Userにある@JsonIgnoreを消すと見えるようになる
    @GET
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public User getUser(@PathParam("uid") String userId) {
        //取得
        User user = userRepository.getUser(userId);
        //存在の確認
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return user;
    }



    //新規アカウントを登録、重複許さない状態なのでおそらくセキュリティ問題ないはず
    // (前回と同じIDでもう一度作ろうとすると、重複と出るはず)
    @PUT
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public User createUser(@PathParam("uid") String uid, @FormParam("name") String name, @FormParam("pw") String pw) {

        // 400 リクエスト方式が不正
        if (isBlank(uid) || isBlank(name) || isBlank(pw)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("ユーザーID,ニックネームおよびパスワードを入力してください")
                            .build());
        }

        // 409 ユーザーIDが重複していないかか調べる　
        if (userRepository.checkDuplicate(uid)) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                                                      .entity("ユーザが重複しています")
                                                      .build()
            );
        }


        // ここがいらない可能性あり、userが追加できなくなる
//        //取得
//        User user = userRepository.getUser(uid);
//        // 404 ユーザが存在しません
//        //存在チェック
//        if (user == null) {
//            throw new WebApplicationException(
//                    Response.Status.NOT_FOUND
//            );
//        }

        timetableRepository.createTimetable(uid, LocalDate.now().getYear());
        // 200 正常にユーザID登録、uid,name,pwはここで記憶される
        return userRepository.createUser(uid, name, pw);
    }



    //ログイン
    @POST
    @Path("/{uid}/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public User login(@PathParam("uid") String uid, @FormParam("pw") String pw) {

        // 400 不正なリクエスト(pw 必須)
        if (pw == null || pw.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("pw は必須です")
                            .build());
        }

        //404 存在チェック
        User user = userRepository.getUser(uid);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 401 パスワードチェック 適切なステータスはなんですか？
        if (!pw.equals(user.getPw())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("パスワードが間違っています")
                            .build()
            );
        }

        // トークン発行成功　tokenはここで記憶される
        String token = userRepository.createToken(uid);
        user.setToken(token);

        // Userクラスを渡すのに成功
        return user;
    }

    // ログアウト時にTokenをnullで上書きしてそのユーザーがTokenを持ってない状態に戻す
    @PUT
    @Path("{uid}/login")
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteToken(@PathParam("uid") String uid, @FormParam("token") String token) {
        User user = userRepository.getUser(uid);

        // 404 ユーザが存在しません
        //存在チェック
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

        userRepository.deleteToken(uid);
    }


    //ユーザの大学の取得
    @GET
    @Path("/{uid}/university")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUniversity(@PathParam("uid") String uid, @QueryParam("token") String token) {
        //取得
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

        // 200　自分の大学返す　
        return user.getUniversity();
    }

    //ユーザの大学の登録
    @PUT
    @Path("/{uid}/university")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateUniversity(@PathParam("uid") String uid, @FormParam("university") String university, @FormParam("token") String token) {

        // 400 リクエスト方式が不正です
        if(university == null || university.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("大学名を入力してください")
                            .build());
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

        // 200　正常に自分の大学登録
        user.setUniversity(university);
    }


    //ユーザの学部の取得
    @GET
    @Path("/{uid}/faculty")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFaculty(@PathParam("uid") String uid, @QueryParam("token") String token) {
        //取得
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

        // 200 自分の学部返す
        return user.getFaculty();
    }


    //ユーザの学部の登録
    @PUT
    @Path("/{uid}/faculty")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateFaculty(@PathParam("uid") String uid, @FormParam("faculty") String faculty, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(faculty == null || faculty.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("学部名を入力してください")
                            .build());
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
        user.setFaculty(faculty);
    }


    //ユーザの学科の取得
    @GET
    @Path("/{uid}/department")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDepartment(@PathParam("uid") String uid, @QueryParam("token") String token) {

        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
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

        // 200 自分の学科を返す
        return user.getDepartment();


    }

    //ユーザの学科の登録
    @PUT
    @Path("/{uid}/department")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateDepartment(@PathParam("uid") String uid, @FormParam("department") String department, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(department == null || department.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("学科名を入力してください")
                            .build());
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
        user.setDepartment(department);
    }


    //ユーザの学年の取得
    @GET
    @Path("/{uid}/grade")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getGrade(@PathParam("uid") String uid, @QueryParam("token") String token) {
        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
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

        // 200 自分の学年を返す
        return user.getGrade();
    }

    //ユーザの学年の登録
    @PUT
    @Path("/{uid}/grade")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateGrade(@PathParam("uid") String uid, @FormParam("grade") Integer grade, @FormParam("token") String token) {

        // 400 リクエスト方式が不正
        if(grade == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("学年を入力してください")
                            .build());
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
        user.setGrade(grade);
    }



    //アカウントのニックネームの取得 ok
    @GET
    @Path("/{uid}/name")
    @Produces(MediaType.TEXT_PLAIN)
    public String getName(@PathParam("uid") String uid, @QueryParam("token") String token) {

        // 404 ユーザが存在しません
        //取得
        User user = userRepository.getUser(uid);
        //存在チェック
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

        // 200 自分のニックネームを返す
        return user.getName();
    }
}
