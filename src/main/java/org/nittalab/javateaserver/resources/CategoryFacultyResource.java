package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Question;
import org.nittalab.javateaserver.models.University;
import org.nittalab.javateaserver.repositories.QuestionRepository;
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
    private final QuestionRepository questionRepository;

    @Autowired
    public CategoryFacultyResource(LectureRepository lectureRepository, CategoryRepository categoryRepository,  QuestionRepository questionRepository) {
        this.lectureRepository = lectureRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    //学部一覧取得
    @Path("/{univ-id}/faculties")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Set<String> getFaculty(@PathParam("univ-id") String univId) {

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
    }

    //学部の追加
    @Path("/{univ-id}/faculties/{faculty-name}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addFaculty(@PathParam("univ-id") String univId,
                           @PathParam("faculty-name") String facultyName) {

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

    public Collection<Lecture> getLectures(@PathParam("univ-id") String univId,
                                   @PathParam("faculty-name") String facultyName) {

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
        return lectures.values();
    }


    //科目の追加
    @Path("/{univ-id}/faculties/{faculty-name}/lectures/{lecture-id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)

    public void addLecture(@PathParam("univ-id") String univId,
                           @PathParam("faculty-name") String facultyName,
                           @PathParam("lecture-id") String lectureId) {

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

    @Path("/{univ-id}/faculties/{faculty-name}/lectures/{lecture-id}/questions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getFacultyQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("lecture-id") String lectureId) {
        University university = categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        Faculty faculty =  university.getFaculty(facultyName);

        if (faculty == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build());
        }

        Lecture lecture = lectureRepository.getLecture(lectureId);

        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された授業が存在しません")
                            .build());
        }

        return lecture.getQuestions();
    }

    @Path("/{univ-id}/faculties/{faculty-name}/lectures/{lecture-id}/questions/{qid}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addFacultyQuestion(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("lecture-id") String lectureId, @PathParam("qid") String qid) {
        University university = categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        Faculty faculty =  university.getFaculty(facultyName);

        if (faculty == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build());
        }

        Lecture lecture = lectureRepository.getLecture(lectureId);

        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された授業が存在しません")
                            .build());
        }

        // ここにQID確認を入れる
        Question question = questionRepository.getQuestion(qid);
        if(question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された質問IDが存在しません")
                            .build());
        }
        lecture.addQuestion(question);
        university.addAllQuestion(question);
        faculty.addAllQuestion(question);
    }
}
