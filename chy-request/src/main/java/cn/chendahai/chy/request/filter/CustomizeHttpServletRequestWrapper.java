package cn.chendahai.chy.request.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class CustomizeHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody = null;//用于将流保存下来

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CustomizeHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        log.info("=========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========");
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        log.info("=========CustomizeHttpServletRequestWrapper.getInputStream()=========");
        final ByteArrayInputStream is = new ByteArrayInputStream(requestBody);

        return new ServletInputStream() {

            @Override
            public int read() {
                return is.read();
            }

            @Override
            public boolean isFinished() {
//                return is.available() == 0;
                System.out.println(is.available());
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do nothing
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        log.info("=========CustomizeHttpServletRequestWrapper.getReader()=========");
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
