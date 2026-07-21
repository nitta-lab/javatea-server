package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Lecture;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//動作確認済み
@Path("/lectures")
@Component
public class LectureResource {

    private final LectureRepository lectureRepository;

    @Autowired
    public LectureResource(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createLecture(
            @FormParam("name") String name,
            @FormParam("grade") Integer grade,
            @FormParam("semester") String semester,
            @FormParam("frame") Integer frame,
            @FormParam("day") String day,
            @FormParam("period") Integer period,
            @FormParam("facultyName") String facultyName,
            @FormParam("departmentName") String departmentName)
    {
        // 400 不正なリクエスト
        if (name == null || name.isEmpty()
                || grade == null
                || semester == null || semester.isEmpty()
                || frame == null
                || day == null || day.isEmpty()
                || period == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // 201 作成成功
        return lectureRepository.createLecture(name, grade, semester, frame, day, period,facultyName, departmentName);

//        // 404 データが存在しない　→　ここではエラー404は必要ない
//        // 500 予期せぬエラー　→　ここではエラー500は必要ない
    }

    //動作確認済み
    @Path("/{lecture-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Lecture getLecture(@PathParam("lecture-id") String lectureId)
    {
        Lecture lecture = lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }
        // 200
        return lecture;
    }

    //動作確認済み
    @Path("/{lecture-id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void putLectureName(
            @PathParam("lecture-id") String lectureId,
            @FormParam("name") String name
    ) {
        // 400 不正なリクエスト
        if (name == null || name.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setName(name);
    }

    //動作確認済み
    @Path("/{lecture-id}/name")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public String getLectureName(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        // 200 成功
        return lecture.getName();
    }

    //動作確認済み
    @Path("/{lecture-id}/grade")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public void putLectureGrade(
            @PathParam("lecture-id") String lectureId,
            @FormParam("grade") Integer grade
    ) {

        // 400 不正なリクエスト
        if (grade == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setGrade(grade);
    }

    //動作確認済み
    @Path("/{lecture-id}/grade")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public int getLectureGrade(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        // 200 成功
        return lecture.getGrade();
    }

    //動作確認済み
    @Path("/{lecture-id}/semester")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public void putLectureSemester(
            @PathParam("lecture-id") String lectureId,
            @FormParam("semester") String semester
    ) {

        // 400 不正なリクエスト
        if (semester == null || semester.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setSemester(semester);
    }

    //動作確認済み
    @Path("/{lecture-id}/semester")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public String getLectureSemester(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        // 200 成功
        return lecture.getSemester();
    }

    //動作確認済み
    @Path("/{lecture-id}/frame")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public void putLectureFrame(
            @PathParam("lecture-id") String lectureId,
            @FormParam("frame") Integer frame
    ) {
        // 400 不正なリクエスト
        if (frame == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setFrame(frame);
    }

    //動作確認済み
    @Path("/{lecture-id}/frame")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public int getLectureFrame(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);

        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        // 200 成功
        return lecture.getFrame();
    }

    //動作確認済み
    @Path("/{lecture-id}/day")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public void putLectureDay(
            @PathParam("lecture-id") String lectureId,
            @FormParam("day") String day
    ) {
        // 400 不正なリクエスト
        if (day == null || day.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setDay(day);
    }


    //動作確認済み
    @Path("/{lecture-id}/day")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public String getLectureDay(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);
        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        // 200 成功
        return lecture.getDay();
    }

    //動作確認済み
    @Path("/{lecture-id}/period")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public void putLecturePeriod(
            @PathParam("lecture-id") String lectureId,
            @FormParam("period") Integer period
    ) {
        // 400 不正なリクエスト
        if (period == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                    .entity("必要な情報が不足しています。")
                    .build()
            );
        }

        // Repositoryで更新
        Lecture lecture =
                lectureRepository.getLecture(lectureId);

        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }

        lecture.setPeriod(period);
    }

    //動作確認済み
    @Path("/{lecture-id}/period")
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public int getLecturePeriod(
            @PathParam("lecture-id") String lectureId
    ) {
        // Repositoryから取得
        Lecture lecture =
                lectureRepository.getLecture(lectureId);

        // 404
        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("lecture が存在しません。")
                            .build()
            );
        }
        // 200 成功
        return lecture.getPeriod();
    }
}
