package com.wjw.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.cms.constant.SortConstant;
import com.wjw.cms.entity.Banner;
import com.wjw.cms.mapper.BannerMapper;
import com.wjw.common.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wjw
 * @since 2021-05-16
 */
@Service
@Transactional
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String KEY = "banners";

    public List<Banner> findAll() {
        List<Banner> banners;
        String banStr = this.redisTemplate.opsForValue().get(KEY);
        if (!StringUtils.isBlank(banStr)){
            banners = JsonUtil.parseList(banStr, Banner.class);
        }else {
            QueryWrapper<Banner> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("sort");
            banners = bannerMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(banners)){
                this.redisTemplate.opsForValue().set(KEY, JsonUtil.serialize(banners), 5, TimeUnit.MINUTES);
            }
        }
        return banners;
    }

    public void addBanner(Banner banner) {
        // 获取sort
        int sort = 0;
        this.redisTemplate.delete(KEY);
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.orderByDesc("sort");
        wrapper.select("sort");
        wrapper.last("limit 1");
        Banner store = bannerMapper.selectOne(wrapper);
        if (store != null) sort = store.getSort() + 1;
        banner.setSort(sort);

//        String banStr = this.redisTemplate.opsForValue().get(KEY);
//        if (!StringUtils.isBlank(banStr)){
//            List<Banner> banners = JsonUtil.parseList(banStr, Banner.class);
//            // redis中的banner都是有效的
//            sort = banners.stream().max(Comparator.comparingInt(Banner::getSort)).get().getSort() + 1;
//            banner.setSort(sort);
//            banners.add(banner);
//            this.redisTemplate.opsForValue().set(KEY, JsonUtil.serialize(banners), 5, TimeUnit.MINUTES);
//        }else {
//            QueryWrapper<Banner> wrapper = new QueryWrapper<>();
//            wrapper.eq("status", 0);
//            wrapper.orderByDesc("sort");
//            wrapper.select("sort");
//            wrapper.last("limit 1");
//            Banner store = bannerMapper.selectOne(wrapper);
//            if (store != null) sort = store.getSort() + 1;
//            banner.setSort(sort);
//        }

        bannerMapper.insert(banner);
    }

    public void removeBanner(Long id) {
        this.redisTemplate.delete(KEY);
        bannerMapper.deleteById(id);
    }

    public void updateBanner(Banner banner) {
        this.redisTemplate.delete(KEY);
        bannerMapper.updateById(banner);
    }

    public void updateSort(Long id, int direct) {
        this.redisTemplate.delete(KEY);
        Banner curBanner = bannerMapper.selectById(id);
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        if (direct == SortConstant.UP){
            wrapper.eq("sort", curBanner.getSort() - 1);
        }else if (direct == SortConstant.DOWN){
            wrapper.eq("sort", curBanner.getSort() + 1);
        }
        Banner changeBanner = bannerMapper.selectOne(wrapper);
        if (changeBanner == null) return ;
        int temp = curBanner.getSort();
        curBanner.setSort(changeBanner.getSort());
        changeBanner.setSort(temp);

        bannerMapper.updateById(curBanner);
        bannerMapper.updateById(changeBanner);
    }
}