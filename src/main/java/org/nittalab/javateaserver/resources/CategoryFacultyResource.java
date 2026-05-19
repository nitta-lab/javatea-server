package org.nittalab.javateaserver.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;


@Path("/categories/universities/{univ-id}/faculties")
@Component
public class CategoryFacultyResource{

    private LectureRepository lectureRepository = null;
    private CategoryRepository categoryRepository = null;

    @Autowired
    public CategoryFacultyResource(LectureRepository lectureRepository, CategoryRepository categoryRepository) {
        this.lectureRepository = lectureRepository;
        this.categoryRepository = categoryRepository;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response getFaculty(@PathParam("univ-id")String univId){

        //400不正リクエスト
        if(univId == null || univId.isEmpty()){
            var response = Response.status(Response.Status.BAD_REQUEST).entity("不正なリクエスト");
            throw new WebApplicationException(response.build());
        }

//        //404データが存在しない
//        if(facultyRepository.getFaculty(univId) == null){
//            var response = Response.status(Response.Status.NOT_FOUND).entity("データが存在しません");
//            throw new WebApplicationException(response.build());
//        }
//
//        //200成功
//        ArrayList<String>faculty = facultyRepository.getFaculty(univId);
//        if(faculty == null){
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        //200成功
        return Response.status(Response.Status.OK).build();

        //500予期せぬエラー
        //springbootが返してくれるためコード無し
    }

}
