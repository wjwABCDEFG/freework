package com.wjw.job.mapper;

import com.wjw.job.entity.Delivery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.vo.DeliveryVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjw
 * @since 2021-05-16
 */
@Repository
public interface DeliveryMapper extends BaseMapper<Delivery> {

    @Select("SELECT d.*, r.position FROM tb_delivery d, tb_recruitment r WHERE d.recruitment_id = r.id and r.hr_id = #{hrId} limit(#{start}, #{pageSize})")
    List<DeliveryVO> pageCandidatesByUser(@Param("hrId") String hrId, @Param("start") long start, @Param("pageSize") long pageSize);

    @Select("SELECT d.*, r.position, u.* FROM tb_delivery d, tb_recruitment r, tb_user u WHERE d.recruitment_id = r.id and r.hr_id = #{hrId} and d.user_id = u.id")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "recruitmentId", column = "recruitment_id"),
            @Result(property = "resumeId", column = "resume_id"),
            @Result(property = "progress", column = "progress"),
            @Result(property = "msg", column = "msg"),
            @Result(property = "position", column = "position"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "user", column = "user_id", javaType = com.wjw.job.entity.User.class, one = @One(select = "com.wjw.job.mapper.UserMapper.selectById"))
    })
    List<DeliveryVO> findCandidatesByUser(String hrId);
}
