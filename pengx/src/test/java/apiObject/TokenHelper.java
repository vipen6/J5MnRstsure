package apiObject;

import static io.restassured.RestAssured.given;

public class TokenHelper {
    //封装accessToken的方法
    public static String getAccessToken(){
        String accessToken=given().log().all()
                .when()
                .param("corpid","ww41451c697c0bd5c6")
                .param("corpsecret","B2a08GSwoWqfX3TuP2xf_17SMJalBJ3hxZiaM879Nw0")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract()
                .response().path("access_token");
        return accessToken;
    }
}
