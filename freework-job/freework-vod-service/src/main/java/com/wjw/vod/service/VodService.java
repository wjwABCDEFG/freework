package com.wjw.vod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
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
}
