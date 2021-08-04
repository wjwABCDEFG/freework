package com.wjw.vod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.wjw.common.enums.ErrCodeEnum;
import com.wjw.common.exception.FreeworkException;
import com.wjw.vod.utils.VodUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wjw
 * @date 2021/5/9 21:32
 */
@Service
@Transactional
public class VodService {

    @Value("${aliyun.vod.file.keyid}")
    private String accessKeyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String accessKeySecret;

    public String uploadVideo(MultipartFile file) {

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String title = fileName.substring(0, fileName.lastIndexOf('.'));

        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String vedioId = response.getVideoId();

        if (StringUtils.isBlank(vedioId)) throw new FreeworkException(ErrCodeEnum.VEDIOUPLOADERR);
        else return vedioId;
    }

    public void removeVideo(String videoId) {
        try{
            DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            // 调用接口实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);
        }catch (ClientException e){
            throw new FreeworkException(ErrCodeEnum.VEDIODELERR);
        }
    }

    public String getVideoPath(String videoId) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);

            // 获取request和response
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            GetPlayInfoResponse response = new GetPlayInfoResponse();

            // 向request传入id
            request.setVideoId(videoId);

            // 初始化对象传入request获取数据
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
//            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//            }
            if (playInfoList.size() >= 1) return playInfoList.get(0).getPlayURL();

            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 抽取初始化方法：获得对象
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
