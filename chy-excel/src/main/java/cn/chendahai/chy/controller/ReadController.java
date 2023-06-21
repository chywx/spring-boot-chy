package cn.chendahai.chy.controller;

import cn.chendahai.chy.entity.Recall;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ReadController {

    private Logger logger = LoggerFactory.getLogger(ReadController.class);

    @RequestMapping("/read")
    public String read(@RequestParam("file") MultipartFile file) {
        List<Recall> recallList;
        try {
            recallList = EasyExcel.read(file.getInputStream(), Recall.class, new SyncReadListener())
                    .sheet(0).doReadSync();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        formatDataList(recallList);

        return "read success";
    }

    private void formatDataList(List<Recall> recallList) {
        if (CollectionUtils.isEmpty(recallList)) {
            return;
        }
        // 拼接数据
        String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=recall&gameNamePrefix=activity-innerAddChip-\"";
        for (Recall recall : recallList) {
            if (recall.getUserId() == null) {
                continue;
            }
            System.out.println(String.format(curl, recall.getUserId(), recall.getRewardAmount()));
        }
    }

}
