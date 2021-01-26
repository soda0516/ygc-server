package me.subin.commonsuser.service;

import java.time.LocalDateTime;

/**
 * 用来进行jwt token 相关操作的接口
 */
public interface IJwtDbService {
    void addJwtTokenInDb(String token, String username, Long userId);
    void refreshJwtTokenInDb(String token);
    void delJwtTokenInDbByUserName(Long userId);
    void delJwtTokenInDb(String token);
    boolean isExistJwtTokenInDb(String token);
    LocalDateTime getTimeByJwtTokenInDb(String token);
}
