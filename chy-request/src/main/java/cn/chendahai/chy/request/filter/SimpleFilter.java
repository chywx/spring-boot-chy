package cn.chendahai.chy.request.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@WebFilter
@Slf4j
public class SimpleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=========RequestBodyParameterReadingFilter.doFilter()=========");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取请求信息
        String requestURI = httpServletRequest.getRequestURI();
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            // 如果处理上传文件数据，下面方法执行到chain.doFilter()时会出线异常，所以此处只处理@RequestBody数据
            if (!StringUtils.isEmpty(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                log.info("=========RequestBodyParameterReadingFilter.doFilter().jsonRequest=========");

                ServletRequest jsonRequest = new CustomizeHttpServletRequestWrapper((HttpServletRequest) request);

                StringBuilder requestParams = getRequestParams(httpServletRequest);
                String requestBody = getRequestBody((HttpServletRequest) request);
                log.info("requestURI:{} body:{} params:{}", requestURI, requestBody, requestParams);
                chain.doFilter(jsonRequest, response);
                return;
            }
        }
        StringBuilder requestParams = getRequestParams(httpServletRequest);
        log.info("requestURI:{} parameterNames:{}", requestURI, requestParams);
        chain.doFilter(request, response);
    }

    private static StringBuilder getRequestParams(HttpServletRequest httpServletRequest) {
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        StringBuilder parameterStr = new StringBuilder();
        while (parameterNames.hasMoreElements()) {
            if (parameterStr.length() > 0) {
                parameterStr.append("&");
            }
            String paramName = parameterNames.nextElement();
            String paramValue = httpServletRequest.getParameter(paramName);
            parameterStr.append(paramName).append("=").append(paramValue);
        }
        return parameterStr;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
