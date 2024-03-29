//package com.wjw;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import org.junit.Test;
//
///**
// * @author wjw
// * @date 2021/1/21 1:04
// */
//public class CodeGenerator {
//
//    @Test
//    public void test() {
//
//        // 1、创建代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 2、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("wjw");
//        gc.setOpen(true); //生成后是否打开资源管理器
//        gc.setFileOverride(false); //重新生成时文件是否覆盖
//        /*
//         * mp生成service层代码，默认接口名称第一个字母有 I
//         * UcenterService
//         */
//        gc.setServiceName("%sService");    //去掉Service接口的首字母I
//        gc.setIdType(IdType.ID_WORKER); //主键策略
//        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
//        gc.setSwagger2(true);//开启Swagger2模式
//
//        mpg.setGlobalConfig(gc);
//
//        // 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/freework?serverTimezone=GMT%2B8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("0751@@@wjw");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 4、包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("job"); //模块名
//        pc.setParent("com.wjw");
//        pc.setController("controller");
//        pc.setEntity("entity");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        mpg.setPackageInfo(pc);
//
//        // 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("tb_delivery");   // 要生成的表
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
//        strategy.setTablePrefix("tb_"); //生成实体时去掉表前缀
//
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
//        strategy.setEntityLombokModel(false); // 是否使用lombok 模型 @Accessors(chain = true) setter链式操作
//
//        strategy.setRestControllerStyle(true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
//
//        mpg.setStrategy(strategy);
//
//        // 6、执行
//        mpg.execute();
//    }
//
//}