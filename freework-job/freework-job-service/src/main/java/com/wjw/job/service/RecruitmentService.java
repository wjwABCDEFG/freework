package com.wjw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.job.constant.CompanyAuth;
import com.wjw.job.entity.Company;
import com.wjw.job.entity.Recruitment;
import com.wjw.job.entity.User;
import com.wjw.job.entity.vo.RecruitmentDetailVO;
import com.wjw.job.entity.vo.RecruitmentQuery;
import com.wjw.job.entity.vo.RecruitmentVO;
import com.wjw.job.mapper.CompanyMapper;
import com.wjw.job.mapper.RecruitmentMapper;
import com.wjw.job.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjw
 * @date 2021/4/7 19:20
 */
@Service
@Transactional
public class RecruitmentService extends ServiceImpl<RecruitmentMapper, Recruitment> {

    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    public List<RecruitmentVO> findAll() {
        List<Recruitment> recList = recruitmentMapper.selectList(null);
        if (CollectionUtils.isEmpty(recList)) throw new FreeworkException(ErrCodeEnum.NONEJOBINFO);

        List<RecruitmentVO> recVOList = new ArrayList<>();
        for (Recruitment rec : recList) {
            RecruitmentVO recVO = new RecruitmentVO();
            BeanUtils.copyProperties(rec, recVO);

            Company company = companyMapper.selectById(rec.getCompanyId());
            if (company == null) throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
            recVO.setCompany(company);

            User user = userMapper.selectById(rec.getHrId());
            if (user == null) throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
            recVO.setHr(user);

            recVOList.add(recVO);
        }

        return recVOList;
    }

    public RecruitmentDetailVO findById(Long id) {
        RecruitmentDetailVO detailVO = new RecruitmentDetailVO();
        Recruitment recruitment = this.recruitmentMapper.selectById(id);
        BeanUtils.copyProperties(recruitment, detailVO);

        Company company = companyMapper.selectById(recruitment.getCompanyId());
        if (company == null) throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
        detailVO.setCompany(company);

        User user = userMapper.selectById(recruitment.getHrId());
        if (user == null) throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
        detailVO.setHr(user);

        return detailVO;
    }

    public List<RecruitmentVO> findIndex() {
        QueryWrapper<Recruitment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Recruitment> recList = recruitmentMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(recList)) throw new FreeworkException(ErrCodeEnum.NONEJOBINFO);

        List<RecruitmentVO> recVOList = new ArrayList<>();
        for (Recruitment rec : recList) {
            RecruitmentVO recVO = new RecruitmentVO();
            BeanUtils.copyProperties(rec, recVO);

            Company company = companyMapper.selectById(rec.getCompanyId());
            if (company == null) throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
            recVO.setCompany(company);

            User user = userMapper.selectById(rec.getHrId());
            if (user == null) throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
            recVO.setHr(user);

            recVOList.add(recVO);
        }

        return recVOList;
    }

    public Map<String, Object> pageQuery(Page<Recruitment> pageInfo, RecruitmentQuery recruitmentQuery) {
        Map<String, Object> map = new HashMap<>();
        Company store = null;
        if (recruitmentQuery == null){
            recruitmentMapper.selectPage(pageInfo, null);
        }else{
            QueryWrapper<Recruitment> wrapper = new QueryWrapper<>();
            String companyName = recruitmentQuery.getCompanyName();
            String address = recruitmentQuery.getAddress();
            String industry = recruitmentQuery.getIndustry();
            String position = recruitmentQuery.getPosition();
            if (StringUtils.isNotEmpty(companyName)) {
                store = companyMapper.findByName(companyName);
                if (store != null){
                    wrapper.eq("company_id", store.getId());
                }
            }
            if (StringUtils.isNotEmpty(address)) wrapper.like("address", address);
            if (StringUtils.isNotEmpty(industry)) wrapper.eq("position", industry);
            if (StringUtils.isNotEmpty(position)) wrapper.eq("position", position);
            recruitmentMapper.selectPage(pageInfo, wrapper);
        }

        map.put("total", pageInfo.getTotal());
        map.put("size", pageInfo.getSize());
        map.put("current", pageInfo.getCurrent());
        map.put("pages", pageInfo.getPages());
        List<Recruitment> records = pageInfo.getRecords();
        List<RecruitmentVO> res = new ArrayList<>();
        for (Recruitment record : records) {
            RecruitmentVO recruitmentVO = new RecruitmentVO();
            BeanUtils.copyProperties(record, recruitmentVO);

            if (store != null){
                recruitmentVO.setCompany(store);
            }else {
                Company company = companyMapper.selectById(record.getCompanyId());
                recruitmentVO.setCompany(company);
            }

            User user = userMapper.selectById(record.getHrId());
            recruitmentVO.setHr(user);

            res.add(recruitmentVO);
        }
        map.put("records", res);

        return map;
    }

    public List<RecruitmentVO> findByCompanyId(String companyId) {
        QueryWrapper<Recruitment> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id", companyId);
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<Recruitment> recList = recruitmentMapper.selectList(wrapper);
//        if (CollectionUtils.isEmpty(recList)) throw new FreeworkException(ErrCodeEnum.NONEJOBINFO);

        List<RecruitmentVO> recVOList = new ArrayList<>();
        for (Recruitment rec : recList) {
            RecruitmentVO recVO = new RecruitmentVO();
            BeanUtils.copyProperties(rec, recVO);

            Company company = companyMapper.selectById(rec.getCompanyId());
            if (company == null) throw new FreeworkException(ErrCodeEnum.NONECOMPANYINFO);
            recVO.setCompany(company);

            User user = userMapper.selectById(rec.getHrId());
            if (user == null) throw new FreeworkException(ErrCodeEnum.NONEUSERINFO);
            recVO.setHr(user);

            recVOList.add(recVO);
        }

        return recVOList;
    }

    public List<Recruitment> findByHrId(String hrId) {
        QueryWrapper<Recruitment> wrapper = new QueryWrapper<>();
        wrapper.eq("hr_id", hrId);
        wrapper.orderByDesc("id");
        List<Recruitment> recList = recruitmentMapper.selectList(wrapper);
        return recList;
    }
}
