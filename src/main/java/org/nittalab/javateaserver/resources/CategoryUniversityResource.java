package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.nittalab.javateaserver.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public University getAllUnivId() {

        //getAllIdで一覧取得
        return categoryRepository.getUniversites();
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


        return categoryRepository.createUniversity(name, kana);
    }

    @Path("/{univ-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public University getUnivInfo(
            @PathParam("univ-id") String univId) {

        // 404 指定された大学IDが存在しません
        if (univId == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }

        // 200 OK
        //getUnivNameKana→指定した大学情報（読みとかなを取得）
        return categoryRepository.getUniversity();
    }

    //大学名を登録・更新→指定された大学IDに対応する大学名を登録または更新する。
    @PUT
    @Path("/{univ-id}/name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  void updateUnivName(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        //404エラー
        if (univId == null || name == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .build());
        }

        //200登録成功putUnivName
        //setName
    }

    //大学の読み仮名変更
    @PUT
    @Path("/{univ-id}/kana")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  void updateUnivKana(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        //404エラー
        if (univId == null || name == null) {

            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("指定された大学IDが存在しません")
                            .build());
        }
        //200登録成功putUnivKana
        //setKana
    }

    //大学全般に属する科目一覧（ID)の取得。
    @GET
    @Path("/{univ-id}/lectures")
    @Produces(MediaType.APPLICATION_JSON)
    public University getUnivLectures(@PathParam("univ-id") String univId) {

        //400失敗
        if (univId == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("データが存在しません")
                            .build());
        }

        //getLectIdで授業IDを取得
        return lectureRepository.getLectures();
    }

    @PUT
    @Path("/{univ-id}/lectures/{lecture-id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void putUnivLectures(@PathParam("univ-id") String univId, @PathParam("lecture-id") String lectId) {
        //404データが存在しない
        if (univId == null || lectId == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("データが存在しません")
                            .build());
        }

        //putLectIdで大学全般科目の授業IDを追加
        //addLecture
    }

}


//関数名変更したから、PUTのとこで更新追加のとこのコードを作る。