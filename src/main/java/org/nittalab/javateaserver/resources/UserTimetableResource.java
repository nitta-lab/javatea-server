package org.nittalab.javateaserver.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

@Path("/users")
@Component
public class UserTimetableResource {

//    private final UserRepository userRepository;
//    private final TimetableRepository timetableRepository;
//    private final LectureRepository lectureRepository;
//
//    @Autowired
//    public UserTimetableResource(UserRepository userRepository, TimetableRepository timetableRepository, LectureRepository lectureRepository){
//        this.userRepository = userRepository;
//        this.timetableRepository = timetableRepository;
//        this.lectureRepository = lectureRepository;
//    }

    // ユーザが時間割登録した年度(year)一覧を取得
    @GET
    @Path("/{uid}/timetable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYears(@PathParam("uid") String uid, @QueryParam("token") String token) {
//        // ユーザの存在チェック
//        User user = userRepository.getUser(uid);
//        if(uid == null || user == null){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("404 ユーザが存在しません")
//                            .build()
//            );
//        }
//        // トークンチェック
//        if(token == null || !token.equals(user.getToken())){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.FORBIDDEN)
//                            .entity("403 認証失敗")
//                            .build()
//            );
//        }
        return Response.ok().build();
    }

    // 指定した年度に登録している授業ID(lecture-id)一覧を取得
    @GET
    @Path("/{uid}/timetable/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLectuers(@PathParam("uid") String uid, @PathParam("year") String year, @QueryParam("token") String token) {
        return Response.ok().build();
    }

    // ユーザが、指定した年度を追加
    @PUT
    @Path("/{uid}/timetable/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putYear(@PathParam("uid") String uid, @PathParam("year") String year, @FormParam("token") String token) {
        return Response.ok().build();
    }

    // 指定した年度(year)に指定した授業ID(lecture-id)を追加
    @PUT
    @Path("/{uid}/timetable/{year}/{lecture-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putLecture(@PathParam("uid") String uid, @PathParam("year") String year,@PathParam("lecture-id") String lectureId, @FormParam("token") String token) {
        return Response.ok().build();
    }

    // 指定した年度(year)の指定した授業ID(lecture-id)を削除
    @DELETE
    @Path("/{uid}/timetable/{year}/{lecture-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putYear(@PathParam("uid") String uid, @PathParam("year") String year,@PathParam("lecture-id") String lectureId, @FormParam("token") String token) {
        return Response.ok().build();
    }
}
