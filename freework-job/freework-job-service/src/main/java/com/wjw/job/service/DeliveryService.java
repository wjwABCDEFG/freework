package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.common.utils.MailUtils;
import com.wjw.job.constant.Progress;
import com.wjw.job.entity.Delivery;
import com.wjw.job.entity.User;
import com.wjw.job.entity.vo.DeliveryVO;
import com.wjw.job.mapper.DeliveryMapper;
import com.wjw.job.mapper.RecruitmentMapper;
import com.wjw.job.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjw
 * @since 2021-05-16
 */
@Service
@Transactional
public class DeliveryService extends ServiceImpl<DeliveryMapper, Delivery> {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Autowired
    private UserMapper userMapper;

    // 投递简历
    public void delivery(long userId, long recruitmentId, long resumeId, int progress) {
        // 保存投递数据
        Delivery delivery = new Delivery();
        delivery.setUserId(userId);
        delivery.setRecruitmentId(recruitmentId);
        delivery.setResumeId(resumeId);
        delivery.setProgress(progress);
        deliveryMapper.insert(delivery);

        // 查询hr
        User hr = recruitmentMapper.findHrById(recruitmentId);
        if (hr == null) throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
        //TODO 如果没有邮箱则发短信，反之亦然，若都没有才抛出异常
        if (hr.getEmail() == null) throw new FreeworkException(ErrCodeEnum.USEREMAILINVALID);

        // 邮件通知
        MailUtils.sendMail(hr.getEmail(),
                "尊敬的hr" + hr.getName() + "你好，您发布的岗位收到一份简历，<a target=\"_blank\" href=\"http://localhost:3000/\">点击查看</a>，不要让求职者久等噢！",
                "[freework]收到一份简历");
    }

    // 更新进度
    public void updateProgress(Long id, Integer progress, String msg) {
        Delivery delivery = new Delivery();
        delivery.setId(id);
        delivery.setProgress(progress);
        if (StringUtils.isNotEmpty(msg)){
            delivery.setMsg(msg);
        }
        deliveryMapper.updateById(delivery);

        if (delivery.getUserId() == null){
            delivery = deliveryMapper.selectById(id);
        }
        User user = userMapper.selectById(delivery.getUserId());

        // 邮件通知
        String mailTitle = "";
        if (progress == Progress.OUT){
            mailTitle = "[freework]感谢信";
        }else if (progress == Progress.OFFER){
            mailTitle = "[freework]offer通知";
        }else if (progress == Progress.EXAM){
            mailTitle = "[freework]笔试通知";
        }else {
            mailTitle = "[freework]求职进度更新提醒";
        }
        MailUtils.sendMail(user.getEmail(), msg, mailTitle);
    }


//    public Map<String, Object> pageCandidatesByUser(String hrId, long pageNum, long pageSize) {
//        Map<String, Object> map = new HashMap<>();
//        long start = (pageNum > 0) ? (pageNum - 1) * pageSize : 0;
//        List<DeliveryVO> deliveryVOList = deliveryMapper.pageCandidatesByUser(hrId, start, pageSize);
//
//        map.put("total", pageInfo.getTotal());
//        map.put("size", pageSize);
//        map.put("current", pageNum);
//        map.put("pages", pageInfo.getPages());
//        map.put("records", res);
//        return deliveryVOList;
//    }

    public List<DeliveryVO> findCandidatesByUser(String hrId) {
        return deliveryMapper.findCandidatesByUser(hrId);
    }
}
