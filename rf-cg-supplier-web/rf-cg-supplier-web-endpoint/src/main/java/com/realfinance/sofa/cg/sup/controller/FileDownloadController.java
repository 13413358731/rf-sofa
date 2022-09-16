package com.realfinance.sofa.cg.sup.controller;

import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@Tag(name = "文件下载")
@RequestMapping("/file")
public class FileDownloadController {

    @javax.annotation.Resource
    private FileStore fileStore;

    @GetMapping("download")
    @Operation(summary = "下载")
    public void fileDownload(@RequestParam("fileToken") String token,
                             Authentication authentication,
                             HttpServletResponse response) {
        FileToken fileToken = FileTokens.decode(token);
        if (fileToken.isExpired()) {
            throw new RuntimeException("下载链接已过期");
        }
        /*if (fileToken.getUsername() != null && !fileToken.getUsername().isEmpty()) {
            if (!fileToken.getUsername().equals(authentication.getName())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                throw new RuntimeException("无权访问该链接");
            }
        }*/
        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileToken.getFileName(), StandardCharsets.UTF_8));
            fileStore.download(fileToken.getFileId(), response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("下载失败：" + e.getMessage());
        }
    }
}
