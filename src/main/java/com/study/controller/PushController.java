package com.study.controller;

import com.study.model.bean.Filnlly;
import com.study.model.result.SearchData;
import com.study.service.Push;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * 推送
 */
@RestController
@RequestMapping("/push")
public class PushController {
    @Autowired
    private Push push;

    /**
     * 图谱推送诊断
     * @param searchData
     * @return
     */
    @PostMapping("/getDiagnose")
    public Map<String, Map<String, String>> getDiagnose(@RequestBody SearchData searchData) {
        List<String> inputsList = searchData.getInputsList();
        String webDiag = searchData.getDiag();
        Map<String, Map<String, String>> pushDiagnoseMap = push.pushDiagnose(inputsList, webDiag);
        return pushDiagnoseMap;
    }

    /**
     * 推送界面诊断对应的不良反应
     * @param searchData
     * @return
     */
    @PostMapping("/getTreat")
    public Map<String, Filnlly> getTreat(@RequestBody SearchData searchData){
        List<String> inputsList = searchData.getInputsList();
        String webDiag = searchData.getDiag();
        Integer disType = searchData.getDisType();
        List<String> webDiags = Arrays.asList(webDiag.split(","));
        Map<String, Filnlly> filnllyMap = push.pushTreat(webDiags, inputsList, disType);
        return filnllyMap;
    }

    @PostMapping("/getInd")
    public Set<String> getInd(@RequestBody SearchData searchData){
        List<String> inputsList = searchData.getInputsList();
        Set<String> indSet = push.pushInd(inputsList);
        return indSet;
    }

}
