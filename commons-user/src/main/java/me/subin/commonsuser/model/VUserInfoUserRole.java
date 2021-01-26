package me.subin.commonsuser.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author soda
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VUserInfoUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("role_id")
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_note")
    private String roleNote;


}
