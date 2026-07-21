//package org.nittalab.javateaserver.resources;
//
//import org.nittalab.javateaserver.models.*;
//import org.nittalab.javateaserver.repositories.CategoryRepository;
//import org.nittalab.javateaserver.repositories.LectureRepository;
//import org.nittalab.javateaserver.repositories.QuestionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Set;
//
//@Path("/categories/universities")
//@Component
//
//public class CategoryDepartmentResource {
//    private CategoryRepository categoryRepository;
//    private LectureRepository lectureRepository;
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    public CategoryDepartmentResource(CategoryRepository categoryRepository, LectureRepository lectureRepository,  QuestionRepository questionRepository) {
//        this.categoryRepository = categoryRepository;
//        this.lectureRepository = lectureRepository;
//        this.questionRepository = questionRepository;
//    }
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments")
//    @GET //学科一覧取得
//    @Produces(MediaType.APPLICATION_JSON)
//    public Set<String> getDepartments(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName){
//
//        University university = categoryRepository.getUniversity(univId);
//
//        //404 not found
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build()
//            );
//        }
//
//
//
//        Faculty faculty = university.getFaculty(facultyName);
//
//        //404 not found
//        if (faculty == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build()
//            );
//        }
//
//        //200 ok
//        return faculty.getDepartments();
//    }
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}")
//    @PUT //学科追加
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public void addDepartment(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName){
//        University university = categoryRepository.getUniversity(univId);
//
//        //404 not found
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build()
//            );
//        }
//
//        Faculty faculty = university.getFaculty(facultyName);
//
//        if(faculty == null){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build()
//            );
//        }
//
//        //200 ok
//        faculty.createDepartment(departmentName);
//    }
//
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures")
//    @GET //各学科特有の授業追加
//    @Produces(MediaType.APPLICATION_JSON)
//    public Collection<Lecture> getLectures(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName) {
//
//        University university = categoryRepository.getUniversity(univId);
//
//        //404 not found
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build()
//            );
//        }
//
//        Faculty faculty = university.getFaculty(facultyName);
//
//        if(faculty == null){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build()
//            );
//        }
//
//        Department department = faculty.getDepartment(departmentName);
//
//        //404 not found
//        if (department == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学科が存在しません")
//                            .build()
//            );
//        }
//
//        //200 ok
//        return department.getLectures().values();
//    }
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}")
//    @PUT //学科特有の各授業の質問IDの一覧取得
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public void addLecture(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId) {
//        University university = categoryRepository.getUniversity(univId);
//
//        //404 not found
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build()
//            );
//        }
//
//        Faculty faculty = university.getFaculty(facultyName);
//
//        if(faculty == null){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build()
//            );
//        }
//
//        Department department = faculty.getDepartment(departmentName);
//
//        //404 not found
//        if (department == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学科が存在しません")
//                            .build()
//            );
//        }
//
//        Lecture lecture = lectureRepository.getLecture(lectureId);
//        if(lecture == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された授業が存在しません")
//                            .build()
//            );
//        }
//
//        //200 ok
//        department.addLecture(lectureId, lecture);
//    }
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}/questions")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Set<Question> getDepartmentQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId) {
//        University university = categoryRepository.getUniversity(univId);
//
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build());
//        }
//
//        Faculty faculty =  university.getFaculty(facultyName);
//
//        if (faculty == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build());
//        }
//
//        Department department = faculty.getDepartment(departmentName);
//
//        if (department == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学科が存在しません")
//                            .build());
//        }
//
//        Lecture lecture = department.getLecture(lectureId);
//
//        if (lecture == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された授業が存在しません")
//                            .build());
//        }
//
//        return lecture.getQuestions();
//    }
//
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}/questions/{qid}")
//    @PUT
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void addDepartmentQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId, @PathParam("qid") String qid) {
//        University university = categoryRepository.getUniversity(univId);
//
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build());
//        }
//
//        Faculty faculty =  university.getFaculty(facultyName);
//
//        if (faculty == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build());
//        }
//
//        Department department =  faculty.getDepartment(departmentName);
//
//        if (department == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学科が存在しません")
//                            .build());
//        }
//
//        Lecture lecture = department.getLecture(lectureId);
//
//        if (lecture == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された授業が存在しません")
//                            .build());
//        }
//
//        // ここにQID確認を入れる
//        Question question = questionRepository.getQuestion(qid);
//        if(question == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された質問IDが存在しません")
//                            .build());
//        }
//        lecture.addQuestion(question);
//        university.addAllQuestion(question);
//        faculty.addAllQuestion(question);
//        department.addAllQuestion(question);
//    }
//
//
//
//    // //////////////////////////
//    // 検索時に使うリソース
//    // /////////////////////////////
//
//    @GET
//    @Path("/{univ-id}/all-questions")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Set<Question> getAllUniversityQuestions(@PathParam("univ-id") String univId) {
//        University university = categoryRepository.getUniversity(univId);
//
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build());
//        }
//        return university.getAllQuestions();
//    }
//
//    @GET
//    @Path("/{univ-id}/faculties/{faculty-name}/all-questions")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Set<Question> getAllFacultyQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName) {
//        University university = categoryRepository.getUniversity(univId);
//
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build());
//        }
//
//        Faculty faculty =  university.getFaculty(facultyName);
//
//        if (faculty == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build());
//        }
//        return faculty.getAllQuestions();
//    }
//
//    @GET
//    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/all-questions")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Set<Question> getAllDepartmentQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName) {
//        University university = categoryRepository.getUniversity(univId);
//
//        if (university == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された大学IDが存在しません")
//                            .build());
//        }
//
//        Faculty faculty =  university.getFaculty(facultyName);
//
//        if (faculty == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学部が存在しません")
//                            .build());
//        }
//
//        Department department =  faculty.getDepartment(departmentName);
//
//        if (department == null) {
//            throw new WebApplicationException(
//                    Response.status(Response.Status.NOT_FOUND)
//                            .entity("指定された学科が存在しません")
//                            .build());
//        }
//        return department.getAllQuestions();
//    }
//}



