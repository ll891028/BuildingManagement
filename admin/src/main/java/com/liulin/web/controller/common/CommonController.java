package com.liulin.web.controller.common;

import com.liulin.common.config.LiulinConfig;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.constant.Constants;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.FileInfo;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.file.AwsFileUtils;
import com.liulin.common.utils.file.FileUtils;
import com.liulin.system.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author liulin
 */
@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = LiulinConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            SysDept building = ShiroUtils.getSysUser().getBuilding();
            String fileName = "";
            String url = "";
            String fileOriName = file.getOriginalFilename().replace(" ", "");
//            Attachment attachmentByMd5 = attachmentService.getAttachmentByMd5(Md5Utils.getMD5ByFile(FileUtils.multipartFileToFile(file)));
//            if (attachmentByMd5 != null) {
//                //MD5重复 不再保存文件直接使用上次的地址
//                url = attachmentByMd5.getAttachmentUrl();
//                fileName = fileOriName;
//            } else {
                // 上传文件路径
                String filePath = LiulinConfig.getUploadPath();
                // 上传并返回新文件名称
//              fileName = FileUploadUtils.upload(filePath, file);
                File savedFile = new File(filePath+"/"+fileName);
                file.transferTo(savedFile);
                String keyName = "attachments/"+building.getDeptId()+"/"+fileName;
                AwsFileUtils.putObject(keyName,savedFile);
                savedFile.delete();
                url = AwsFileUtils.getUrl(keyName);
//            }


            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("originalFileName", fileOriName);
            ajax.put("url", url);
            ajax.put("fileSize", file.getSize());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/common/uploads")
    @ResponseBody
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
        SysDept building = ShiroUtils.getSysUser().getBuilding();
        try {
            // 上传文件路径
            String filePath = LiulinConfig.getUploadPath();
            List<FileInfo> fileInfos = new LinkedList<FileInfo>();
            for (MultipartFile file : files) {
                String fileName = "";
                String url = "";
                File tempFile = FileUtils.multipartFileToFile(file);
                String fileOriName = file.getOriginalFilename().replace(" ", "");
//                Attachment attachmentByMd5 = attachmentService.getAttachmentByMd5(Md5Utils.getMD5ByFile(tempFile));
//                if (attachmentByMd5 != null) {
//                    //MD5重复 不再保存文件直接使用上次的地址
//                    url = attachmentByMd5.getAttachmentUrl();
//                    fileName = fileOriName;
//                } else {
                    // 上传并返回新文件名称
                    String keyName = "attachments/"+building.getDeptId()+"/"+fileOriName;
                    AwsFileUtils.putObject(keyName,tempFile);
                    url = AwsFileUtils.getUrl(keyName);
//                }
                tempFile.delete();
                fileInfos.add(new FileInfo(fileName, url, fileOriName,file.getSize()));
            }
            return AjaxResult.success(fileInfos);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = LiulinConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    @GetMapping("/error/comingSoon")
    public String comingSoon() {
       return "error/comingSoon";
    }
}
