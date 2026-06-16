package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Department;
import org.nittalab.javateaserver.models.Faculty;
import org.nittalab.javateaserver.models.Lecture;
import org.nittalab.javateaserver.models.University;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Path("/categories/universities")
@Component

public class CategoryDepartmentResource {
    private CategoryRepository categoryRepository;
    private LectureRepository lectureRepository;

    @Autowired
    public CategoryDepartmentResource(CategoryRepository categoryRepository, LectureRepository lectureRepository) {
        this.categoryRepository = categoryRepository;
        this.lectureRepository = lectureRepository;
    }

    @Path("/{univ-id}/faculties/{faculty-name}/departments")
    @GET //学科一覧取得
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getDepartments(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName){

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build()
            );
        }



        Faculty faculty = university.getFaculty(facultyName);

        //404 not found
        if (faculty == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build()
            );
        }

        //200 ok
        return faculty.getDepartments();
    }

    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}")
    @PUT //学科追加
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void addDepartment(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName){
        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build()
            );
        }

        Faculty faculty = university.getFaculty(facultyName);

        if(faculty == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build()
            );
        }

        //200 ok
        faculty.createDepartment(departmentName);
    }


    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures")
    @GET //各学科特有の授業追加
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Lecture> getLectures(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName) {

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build()
            );
        }

        Faculty faculty = university.getFaculty(facultyName);

        if(faculty == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build()
            );
        }

        Department department = faculty.getDepartment(departmentName);

        //404 not found
        if (department == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学科が存在しません")
                            .build()
            );
        }

        //200 ok
        return department.getLectures().values();
    }

    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}")
    @PUT //学科特有の各授業の質問IDの一覧取得
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void addLecture(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId) {
        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build()
            );
        }

        Faculty faculty = university.getFaculty(facultyName);

        if(faculty == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学部が存在しません")
                            .build()
            );
        }

        Department department = faculty.getDepartment(departmentName);

        //404 not found
        if (department == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学科が存在しません")
                            .build()
            );
        }

        Lecture lecture = lectureRepository.getLecture(lectureId);
        if(lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された授業が存在しません")
                            .build()
            );
        }

        //200 ok
        department.addLecture(lectureId, lecture);
    }
}