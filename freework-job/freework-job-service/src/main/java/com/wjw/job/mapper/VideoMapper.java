package com.wjw.job.mapper;

import com.wjw.job.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.vo.VideoVO;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wjw
 * @since 2021-05-10
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {

    @Select("select * from tb_video")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "videoTitle", column = "video_title"),
            @Result(property = "videoPic", column = "video_pic"),
            @Result(property = "videoSourceId", column = "video_source_id"),
            @Result(property = "videoOriginalName", column = "video_original_name"),
            @Result(property = "playCount", column = "play_count"),
            @Result(property = "uploadUser", column = "uploader", javaType = com.wjw.job.entity.User.class, one = @One(select = "com.wjw.job.mapper.UserMapper.selectById"))
    })
    List<VideoVO> findAll();
}
