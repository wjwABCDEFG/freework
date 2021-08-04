package com.wjw.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author wjw
 * @date 2021/4/20 19:18
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select u.* from tb_user u, tb_company c where u.id = c.basic_hr and c.id = #{companyId}")
    User findByCompanyId(Long companyId);
}
