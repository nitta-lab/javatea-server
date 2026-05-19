package org.nittalab.javateaserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/categories/universities")
@Component
public class CategoryUniversityResource {

    private LectureRepository lectureRepository = null;
    private CategoryRepository categoryRepository = null;

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

        return Response
                .ok(getAllId(), MediaType.APPLICATION_JSON)
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
        String createdUnivId =
                categoryRepository.createUnivId(name, kana);

        return Response
                .status(Response.Status.CREATED)
                .entity(createdUnivId)
                .build();
    }

    @Path("/categories/universities/{univ-id}")
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
        return Response
                .ok(getUnivNameKana(), MediaType.APPLICATION_JSON)
                .build();
    }

    //大学名を登録・更新→指定された大学IDに対応する大学名を登録または更新する。
    @PUT
    @Path("/categories/universities/{univ-id}/name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public  Response updateUnivName(@PathParam("univ-id") String univId, @FormParam("name") String name) {
        //ここから続き
    }



}