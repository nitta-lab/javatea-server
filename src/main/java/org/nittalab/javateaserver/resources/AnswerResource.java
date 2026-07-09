package org.nittalab.javateaserver.resources;

import org.nittalab.javateaserver.models.Answer;
import org.nittalab.javateaserver.models.Question;
import org.nittalab.javateaserver.models.User;
import org.nittalab.javateaserver.repositories.AnswerRepository;
import org.nittalab.javateaserver.repositories.QuestionRepository;
import org.nittalab.javateaserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.*;
@Path("/questions")
@Component
public class AnswerResource {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    @Autowired
    public AnswerResource(AnswerRepository answerRepository, UserRepository userRepository, QuestionRepository questionRepository){
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @GET
    @Path("/{qid}/answers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HashMap<String,Answer> getAnswers(@PathParam("qid") String qid,@QueryParam("uid") String uid,@QueryParam("token") String token){
        checkUser(uid, token);

        checkQuestion(qid);

        //nullなら回答が一つも存在しない
        return answerRepository.getAnswers(qid);
    }

    @POST
    @Path("/{qid}/answers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Answer createAnswer(@PathParam("qid") String qid,@FormParam("uid") String uid,@FormParam("body") String body,@FormParam("token") String token){
        checkUser(uid, token);

        checkQuestion(qid);

        if(body == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("必要な情報が不足しています。")
                            .build()
            );
        }
        Answer newAnswer = answerRepository.createAnswer(qid, body, uid);
        if(newAnswer == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("回答の作成に失敗しました")
                            .build()
            );
        }

        return newAnswer;
    }

    @GET
    @Path("/{qid}/answers/{aid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Answer getAnswer(@PathParam("qid") String qid,@PathParam("aid") String aid,@QueryParam("uid") String uid,@QueryParam("token") String token){
        checkUser(uid, token);

        checkQuestion(qid);

        //nullなら回答が存在しない
        return answerRepository.getAnswer(qid,aid);
    }

    //userの存在確認とtokenCheck
    private void checkUser(String uid,String token){
        User user = userRepository.getUser(uid);

        if(user == null||uid == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 ユーザが存在しません")
                            .build()
            );
        }

        String userToken = user.getToken();

        if(!token.equals(userToken)){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("401 認証エラー")
                            .build()
            );
        }
    }

    //questionの存在確認
    private void checkQuestion(String qid){
        Question question = questionRepository.getQuestion(qid);

        if(qid == null||question == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("404 質問が存在しません")
                            .build()
            );
        }
    }
}
