package com.apps.miaowu.service.impl;

import com.apps.miaowu.bean.result.APIResult;
import com.apps.miaowu.bean.result.ResultCode;
import com.apps.miaowu.bean.result.ResultEnum;
import com.apps.miaowu.service.ImgService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author lzy
 * @create 2020/7/16 17:05
 */

@Service
public class ImgServiceImpl implements ImgService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public APIResult addImg(HttpServletRequest req, MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String sourcePath = "miaowuimg";
        String filePath = sourcePath + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return APIResult.newResult(ResultEnum.SUCCESS, url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return APIResult.newResult(ResultCode.BadRequest, "upload fail", null);
    }

    @Override
    public APIResult delImg(String url) {
        return APIResult.newResult(ResultEnum.NO_CONTENT, null);
    }
}
