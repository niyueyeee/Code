package cn.itcast.core.controller;

import cn.itcast.common.utils.FastDFSClient;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    //图片服务器URL  properties
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    //上传
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) {

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传图片
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            // group1/sfsfsfsf.jpg
            String path = fastDFSClient.uploadFile(file.getBytes(), ext, null);

            System.out.println(FILE_SERVER_URL + path);
            return new Result(true, FILE_SERVER_URL + path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
