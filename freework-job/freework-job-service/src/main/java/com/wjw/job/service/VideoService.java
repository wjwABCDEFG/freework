package com.wjw.job.service;

import com.wjw.job.entity.Video;
import com.wjw.job.mapper.VideoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjw
 * @since 2021-05-10
 */
@Service
@Transactional
public class VideoService extends ServiceImpl<VideoMapper, Video> {

    @Autowired
    private VideoMapper videoMapper;
}
