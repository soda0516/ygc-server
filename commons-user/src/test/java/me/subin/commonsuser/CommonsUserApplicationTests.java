package me.subin.commonsuser;

import lombok.extern.slf4j.Slf4j;
import me.subin.commonsuser.mapper.UserInfoMapper;
import me.subin.commonsuser.service.IUserInfoService;
import me.subin.utils.JsonConverterBin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CommonsUserApplicationTests {

    @Autowired
    IUserInfoService iUserInfoService;
    @Test
    void contextLoads() {
        log.info(JsonConverterBin.transferToJson(iUserInfoService.listUserInfoDetailBo()));
    }

}
