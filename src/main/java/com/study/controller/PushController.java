package com.study.controller;

import com.study.model.bean.Filnlly;
import com.study.model.result.NeoParamVO;
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
     * @param neoParamVO
     * @return
     */
    @PostMapping("/getDiagnose")
    public Map<String, Map<String, String>> getDiagnose(@RequestBody NeoParamVO neoParamVO) {
        List<String> inputs = neoParamVO.getInputs();
        String webDiag = neoParamVO.getWebDiag();
        Map<String, Map<String, String>> pushDiagnoseMap = push.pushDiagnose(inputs, webDiag);
        return pushDiagnoseMap;
    }

    /**
     * 推送界面诊断对应的不良反应
     * @param neoParamVO
     * @return
     */
    @PostMapping("/getTreat")
    public Map<String, Filnlly> getTreat(@RequestBody NeoParamVO neoParamVO){
        String webDiag = neoParamVO.getWebDiag();
        Integer disType = neoParamVO.getDisType();
        List<String> inputs = neoParamVO.getInputs();
        List<String> webDiags = Arrays.asList(webDiag.split(","));
        Map<String, Filnlly> filnllyMap = push.pushTreat(webDiags, inputs, disType);
        return filnllyMap;
    }

}
