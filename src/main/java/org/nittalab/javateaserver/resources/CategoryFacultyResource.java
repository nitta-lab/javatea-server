package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;

import org.nittalab.javateaserver.models.Faculty;
import org.nittalab.javateaserver.models.Lecture;

import java.util.*;


@Path("/categories/universities")
@Component
public class CategoryFacultyResource {

    private final LectureRepository lectureRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryFacultyResource(LectureRepository lectureRepository, CategoryRepository categoryRepository) {
        this.lectureRepository = lectureRepository;
        this.categoryRepository = categoryRepository;
    }

    //学部一覧取得
    @Path("/{univ-id}/faculties")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Set<String> getFaculty(@PathParam("univ-id") String univId) {

//        //400不正リクエスト
//        if (univId == null || univId.isEmpty()) {
//            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
//            throw new WebApplicationException(response.build());
//        }

        University university = categoryRepository.getUniversity(univId);
        // 404 指定された大学IDが存在しません
        if (university == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        //200成功
        return university.getFaculties();

        //500予期せぬエラー
        //springbootが返してくれるためコード無し
    }

    //学部の追加
    @Path("/{univ-id}/faculties/{faculty-name}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addFaculty(@PathParam("univ-id") String univId,
                           @PathParam("faculty-name") String facultyName) {

//        //400不正リクエスト
//        if (univId == null || univId.isEmpty() || facultyName == null || facultyName.isEmpty()) {
//            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
//            throw new WebApplicationException(response.build());
//        }

//        //404データが存在しない(大学が存在しない場合)
//        if (categoryRepository.getUniversity(univId) == null) {
//            var response = Response.status(Response.Status.NOT_FOUND).entity("大学が存在しません");
//            throw new WebApplicationException(response.build());
//
//        }
        University university = categoryRepository.getUniversity(univId);
        // 404 指定された大学IDが存在しません
        if (university == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        Faculty faculty = university.createFaculty(facultyName);
        // 404 指定された学部が存在しません
        if (faculty == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build());
        }

    }

    //科目一覧取得
    @Path("/{univ-id}/faculties/{faculty-name}/lectures")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Set<String> getLectures(@PathParam("univ-id") String univId,
                                   @PathParam("faculty-name") String facultyName) {

//        //400不正リクエスト
//        if (univId == null || univId.isEmpty() || facultyName == null || facultyName.isEmpty()) {
//            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
//            throw new WebApplicationException(response.build());
//        }

        //404データが存在しない(大学が存在しない)
        if (categoryRepository.getUniversity(univId) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("大学が存在しません");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない(学部が存在しない)
        if (categoryRepository.getUniversity(univId).getFaculty(facultyName) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("学部が存在しません");
            throw new WebApplicationException(response.build());
        }

        University university = categoryRepository.getUniversity(univId);
        Faculty faculty = university.getFaculty(facultyName);

        HashMap<String, Lecture> lectures = faculty.getLectures();


        //200成功
        return lectures.keySet();
    }


    //科目の追加
    @Path("/{univ-id}/faculties/{faculty-name}/lectures/{lecture-id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addLecture(@PathParam("univ-id") String univId,
                           @PathParam("faculty-name") String facultyName,
                           @PathParam("lecture-id") String lectureId) {

        //400不正なリクエスト
        if (univId == null || univId.isEmpty() ||
                facultyName == null || facultyName.isEmpty() ||
                lectureId == null || lectureId.isEmpty()) {
            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない(大学が存在しない)
        if (categoryRepository.getUniversity(univId) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("大学が存在しません");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない(学部が存在しない)
        if (categoryRepository.getUniversity(univId).getFaculty(facultyName) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("学部が存在しません");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない
        if (lectureRepository.getLecture(lectureId) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("データが存在しません");
            throw new WebApplicationException(response.build());
        }


        University university = categoryRepository.getUniversity(univId);
        Faculty faculty = university.getFaculty(facultyName);

        faculty.addLecture(lectureId,lectureRepository.getLecture(lectureId));



    }
}
