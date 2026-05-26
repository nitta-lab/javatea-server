package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.User;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.nittalab.javateaserver.repositories.TimetableRepository;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.ArrayList;

@Path("/users")
@Component
public class UserTimetableResource {

    private final UserRepository userRepository;
    private final TimetableRepository timetableRepository;
    private final LectureRepository lectureRepository;

    @Autowired
    public UserTimetableResource(UserRepository userRepository, TimetableRepository timetableRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.timetableRepository = timetableRepository;
        this.lectureRepository = lectureRepository;
    }

    // ユーザが時間割登録した年度(year)一覧を取得
    @GET
    @Path("/{uid}/timetable")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Integer> getYears(@PathParam("uid") String uid, @QueryParam("token") String token) {
        // ユーザの存在チェック・トークンチェック
        checkUser(uid, token);

        // 年度取得 ok
        return timetableRepository.getYears(uid);
    }

    // 指定した年度に登録している授業ID(lecture-id)一覧を取得
    @GET
    @Path("/{uid}/timetable/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getLectures(@PathParam("uid") String uid, @PathParam("year") int year, @QueryParam("token") String token) {
        // ユーザの存在チェック・トークンチェック
        checkUser(uid, token);

        // 授業ID一覧の取得・年度の存在確認 ok
        ArrayList<String> lectures = timetableRepository.getLectureIds(uid, year);
        if (lectures == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 年度が存在しません")
                            .build()
            );
        }
        return lectures;
    }

    // ユーザが、指定した年度を追加
    @PUT
    @Path("/{uid}/timetable/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addYear(@PathParam("uid") String uid, @PathParam("year") int year, @FormParam("token") String token) {
        // ユーザの存在チェック・トークンチェック
        checkUser(uid, token);

        // 年度追加 ok
        timetableRepository.createTimetable(uid, year);
    }

    // 指定した年度(year)に指定した授業ID(lecture-id)を追加
    @PUT
    @Path("/{uid}/timetable/{year}/{lecture-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addLecture(@PathParam("uid") String uid, @PathParam("year") int year, @PathParam("lecture-id") String lectureId, @FormParam("token") String token) {
        // ユーザの存在チェック・トークンチェック
        checkUser(uid, token);

        // 授業の存在チェック ok
        checkLecture(lectureId);

        // 授業ID追加 ok
        if (!timetableRepository.addLectureId(uid, year, lectureId)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 年度が存在しません")
                            .build()
            );
        }
    }

    // 指定した年度(year)の指定した授業ID(lecture-id)を削除
    @DELETE
    @Path("/{uid}/timetable/{year}/{lecture-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void removeYear(@PathParam("uid") String uid, @PathParam("year") int year, @PathParam("lecture-id") String lectureId, @FormParam("token") String token) {
        // ユーザの存在チェック・トークンチェック
        checkUser(uid, token);

        // 授業の存在チェック ok
        checkLecture(lectureId);

        // 授業IDの削除 ok
        if (!timetableRepository.deleteLectureId(uid, year, lectureId)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 年度が存在しません")
                            .build()
            );
        }
    }

    // ユーザの存在チェック・トークンチェック
    private void checkUser(String uid, String token) {
        User user = userRepository.getUser(uid);
        if (uid == null || user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 ユーザが存在しません")
                            .build()
            );
        }
        if (token == null || !token.equals(user.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("403 認証失敗")
                            .build()
            );
        }
    }

    // 授業の存在チェック
    private void checkLecture(String lectureId) {
        if (lectureId == null || lectureRepository.getLecture(lectureId) == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 授業が存在しません")
                            .build()
            );
        }
    }
}