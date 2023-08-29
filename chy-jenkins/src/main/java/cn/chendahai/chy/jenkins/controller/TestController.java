package cn.chendahai.chy.jenkins.controller;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
import com.cdancy.jenkins.rest.domain.job.*;
import com.cdancy.jenkins.rest.domain.system.SystemInfo;
import com.cdancy.jenkins.rest.features.JobsApi;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/getApplicationName")
    public String getApplicationName() {
        logger.info("application name: {}", applicationName);
        return "success→" + applicationName;
    }


    /**
     * https://blog.csdn.net/m0_37577255/article/details/115997781
     */
    public static void main(String[] args) {
        JenkinsClient client = JenkinsClient.builder()
                .endPoint("https://jenkins.gbank.team") // Optional. Defaults to http://127.0.0.1:8080
                .credentials("chenhaiyang:***") // Optional.
                .build();

        SystemInfo systemInfo = client.api().systemApi().systemInfo();
        System.out.println(systemInfo.jenkinsVersion());

        JobsApi jobsApi = client.api().jobsApi();

        String jobName = "casino-gaming.bonus";

        // 获取job信息
        JobInfo jobInfo = jobsApi.jobInfo(null, jobName);
        log.info("jobInfo:{}", jobInfo);


        // 参数化构建
        Map<String, List<String>> param = new HashMap<>();
        param.put("branch", Collections.singletonList("shenzhen/dev"));
        param.put("environment", Collections.singletonList("test-bjhw"));
//        IntegerResponse integerResponse = jobsApi.buildWithParameters(null, jobName, param);
//        System.out.println("buildWithParameters integerResponse:" + integerResponse);


        // 最后构建的序号
        Integer lastBuildNumber = jobsApi.lastBuildNumber(null, jobName);
        System.out.println("lastBuildNumber:" + lastBuildNumber);


        /**
         * 获取任务构建对应的控制台输出
         * param 文件夹路径null  任务名称“test"   0:控制台输出文本开始的位置
         * 获取指定的序号的控制台输出，不指定默认是最新的
         */
//        ProgressiveText progressiveText = jobsApi.progressiveText(null, "chy-test-other", 0);
        ProgressiveText progressiveText = jobsApi.progressiveText(null, jobName, 2, 0);
        log.info("progressText:" + progressiveText);


        /**
         * 获取任务构建index对应的构建步骤内容
         * param 文件夹路径null  任务名称"pipeline-hello-word"    构建index:17
         */
        Workflow workFlow = jobsApi.workflow(null, jobName, 2);
        log.info("workFlow:" + workFlow);

    }

}