package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.*;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.nittalab.javateaserver.repositories.QuestionRepository;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.nittalab.javateaserver.util.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Path("/categories/universities")
@Component

public class CategoryDepartmentResource {
    private CategoryRepository categoryRepository;
    private LectureRepository lectureRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    @Autowired
    public CategoryDepartmentResource(CategoryRepository categoryRepository, LectureRepository lectureRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.lectureRepository = lectureRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
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

    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}/questions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getDepartmentQuestions(
            @PathParam("univ-id") String univId,
            @PathParam("faculty-name") String facultyName,
            @PathParam("department-name") String departmentName,
            @PathParam("lecture-id") String lectureId,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token) {

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

        Department department =  faculty.getDepartment(departmentName);

        if (department == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学科が存在しません")
                            .build());
        }

        Lecture lecture = department.getLecture(lectureId);

        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された授業が存在しません")
                            .build());
        }

        // 認証
        User requester = authenticate(requesterUid, token);

        // ==================== 🛠️ ここから追加（デバッグ用ログ） ====================
        System.out.println("========== [DEBUG START] ==========");
        System.out.println("① 取得したLecture: " + lecture);
        System.out.println("② Lecture内の質問数: " + (lecture != null ? lecture.getQuestions().size() : 0));
        System.out.println("③ リクエストしたUser: " + requester);
        // =======================================================================

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : lecture.getQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        return visibleQuestions;
    }

    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/lectures/{lecture-id}/questions/{qid}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addDepartmentQuestions(@PathParam("univ-id") String univId, @PathParam("faculty-name") String facultyName, @PathParam("department-name") String departmentName, @PathParam("lecture-id") String lectureId, @PathParam("qid") String qid) {
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

        Department department =  faculty.getDepartment(departmentName);

        if (department == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学科が存在しません")
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
        department.addAllQuestion(question);
    }



    // //////////////////////////
    // 検索時に使うリソース
    // /////////////////////////////

    @GET
    @Path("/{univ-id}/all-questions")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getAllUniversityQuestions(
            @PathParam("univ-id") String univId,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token) {

        University university = categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        // 認証
        User requester = authenticate(requesterUid, token);

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : university.getAllQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        return visibleQuestions;
    }

    @GET
    @Path("/{univ-id}/faculties/{faculty-name}/all-questions")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getAllFacultyQuestions(
            @PathParam("univ-id") String univId,
            @PathParam("faculty-name") String facultyName,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token) {

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

        // 認証
        User requester = authenticate(requesterUid, token);

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : faculty.getAllQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        return visibleQuestions;
    }

    @GET
    @Path("/{univ-id}/faculties/{faculty-name}/departments/{department-name}/all-questions")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getAllDepartmentQuestions(
            @PathParam("univ-id") String univId,
            @PathParam("faculty-name") String facultyName,
            @PathParam("department-name") String departmentName,
            @QueryParam("uid") String requesterUid,
            @QueryParam("token") String token) {

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

        Department department =  faculty.getDepartment(departmentName);

        if (department == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された学科が存在しません")
                            .build());
        }

        // 認証
        User requester = authenticate(requesterUid, token);

        // 閲覧権限があるものだけに絞り込む
        Set<Question> visibleQuestions = new HashSet<>();
        for (Question question : department.getAllQuestions()) {
            if (PermissionChecker.hasPermission(
                    question.getViewPermission(), question.getUid(), requester.getUid(), userRepository)) {
                visibleQuestions.add(question);
            }
        }

        return visibleQuestions;
    }

    /**
     * リクエストしてきたユーザーが本人かどうかをtokenで確認する
     * (QuestionResourceの各エンドポイントと同じ認証パターン)
     */
    private User authenticate(String requesterUid, String token) {
        User requester = userRepository.getUser(requesterUid);

        // 404 ユーザが存在しません
        if (requester == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("ユーザが存在しません")
                            .build()
            );
        }

        // 403 認証失敗
        if (token == null || !token.equals(requester.getToken())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("認証失敗")
                            .build()
            );
        }

        return requester;
    }
}