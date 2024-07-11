package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.admin
 * @Class: CommonController
 * @Description: 通用接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/10 22:20
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    /**
     * 阿里云文件上传工具类
     */
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * @Description: 文件上传
     * @Param: file      {org.springframework.web.multipart.MultipartFile}
     * @Return: com.sky.result.Result<java.lang.String>
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 22:24
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 新的文件名称
            String fileName = UUID.randomUUID().toString() + suffix;
            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}

