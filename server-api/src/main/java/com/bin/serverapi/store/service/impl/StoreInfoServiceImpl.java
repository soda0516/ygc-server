package com.bin.serverapi.store.service.impl;

import com.bin.serverapi.store.entity.StoreInfo;
import com.bin.serverapi.store.mapper.StoreInfoMapper;
import com.bin.serverapi.store.service.IStoreInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2021-01-05
 */
@Service
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo> implements IStoreInfoService {

}
