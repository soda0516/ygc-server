package com.subin.springbootuser.service;

import java.time.LocalDateTime;

/**
 * 用来进行jwt token 相关操作的接口
 */
public interface IJwtDbService {
    void addJwtTokenInDb(String token, String username);
    void refreshJwtTokenInDb(String token);
    void delJwtTokenInDbByUserName(String username);
    void delJwtTokenInDb(String token);
    boolean isExistJwtTokenInDb(String token);
    LocalDateTime getTimeByJwtTokenInDb(String token);
    String getJwtSecret(String token);
}
