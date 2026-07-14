package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Question;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.nittalab.javateaserver.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("categories/general/questions")
@Component
public class CategoryGeneralResource {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public CategoryGeneralResource(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    // 【全般】の質問を取得
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Question> getGeneralQuestions() {
        return categoryRepository.getGeneralQuestions();
    }

    // 【全般】の質問追加
    @Path("/{qid}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addGeneralQuestion(@PathParam("qid") String qid) {
        Question question = questionRepository.getQuestion(qid);
        if(question == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された質問IDが存在しません")
                            .build());
        }
        categoryRepository.addGeneralQuestion(question);
    }
}
