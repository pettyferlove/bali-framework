package com.github.bali.core.framework.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * @author 莫世源
 * @version 1.0.0
 */
@UtilityClass
public class RequestUtils {
	public String[] names = {"Proxy-Client-IP","proxy-client-ip",
    		"WL-Proxy-Client-IP","wl-proxy-client-ip",
    		"WL-Proxy-Client-IP","wl-proxy-client-ip",
    		"HTTP_CLIENT_IP","http_client_ip",
    		"HTTP_X_FORWARDED_FOR","http_x_forwarded_for",
    		"X-Real-IP","x-real-ip"};

	public String getRequestURL(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURL());
		if (request.getQueryString() != null) {
			sb.append('?').append(request.getQueryString());
		}
		return sb.toString();
	}


	/**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
	public String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!= -1 ){
                ip = ip.split(",")[0];
            }
        }
        if(isRealClientIp(ip)) {
        	 return ip;
        }
		for (String name : names) {
        	 ip = request.getHeader(name);
        	 boolean flag = isRealClientIp(ip);
        	 if (flag) {
        		 break;
             }
		}
        if (!isRealClientIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

	public boolean isRealClientIp(String ip) {
		return !(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip));
	}
}
