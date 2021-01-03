package com.wechat.department;

import apiObject.DepartMentObject;
import apiObject.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FakerUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 增加初始化数据方法clearDepartment()
 * 优化：对脚本进行了分层，减少了重复代码，提高代码复用率，并减少了维护成本。
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Demo_05_param_csv {
    private static final Logger logger = LoggerFactory.getLogger(Demo_05_param_csv.class);
    static String accessToken;
    @BeforeAll
    public static void getAccessToken(){
        accessToken= TokenHelper.getAccessToken();
        logger.info(accessToken);
    }

    //初始化数据
//    @BeforeEach
//    @AfterEach
    void clearDepartment(){
        //先查询到id为1的子部门
        Response listResponse = DepartMentObject.listDepartMent("1",accessToken);
        ArrayList<Integer> departmentlistid = listResponse.path("department.id");
        //遍历查到到id集合
        for(int departmentid : departmentlistid){
            if(1==departmentid){
                continue;
            }
            //遍历删除子部门
            Response delResponse = DepartMentObject.deletDepartMent(departmentid+"",accessToken);
        }

    }

    @DisplayName("创建部门")
    @ParameterizedTest
    @CsvFileSource(resources = "/data/createDepartment.csv", numLinesToSkip = 1)
    void createDepartment(String creatName,String creatEnName,String returncode){

//        String name = "name"+ FakerUtils.getTimeStamp();
//        String enName= "en_name"+FakerUtils.getTimeStamp();


        Response response = DepartMentObject.creatDepartMent(creatName,creatEnName,accessToken);
        assertEquals(returncode,response.path("errcode").toString());

    }

    @DisplayName("更新部门")
    @Test
    @Order(2)
    void updateDepartment(){
        String updateName = "name"+ FakerUtils.getTimeStamp();
        String updateEnName = "en_name"+FakerUtils.getTimeStamp();

        String departmentId = DepartMentObject.creatDepartMent(accessToken);

        Response updateResponse = DepartMentObject.updateDepartMent(updateName,updateEnName,departmentId,accessToken);
        assertEquals("0",updateResponse.path("errcode").toString());
    }

    @DisplayName("查询部门")
    @Test
    @Order(3)
    void listDepartment(){
        String departmentId = DepartMentObject.creatDepartMent(accessToken);


        Response response = DepartMentObject.listDepartMent(departmentId,accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartment(){
        String departmentId = DepartMentObject.creatDepartMent(accessToken);


        Response response = DepartMentObject.deletDepartMent(departmentId,accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

}
