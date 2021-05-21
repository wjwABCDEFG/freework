package com.wjw.job.service;

import com.wjw.common.entity.Result;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.job.client.VodClient;
import com.wjw.job.entity.Video;
import com.wjw.job.entity.vo.VideoVO;
import com.wjw.job.mapper.VideoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-10
 */
@Service
@Transactional
public class VideoService extends ServiceImpl<VideoMapper, Video> {

    @Autowired
    private VideoMapper videoMapper;

    // 注入远程调用方法
    @Autowired
    private VodClient vodClient;

    public void removeVideo(Long id) {
        //根据id获取视频资源id
        Video video = videoMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //远程调用删除视频方法
            Result result = vodClient.removeVideo(videoSourceId);
            if (result.getCode() != 2000 && "timeout".equals(result.getMsg())){
                throw new FreeworkException(ErrCodeEnum.TIMEOUT);
            }
        }
        videoMapper.deleteById(id);
    }

    public List<VideoVO> findAll() {
        List<VideoVO> voList = videoMapper.findAll();
        for (VideoVO videoVO : voList) {
            //远程调用获取视频连接方法
            Result result = vodClient.getVideoPath(videoVO.getVideoSourceId());
            if (result.getCode() != 2000 && "timeout".equals(result.getMsg())){
                throw new FreeworkException(ErrCodeEnum.TIMEOUT);
            }
            videoVO.setVideoPath(result.getData().toString());
        }
        return voList;
    }
}
