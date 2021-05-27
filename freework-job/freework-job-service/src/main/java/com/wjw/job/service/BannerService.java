package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.job.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @since 2021-05-26
 */
@Service
@Transactional
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    @Autowired
    private BannerMapper bannerMapper;

    public void addBanner(Banner banner) {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.orderByDesc("sort");
        wrapper.select("sort");
        Banner store = bannerMapper.selectOne(wrapper);
        int sort = 0;
        if (store != null) sort = store.getSort() + 1;
        banner.setSort(sort);
        bannerMapper.insert(banner);
    }
}
