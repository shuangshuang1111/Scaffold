package com.integration.scaffold.relationaldataaccess.mysql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class AddressBookControllerTest {
    // java.lang.IllegalStateException: Configuration error: found multiple declarations of @BootstrapWith for test class [com.integration.scaffold.relationaldataaccess.mysql.AddressBookControllerTest]: [@org.springframework.test.context.BootstrapWith(value=org.springframework.boot.test.context.SpringBootTestContextBootstrapper.class),
    // @org.springframework.test.context.BootstrapWith(value=org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTestContextBootstrapper.class)]
    // 这两个注解分别使用了不同Bootstrap来启动应用的上下文。
    //@BootstrapWith(WebMvcTestContextBootstrapper.class)
    //@BootstrapWith(SpringBootTestContextBootstrapper.class)
    //因此，只能二选一。
    //@WebMvcTest
    //1 这个注解仅用于Controller层的单元测试。默认情况下会仅实例化所有的Controller，可以通过指定单个Controller的方式实现对单个Controller的测试。
    //2 同时，如果被测试的Controller依赖Service的话，需要对该Service进行mock,如使用@MockBean
    //3 该注解的定义中还包括了@AutoConfigureMockMvc注解，因此，可以直接使用MockMvc对被测controller发起http请求。当然这过程中是不会产生真实的网络流量的。
    //
    //@SpringBootTest
    //1 这个注解用于集成测试，也就是默认会加载完整的Spring应用程序并注入所有所需的bean。一般会通过带有@SpringBootApplication的配置类来实现。
    //2 由于会加载整个应用到Spring容器中，整个启动过程是非常缓慢的（通常10+秒起步），一般会用于集成测试，可以使用TestRestTemplete或者MockMvc来发起请求并验证响应结果。
    //3 SpringBootTest中的也可以使用Mockito等Mock工具来对某些bean进行mock，但是一般不会只对单个层进行测试，推荐用于单个应用的端到到集成测试。
    //4 如果涉及到第三方依赖，如数据库、服务间调用、Redis等，可以考虑服务虚拟化方案。
    // 大概意思就是：尝试执行无效的SQL语句时引发代码42001的错误，原因就是 H2 不支持
    //MySQL 的 ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 这些设置，创建表的时候去掉就好了。
    //   mockMvc.perform(MockMvcRequestBuilders.post("/pis/config/add")  表单入参写法
    //                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //                .param("key", "key-test")
    //                .param("value", "v_test")
    //                .param("summary", "简介")
    //                .accept(MediaType.APPLICATION_JSON))
    //                .andDo(print());


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testSave() throws Exception {
        // SEQ_LOJA_DOCUMENTO处于初始状态。它在持久化时生成 ID = 1，这会与您手动插入的行发生冲突
        // 从5.2开始支持APPLICATION_JSON_VALUE，因为像Chrome这样的主流浏览器现在都符合规范，并且可以正确解释UTF-8特殊字符，而不需要charset=UTF-8参数。@Deprecated APPLICATION_JSON_UTF8_VALUE
        String content = mockMvc.perform(post("/addressBook/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{      \"userId\": 22223,\n" +
                                "        \"consignee\": \"云志\",\n" +
                                "        \"phone\": \"15210675046\",\n" +
                                "        \"sex\": \"0\",\n" +
                                "        \"provinceCode\": \"01\",\n" +
                                "        \"provinceName\": \"河北\",\n" +
                                "        \"cityCode\": \"0011\",\n" +
                                "        \"cityName\": \"北京\",\n" +
                                "        \"districtCode\": \"2224\",\n" +
                                "        \"districtName\": \"昌平区\",\n" +
                                "        \"detail\": \"这是首都北京\",\n" +
                                "        \"label\": \"首都\",\n" +
                                "        \"isDefault\": 1,\n" +
                                "        \"isDeleted\": 1\n" +
                                "    }")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content).get("datas");
        Assert.isTrue(jsonNode.get("userId").asLong() == 1, "userid比较数据错误");
        Assert.isTrue(jsonNode.get("consignee").asText().equals("云志"), "比较consignee数据错误");

    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/addressBook/100000000000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"datas\" : null,\n" +
                        "  \"respCode\" : 200,\n" +
                        "  \"respMsg\" : \"操作成功!\"\n" +
                        "}"));
    }

    @Test
    public void testListByUserId() throws Exception {
        mockMvc.perform(get("/addressBook/list/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/addressBook/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"datas\":null,\"respCode\":200,\"respMsg\":\"操作成功!\"}"));
    }

    @Test
    public void testUpdate() throws Exception {
        String content = mockMvc.perform(put("/addressBook/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{      \n" +
                                "        \"id\": 101,\n" +
                                "        \"userId\": 1,\n" +
                                "        \"consignee\": \"云志\",\n" +
                                "        \"phone\": \"15210675046\",\n" +
                                "        \"sex\": \"0\",\n" +
                                "        \"provinceCode\": \"01\",\n" +
                                "        \"provinceName\": \"河北\",\n" +
                                "        \"cityCode\": \"0011\",\n" +
                                "        \"cityName\": \"北京\",\n" +
                                "        \"districtCode\": \"2224\",\n" +
                                "        \"districtName\": \"昌平区\",\n" +
                                "        \"detail\": \"这是首都北京\",\n" +
                                "        \"createTime\": \"2024-03-26T15:51:13\",\n" +
                                "        \"label\": \"首都\",\n" +
                                "        \"createUser\": 1111111,\n" +
                                "        \"isDefault\": 1,\n" +
                                "        \"isDeleted\": 1\n" +
                                "    }")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(content).get("datas");

        Assert.isTrue(jsonNode.get("userId").asLong() == 1, "Userid is wrong");
        Assert.isTrue(jsonNode.get("consignee").asText().equals("云志"), "Consignee is wrong");
        Assert.isTrue(jsonNode.get("cityName").asText().equals("北京"), "CityName is wrong");
        Assert.isTrue(jsonNode.get("provinceName").asText().equals("河北"), "ProvinceName is wrong");
        Assert.isTrue(jsonNode.get("districtName").asText().equals("昌平区"), "DistrictName is wrong");
        Assert.isTrue(jsonNode.get("detail").asText().equals("这是首都北京"), "Detail is wrong");
        Assert.isTrue(jsonNode.get("provinceCode").asText().equals("01"), "ProvinceCodee is wrong");
        Assert.isTrue(jsonNode.get("cityCode").asText().equals("0011"), "CityCode is wrong");
        Assert.isTrue(jsonNode.get("label").asText().equals("首都"), "Label is wrong");


    }

    @Test
    public void testGetAllUserInfo() throws Exception {
        mockMvc.perform(get("/addressBook/UserInfo/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"datas\":null,\"respCode\":200,\"respMsg\":\"操作成功!\"}"));

    }

    @Test
    public void testGetUserInfoByPage() throws Exception {
        String content = mockMvc.perform(get("/addressBook/UserInfoByPage/1/0/6")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())

                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        Assert.isTrue(jsonNode.get("respCode").asInt() == 200, "RespCode is error");
        Assert.isTrue(jsonNode.get("respMsg").asText().equals("操作成功!"), "RespMsg is error");
    }


    @Test
    public void testAllConsignee() throws Exception {
        mockMvc.perform(get("/addressBook/consignee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"datas\":[\"云志\"],\"respCode\":200,\"respMsg\":\"操作成功!\"}"));
    }

    // WebTestClient 是围绕 WebClient 的薄壳，可用于执行请求并公开专用的流利API来验证响应。
    // WebTestClient 通过使用模拟请求和响应绑定到 WebFlux 应用程序，或者它可以通过HTTP连接测试任何Web服务器。


}
