package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserMiniapp;
import com.subin.springbootuser.mapper.UserMiniappMapper;
import com.subin.springbootuser.service.IUserMiniappService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserMiniappServiceImpl extends ServiceImpl<UserMiniappMapper, UserMiniapp> implements IUserMiniappService {

}
