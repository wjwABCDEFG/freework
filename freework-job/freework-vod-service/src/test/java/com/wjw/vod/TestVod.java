package com.wjw.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author wjw
 * @date 2021/5/9 20:26
 */
public class TestVod {

    /**
     * 根据id获取播放地址
     * @throws ClientException
     */
    @Test
    public void getUrlFromId() throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = TestVod.initVodClient("LTAI5tMy2iXWoR39QuqKaSbk", "cKx0YIUSpvN15hdrNkLcAzrGO332Jj");

        // 获取request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向request传入id
        request.setVideoId("b572cf3f71424982bb862b2c3f92277d");

        // 初始化对象传入request获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /**
     * 根据id获取凭证
     * 因为如果配置了加密，直接获取的地址就无效了，要获取凭证
     * 获取凭证后再通过阿里的视频播放器获取真正可用的地址
     * @throws ClientException
     */
    @Test
    public void getAccessKeyFromId() throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = TestVod.initVodClient("LTAI5tMy2iXWoR39QuqKaSbk", "cKx0YIUSpvN15hdrNkLcAzrGO332Jj");

        // 获取带凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 向request传入id
        request.setVideoId("b572cf3f71424982bb862b2c3f92277d");

        // 初始化对象传入request获取数据
        response = client.getAcsResponse(request);
        System.out.println("PlayAuth = " + response.getPlayAuth());
    }


    /**
     * 从本地文件上传
     */
    @Test
    public void testUploadFromDisk(){
        String accessKeyId = "LTAI5tMy2iXWoR39QuqKaSbk";
        String accessKeySecret = "cKx0YIUSpvN15hdrNkLcAzrGO332Jj";
        String title = "freework-test-java-disk";
        String fileName = "G:\\BaiduNetdiskDownload\\FreeworkSampleVideo.mp4";
        uploadFromDisk(accessKeyId, accessKeySecret, title, fileName);
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

    private static void uploadFromDisk(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
