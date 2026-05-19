package org.nittalab.javateaserver.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lectures")
@Component
public class LectureResource {

//    private LectureRepository lecruteRepository = null;
//
//    @Autowired
//    public LectureResource(LectureRepository lecruteRepository) {
//        this.lecruteRepository = lecruteRepository;
//    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLecture(
            @FormParam("name") String name,
            @FormParam("grade") Integer grade,
            @FormParam("semester") String semester,
            @FormParam("frame") Integer frame,
            @FormParam("day") String day,
            @FormParam("period") Integer period)
    {
        // 400 不正なリクエスト
        if (name == null || name.isEmpty()
                || grade == null
                || semester == null || semester.isEmpty()
                || frame == null
                || day == null || day.isEmpty()
                || period == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // 201 作成成功
        String lectureId = lectureRepository.createLecture(name, grade, semester, frame, day, period);
        return Response.status(Response.Status.OK)
                .build();

//        // 404 データが存在しない　→　ここではエラー404は必要ない

//        // 500 予期せぬエラー　→　ここではエラー500は必要ない
    }

    @Path("/{lecture-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLecture(@PathParam("lecture-id") String lectureId)
    {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません")
                    .build();
        }

        String lecture = lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }
        // 200
        return Response.status(Response.Status.OK)
                .entity(lecture)
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putLectureName(
            @PathParam("lecture-id") String lectureId,
            @FormParam("name") String name
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        boolean result =
                lectureRepository.updateLectureName(lectureId, name);
        // 404
        if (!result) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("授業名を変更しました。")
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/name")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLectureName(
            @PathParam("lecture-id") String lectureId
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        String name =
                lectureRepository.getLectureName(lectureId);

        // 404
        if (name == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(name)
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/grade")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public Response putLectureGrade(
            @PathParam("lecture-id") String lectureId,
            @FormParam("grade") Integer grade
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || grade == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        boolean result =
                lectureRepository.updateLectureGrade(lectureId, grade);

        // 404
        if (!result) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("受講可能学年を変更しました。")
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/grade")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLectureGrade(
            @PathParam("lecture-id") String lectureId
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        Integer grade =
                lectureRepository.getLectureGrade(lectureId);

        // 404
        if (grade == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(grade)
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/semester")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public Response putLectureSemester(
            @PathParam("lecture-id") String lectureId,
            @FormParam("semester") String semester
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || semester == null || semester.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        boolean result =
                lectureRepository.updateLectureSemester(lectureId, semester);

        // 404
        if (!result) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("学期区分を変更しました。")
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/semester")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLectureSemester(
            @PathParam("lecture-id") String lectureId
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        String semester =
                lectureRepository.getLectureSemester(lectureId);

        // 404
        if (semester == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(semester)
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/frame")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public Response putLectureFrame(
            @PathParam("lecture-id") String lectureId,
            @FormParam("frame") Integer frame
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || frame == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        boolean result =
                lectureRepository.updateLectureFrame(lectureId, frame);

        // 404
        if (!result) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("コマ数を変更しました。")
                .build();

        // 500はサーバ側で自動
    }

    @Path("/{lecture-id}/frame")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLectureFrame(
            @PathParam("lecture-id") String lectureId
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        Integer frame =
                lectureRepository.getLectureFrame(lectureId);

        // 404
        if (frame == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(frame)
                .build();

        // 500はサーバ側で自動
    }

    //これが正しい
    @Path("/{lecture-id}/day")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public Response putLectureDay(
            @PathParam("lecture-id") String lectureId,
            @FormParam("week") String week
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || week == null || week.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        lecture.setDay(week);
        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("開講曜日を変更しました。")
                .build();

        // 500はサーバ側で自動
    }


    //これが正しい
    @Path("/{lecture-id}/day")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLectureDay(
            @PathParam("lecture-id") String lectureId
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        String week = lecture.getDay();
        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(week)
                .build();

        // 500はサーバ側で自動
    }

    //ok
    @Path("/{lecture-id}/period")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public Response putLecturePeriod(
            @PathParam("lecture-id") String lectureId,
            @FormParam("time") Integer time
    ) {
        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()
                || time == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build();
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);

        // 404
        if (lecture == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        lecture.setPeriod(time);
        // 200 成功
        return Response.status(Response.Status.OK)
                .entity("開講時限を変更しました。")
                .build();

        // 500はサーバ側で自動
    }

    //ok
    @Path("/{lecture-id}/period")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public Response getLecturePeriod(
            @PathParam("lecture-id") String lectureId
    ) {

        // 400 不正なリクエスト
        if (lectureId == null || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("lecture-id が指定されていません。")
                    .build();
        }

        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);

        // 404
        if (lecture == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("lecture が存在しません。")
                    .build();
        }

        String time = lecture.getTime();
        // 200 成功
        return Response.status(Response.Status.OK)
                .entity(time)
                .build();

        // 500はサーバ側で自動
    }


}
