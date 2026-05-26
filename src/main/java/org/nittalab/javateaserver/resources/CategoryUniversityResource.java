package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.nittalab.javateaserver.models.Lecture;
import org.nittalab.javateaserver.models.University;
import org.nittalab.javateaserver.repositories.CategoryRepository;
import org.nittalab.javateaserver.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Path("/categories/universities")
@Component
public class CategoryUniversityResource {

    private LectureRepository lectureRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryUniversityResource(
            LectureRepository lectureRepository,
            CategoryRepository categoryRepository) {

        this.lectureRepository = lectureRepository;
        this.categoryRepository = categoryRepository;
    }

    // 大学一覧の取得
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, University> getAllUnivId() {

        //getAllIdで一覧取得
        return categoryRepository.getUniversities();
    }

    // 大学の新規作成
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String postNewUnivId(
            @FormParam("name") String name,
            @FormParam("kana") String kana) {

        // 400 不正なリクエスト
        if (name == null || kana == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("登録者が見つかりません")
                            .build());
        }

        // 201 作成成功、作成したuniv-idを返す
        //createUnivIdで名前と仮名を作成する→大学の作成
        University university = categoryRepository.createUniversity(name, kana);

        if(university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("不正な読み仮名です。")
                            .build());
        }
        return university.getId();

    }

    @Path("/{univ-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public University getUnivInfo(
            @PathParam("univ-id") String univId) {

        University university =
                categoryRepository.getUniversity(univId);
        // 404 指定された大学IDが存在しません
        if (university == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        // 200 OK
        //getUnivNameKana→指定した大学情報（読みとかなを取得）
        return university;
    }

    //大学名を登録・更新→指定された大学IDに対応する大学名を登録または更新する。
    @PUT
    @Path("/{univ-id}/name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public  void updateUnivName(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        University university =
                categoryRepository.getUniversity(univId);

        //404エラー
        if (university == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        if (name == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("nameが不足しています")
                            .build());
        }

        //200登録成功putUnivName
        //setName
        university.setName(name);
    }

    //大学の読み仮名変更
    @PUT
    @Path("/{univ-id}/kana")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  void updateUnivKana(@PathParam("univ-id") String univId, @FormParam("Kana") String kana) {
        University university =
                categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        if (kana == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("kanaが不足しています")
                            .build());
        }

        university.setKana(kana);
    }

    //大学全般に属する科目一覧（ID)の取得。
    @GET
    @Path("/{univ-id}/lectures")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getUnivLectures(@PathParam("univ-id") String univId) {

        University university =
                categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        return university.getLectures().keySet();
    }

    //大学全般の質問の追加
    @PUT
    @Path("/{univ-id}/lectures/{lecture-id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void putUnivLectures(@PathParam("univ-id") String univId, @PathParam("lecture-id") String lectId) {
        University university =
                categoryRepository.getUniversity(univId);

        if (university == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        if (lectId == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("lecture-idが不足しています")
                            .build());
        }

        Lecture lecture =
                lectureRepository.getLecture(lectId);

        if (lecture == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("指定された授業IDが存在しません")
                            .build());
        }

        university.addLecture(lectId, lecture);
    }

}


//関数名変更したから、PUTのとこで更新追加のとこのコードを作る。