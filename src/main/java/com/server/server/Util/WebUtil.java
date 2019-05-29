package com.server.server.Util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUEST_WITH = "X-Requested-With";

    public static boolean isAjax(HttpServletRequest request){
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUEST_WITH));
    }
}
