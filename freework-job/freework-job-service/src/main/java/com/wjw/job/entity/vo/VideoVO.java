package com.wjw.job.entity.vo;

import com.wjw.job.entity.User;
import com.wjw.job.entity.Video;

/**
 * @author wjw
 * @date 2021/5/20 16:53
 */
public class VideoVO extends Video {

    private User uploadUser;
    private String videoPath;

    public User getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
