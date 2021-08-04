//package com.wjw;
//
//import com.wjw.job.FreeworkJobApplication;
//import com.wjw.job.entity.Role;
//import com.wjw.job.mapper.RoleMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author wjw
// * @date 2021/1/21 16:09
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = FreeworkJobApplication.class)
//public class PerformAnalyzeTest {
//
//    @Autowired
//    private RoleMapper roleMapper;
//
//    @Test
//    public void testInsert(){
//        Role role = new Role();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        role.setRoleName("aa");
//        role.setRemark("bb");
//        int result = roleMapper.insert(role);
//        System.out.println(result); //影响的行数
//    }
//}
