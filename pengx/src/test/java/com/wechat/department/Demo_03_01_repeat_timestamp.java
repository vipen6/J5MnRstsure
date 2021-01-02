package com.wechat.department;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FakerUtils;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Demo_03_01_repeat_timestamp {
    private static final Logger logger = LoggerFactory.getLogger(Demo_03_01_repeat_timestamp.class);
    static String accessToken;
    static String departmendId;
    @BeforeAll
    public static void getAccessToken(){
        accessToken=given().log().all()
                .when()
                .param("corpid","ww41451c697c0bd5c6")
                .param("corpsecret","B2a08GSwoWqfX3TuP2xf_17SMJalBJ3hxZiaM879Nw0")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract()
                .response().path("access_token");
    }
    @DisplayName("创建部门")
    @Test
    @Order(1)
    void createDepartment(){
        String name = "name"+ FakerUtils.getTimeStamp();
        String enName= "en_name"+FakerUtils.getTimeStamp();

        String body = "{\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 3,\n" +
                "}\n";
        Response response = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        departmendId=response.path("id").toString();
        logger.info(accessToken);
    }

    @DisplayName("更新部门")
    @Test
    @Order(2)
    void updateDepartment(){
        String name = "name"+ FakerUtils.getTimeStamp();
        String enName= "en_name"+FakerUtils.getTimeStamp();

        String creatbody = "{\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 3,\n" +
                "}\n";
        Response creatresponse = given().log().all()
                .contentType("application/json")
                .body(creatbody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        String departmendId=creatresponse.path("id").toString();

        String updateBody ="{\n" +
                "   \"id\": "+departmendId+",\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"order\": 3\n" +
                "}\n";
        Response updateresponse = given().log().all()
                .contentType("application/json")
                .body(updateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+accessToken)
                .then()
                .log().body()
                .extract().response();
        assertEquals("0",updateresponse.path("errcode").toString());
    }

    @DisplayName("查询部门")
    @Test
    @Order(3)
    void listDepartment(){
        String name = "name"+ FakerUtils.getTimeStamp();
        String enName= "en_name"+FakerUtils.getTimeStamp();

        String creatbody = "{\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 3,\n" +
                "}\n";
        Response creatresponse = given().log().all()
                .contentType("application/json")
                .body(creatbody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        String departmendId=creatresponse.path("id").toString();

        Response response = given().log().all()
                .when()
                .param("id",departmendId)
                .param("access_token",accessToken)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log().body()
                .extract().response();
        assertEquals("0",response.path("errcode").toString());
        assertEquals(departmendId,response.path("department.id[0]").toString());
    }

    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartment(){
        String name = "name"+ FakerUtils.getTimeStamp();
        String enName= "en_name"+FakerUtils.getTimeStamp();

        String creatbody = "{\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 3,\n" +
                "}\n";
        Response creatresponse = given().log().all()
                .contentType("application/json")
                .body(creatbody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        String departmendId=creatresponse.path("id").toString();


        Response response = given().log().all()
                .contentType("application/json")
                .param("access_token",accessToken)
                .param("id",departmendId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().body()
                .extract().response();
        assertEquals("0",response.path("errcode").toString());
    }

}
