//package com.wjw.upload; /**
// * @author wjw
// * @date 2021/3/4 17:59
// */
//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.domain.ThumbImageConfig;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class FastDFSTest {
//
//    @Autowired
//    private FastFileStorageClient storageClient;
//
//    @Autowired
//    private ThumbImageConfig thumbImageConfig;
//
//    @Test
//    public void testUpload() throws FileNotFoundException {
//        // 要上传的文件
//        File file = new File("C:\\Users\\wjw\\Desktop\\logo.png");
//        // 上传并保存图片，参数：1-上传的文件流 2-文件的大小 3-文件的后缀 4-可以不管他
//        StorePath storePath = this.storageClient.uploadFile(
//                new FileInputStream(file), file.length(), "png", null);
//        // 带分组的路径
//        System.out.println(storePath.getFullPath());
//        // 不带分组的路径
//        System.out.println(storePath.getPath());
//    }
//
//    @Test
//    public void testUploadAndCreateThumb() throws FileNotFoundException {
//        File file = new File("C:\\Users\\wjw\\Desktop\\logo.png");
//        // 上传并且生成缩略图    1上传的文件流  2。文件大小 3 文件后缀
//        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
//                new FileInputStream(file), file.length(), "png", null);
//        // 带分组的路径
//        System.out.println(storePath.getFullPath());
//        // 不带分组的路径
//        System.out.println(storePath.getPath());
//        // 获取缩略图路径
//        System.out.println(thumbImageConfig.getThumbImagePath(storePath.getFullPath()));
//        System.out.println(thumbImageConfig.getThumbImagePath(storePath.getPath()));
//    }
//}