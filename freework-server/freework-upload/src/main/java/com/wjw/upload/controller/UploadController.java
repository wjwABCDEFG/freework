package com.wjw.upload.controller;

import com.wjw.common.entity.Result;
import com.wjw.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author wjw
 * @date 2021/3/4 17:52
 */
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public Result uploadImg(@RequestParam("file") MultipartFile file){
        Map<String, String> path = uploadService.uploadImg(file);
        if (path == null){
            return Result.error();
        }
        return Result.ok().data(path);
    }
}
