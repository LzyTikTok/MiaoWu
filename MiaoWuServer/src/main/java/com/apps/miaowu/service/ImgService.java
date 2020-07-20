package com.apps.miaowu.service;

import com.apps.miaowu.bean.result.APIResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lzy
 * @create 2020/7/16 17:05
 */
public interface ImgService {
    /**
     *  add img
     *
     * @param request http请求
     * @param file 图片文件
     * @return APIResult
     */
    APIResult addImg(HttpServletRequest request, MultipartFile file);

    /**
     * del img
     *
     * @param url 图片url
     * @return APIResult
     */
    APIResult delImg(String url);
}
