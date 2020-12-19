package cn.chendahai.chy.html2pdf;/**
 * @author chy
 * @date 2020/9/18 0018 下午 14:02
 * Description：
 */

import com.itextpdf.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/9/18 0018
 */
public class JavaToPdfHtmlFreeMarkerBet {

    // TODO 明显点，由于是聚合项目，导致基本路径是变了的

    private static final String DEST = "chy-generate-pdf/target/bet-gg.pdf";
    private static final String HTML = "index7.html";

    private static final String IMG_PATH = "file:///D:/project/javaproject/spring-boot-chy/chy-generate-pdf/src/main/resources/static/";

    private static Configuration freemarkerCfg;

    static {
        freemarkerCfg = new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates/betPrint"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        Map<String, Object> data = new HashMap();
        data.put("name", "dahai");
        String content = JavaToPdfHtmlFreeMarkerBet.freeMarkerRender(data, HTML);
        JavaToPdfHtmlFreeMarkerBet.createPdf(content, DEST);
    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void createPdf(String content, String dest) throws IOException, com.lowagie.text.DocumentException {
        ITextRenderer render = new ITextRenderer();
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL(IMG_PATH);
        render.layout();
        render.createPDF(new FileOutputStream(dest));
    }
}
