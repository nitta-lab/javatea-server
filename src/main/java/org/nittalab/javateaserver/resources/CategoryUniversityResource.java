package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getAllUnivId() {

        //getAllIdで一覧取得
        return Response
                .ok(categoryRepository.getAllId(), MediaType.APPLICATION_JSON)
                .build();
    }

    // 大学の新規作成
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response postNewUnivId(
            @FormParam("name") String name,
            @FormParam("kana") String kana) {

        // 400 不正なリクエスト
        if (name == null || kana == null) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("登録対象が見つかりません")
                    .build();
        }

        // 201 作成成功、作成したuniv-idを返す
        //createUnivIdで名前と仮名を作成する→大学の作成
        String createdUnivId =
                categoryRepository.createUnivId(name, kana);

        return Response
                .status(Response.Status.CREATED)
                .entity(createdUnivId)
                .build();
    }

    @Path("/{univ-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnivInfo(
            @PathParam("univ-id") String univId) {

        // 404 指定された大学IDが存在しません
        if (univId == null) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("指定された大学IDが存在しません")
                    .build();
        }

        // 200 OK
        //getUnivNameKana→指定した大学情報（読みとかなを取得）
        return Response
                .ok(categoryRepository.getUnivNameKana(), MediaType.APPLICATION_JSON)
                .build();
    }

    //大学名を登録・更新→指定された大学IDに対応する大学名を登録または更新する。
    @PUT
    @Path("/{univ-id}/name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  Response updateUnivName(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        //404エラー
        if (univId == null || name == null) {

            return Response.ok().build();
        }

        //200登録成功putUnivName
        return Response
                .ok(categoryRepository.putUnivName(), MediaType.TEXT_PLAIN)
                .build();
    }

    //大学の読み仮名変更
    @PUT
    @Path("/{univ-id}/kana")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  Response updateUnivKana(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        //404エラー
        if (univId == null || name == null) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("指定された大学IDが存在しません")
                    .build();
        }

        //200登録成功putUnivKana
        return Response.ok().build();
    }

    //大学全般に属する科目一覧（ID)の取得。
    @GET
    @Path("/{univ-id}/lectures")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUnivLectures(@PathParam("univ-id") String univId) {

        //400失敗
        if (univId == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("データが存在しません")
                    .build();
        }

        //getLectIdで授業IDを取得
        return Response
                .ok(lectureRepository.getLectId(), MediaType.TEXT_PLAIN)
                .build();
    }

    @PUT
    @Path("/{univ-id}/lectures/{lecture-id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putUnivLectures(@PathParam("univ-id") String univId, @PathParam("lecture-id") String lectId) {
        //404データが存在しない
        if (univId == null || lectId == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("データが存在しません")
                    .build();
        }

        //putLectIdで大学全般科目の授業IDを追加
        return Response.ok().build();
    }

}