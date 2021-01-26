package me.subin.commonsuser.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.jackson.serializer.ZeroToNullSerializer;

import java.util.List;

/**
 * @author bin
 * @ClassName UserInfoDetailBo
 * @Description TODO
 * @date 2021/1/25 10:48
 */
@Data
public class UserInfoDetailBo {
    private Long id;
    private String username;
    private String password;
    @JsonSerialize(using = ZeroToNullSerializer.class)
    private Long storeId;
    private List<Long> roleIds;
}
