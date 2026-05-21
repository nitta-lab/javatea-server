package org.nittalab.javateaserver.resources;


import org.nittalab.javateaserver.models.Faculty;
import org.nittalab.javateaserver.models.University;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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

    @Path("/universities/{univ-id}/faculties/{faculty-name}/departments/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartments(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName){

        //400 bad request
        if (univId == null || facultyName == null || univId.isEmpty() || facultyName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        University university = categoryRepository.getUniversity(univId);
        Faculty faculty = University.getFaculty(facultyName);
        List<String> departmentName;

    }

}
