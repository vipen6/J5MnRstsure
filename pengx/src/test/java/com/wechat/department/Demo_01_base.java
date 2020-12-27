package com.wechat.department;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static io.restassured.RestAssured.given;

public class Demo_01_base {
    private static final Logger logger = LoggerFactory.getLogger(Demo_01_base.class);
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
    void createDepartment(){
        String body = "{\n" +
                "   \"name\": \"广州研发中心\",\n" +
                "   \"name_en\": \"RDGZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1,\n" +
                "   \"id\": 2\n" +
                "}\n";
        Response response = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        departmendId=response.path("id");
        logger.info(accessToken);
    }

}
