package org.nittalab.javateaserver.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;

import org.nittalab.javateaserver.models.Faculty;
import org.nittalab.javateaserver.models.Lecture;

import java.util.ArrayList;


@Path("/category-faculty")
@Component
public class CategoryFacultyResource {

    private LectureRepository lectureRepository = null;
    private CategoryRepository categoryRepository = null;

    @Autowired
    public CategoryFacultyResource(LectureRepository lectureRepository, CategoryRepository categoryRepository) {
        this.lectureRepository = lectureRepository;
        this.categoryRepository = categoryRepository;
    }

    //学部一覧取得
    @Path("/categories/universities/{univ-id}/faculties")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> getFaculty(@PathParam("univ-id") String univId) {

        //400不正リクエスト
        if (univId == null || univId.isEmpty()) {
            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
            throw new WebApplicationException(response.build());
        }

        ArrayList<String> faculty = categoryRepository.getFaculties(univId);

        //404データが存在しない
        if (faculty == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("データが存在しません");
            throw new WebApplicationException(response.build());
        }

        //200成功
        return faculty;

        //500予期せぬエラー
        //springbootが返してくれるためコード無し
    }

    //学部の追加
    @Path("/{univ-id}/faculties/{faculty-name}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addFaculty(@PathParam("univ-id") String univId,
                           @PathParam("faculty-name") String facultyName) {

        //400不正リクエスト
        if (univId == null || univId.isEmpty() || facultyName == null || facultyName.isEmpty()) {
            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない(大学が存在しない場合)
        if (categoryRepository.getUniversities(univId) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("大学が存在しません");
            throw new WebApplicationException(response.build());

        }

        Faculty faculty = categoryRepository.createFaculty(facultyName);

        //404データが存在しない
        if (faculty == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("追加出来ません");
            throw new WebApplicationException(response.build());
        }

    }

    //科目一覧取得
    @Path("/{univ-id}/faculties/{faculty-name}/lectures")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> getLectures(@PathParam("univ-id") String univId,
                                         @PathParam("faculty-name") String facultyName) {

        //400不正リクエスト
        if (univId == null || univId.isEmpty() || facultyName == null || facultyName.isEmpty()) {
            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
            throw new WebApplicationException(response.build());
        }

        ArrayList<String> lectures = categoryRepository.getLectures(univId, facultyName);

        //404データが存在しない
        if (lectures == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("データが存在しません");
            throw new WebApplicationException(response.build());
        }

        //200成功
        return lectures;
    }


    //科目の追加
    @Path("/{univ-id}/faculties/{faculty-name}/lectures/{lecture-id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addlecture(@PathParam("univ-id") String univId,
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
        if (categoryRepository.getUniversities()) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("大学が存在しません");
            throw new WebApplicationException(response.build());
        }

        //404データが存在しない(学部が存在しない)
        if (categoryRepository.get(facultyName) == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("学部が存在しません");
            throw new WebApplicationException(response.build());
        }

        Lecture lecture = categoryRepository.addLecture(lectureId,lectureRepository.getLecture(lectureId));

        //404データが存在しない
        if (lecture == null) {
            var response = Response.status(Response.Status.NOT_FOUND).entity("データが存在しません");
            throw new WebApplicationException(response.build());
        }


    }
}
