package com.wjw.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
            banners = bannerMapper.selectList(null);
            if (!CollectionUtils.isEmpty(banners)){
                this.redisTemplate.opsForValue().set(KEY, JsonUtil.serialize(banners), 5, TimeUnit.MINUTES);
            }
        }
        return banners;
    }

    public void addBanner(Banner banner) {
        bannerMapper.insert(banner);
        String banStr = this.redisTemplate.opsForValue().get(KEY);
        if (!StringUtils.isBlank(banStr)){
            List<Banner> banners = JsonUtil.parseList(banStr, Banner.class);
            banners.add(banner);
            this.redisTemplate.opsForValue().set(KEY, JsonUtil.serialize(banners), 5, TimeUnit.MINUTES);
        }
    }

    public void removeBanner(Long id) {
        this.redisTemplate.delete(KEY);
        bannerMapper.deleteById(id);
    }

    public void updateBanner(Banner banner) {
        this.redisTemplate.delete(KEY);
        bannerMapper.updateById(banner);
    }
}