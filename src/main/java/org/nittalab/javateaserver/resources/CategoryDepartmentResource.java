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

@Path("/categories")
@Component

public class CategoryDepartmentResource {
    private CategoryRepository categoryRepository;
    private LectureRepository lectureRepository;

    @Autowired
    public CategoryDepartmentResource(CategoryRepository categoryRepository, LectureRepository lectureRepository) {
        this.categoryRepository = categoryRepository;
        this.lectureRepository = lectureRepository;
    }

    @Path("/universities/{univ-id}/faculties/{faculty-name}/departments")
    @GET //学科一覧取得
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartments(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName){
        //400 bad request
        if (univId == null || facultyName == null || univId.isEmpty() || facultyName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Faculty faculty = university.getFaculty(facultyName);

        //404 not found
        if (faculty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        /*
        500 internal server error
        spring bootが500を返してくれるからコードなし
         */

        ArrayList<String> departmentNames = new ArrayList<>(faculty.getDepartments());

        //200 ok
        return Response.ok(departmentNames).build();
    }

    @Path("universities/{univ-id}/faculties/{faculty-name}/departments/{department-name}")
    @PUT //学科追加
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addDepartment(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName){
        //400 bad request
        if (univId == null || facultyName == null || departmentName == null || univId.isEmpty() || facultyName.isEmpty() || departmentName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Faculty faculty = university.getFaculty(facultyName);

        //404 not found
        if (faculty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        /*
        500 internal server error
        spring bootが500を返してくれるからコードなし
         */

        //200 ok
        faculty.createDepartment(departmentName);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/universities/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures")
    @GET //各学科特有の授業追加
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLectures(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName) {
        //400 bad request
        if (univId == null || facultyName == null || departmentName == null || univId.isEmpty() || facultyName.isEmpty() || departmentName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Faculty faculty = university.getFaculty(facultyName);

        //404 not found
        if (faculty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Department department = faculty.getDepartment(departmentName);

        //404 not found
        if (department == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        /*
        500 internal server error
        spring bootが500を返してくれるからコードなし
         */

        ArrayList<String> lectureIds = new ArrayList<>(department.getLectures().keySet());

        //200 ok
        return Response.ok(lectureIds).build();
    }

    @Path("universities/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}")
    @PUT //学科特有の各授業の質問IDの一覧取得
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addLecture(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId) {
        //400 bad request
        if (univId == null || facultyName == null || departmentName == null || lectureId == null || univId.isEmpty() || facultyName.isEmpty() || departmentName.isEmpty() || lectureId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        University university = categoryRepository.getUniversity(univId);

        //404 not found
        if (university == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Faculty faculty = university.getFaculty(facultyName);

        //404 not found
        if (faculty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Department department = faculty.getDepartment(departmentName);

        //404 not found
        if (department == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        /*
        500 internal server error
        spring bootが500を返してくれるからコードなし
         */

        Lecture lecture = lectureRepository.getLecture(lectureId);

        //200 ok
        department.addLecture(lectureId, lecture);
        return Response.status(Response.Status.OK).build();
    }
}
