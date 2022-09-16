package com.realfinance.sofa.cg.util;

import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;

public class LinkUtils {
    private static String SERVLET_CONTEXT_PATH = StringUtils.defaultString(SpringContextHolder.getApplicationContext()
            .getEnvironment().getProperty("server.servlet.context-path"),"");

    public static String createFileDownloadLink(FileToken fileToken) {
        return SERVLET_CONTEXT_PATH + "/file/download?fileToken=" + FileTokens.encode(fileToken);
    }
}
