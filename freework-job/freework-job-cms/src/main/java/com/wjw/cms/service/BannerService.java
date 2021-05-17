package com.wjw.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjw.cms.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-16
 */
@Service
@Transactional
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    @Autowired
    private BannerMapper bannerMapper;

    public List<Banner> findAll() {
        return bannerMapper.selectList(null);
    }
}