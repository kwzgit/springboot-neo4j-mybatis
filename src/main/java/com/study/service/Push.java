package com.study.service;

import com.study.model.UntowardEffectModel;

import java.util.List;
import java.util.Map;

/**
 * 推送接口
 */
public interface Push {
    /**
     * 知识图谱推送诊断
     * @param inputs   传入的分词集合
     * @param webDiag  传入的界面诊断
     * @return
     */
    Map<String, Map<String, String>> pushDiagnose(List<String> inputs,String webDiag);

    /**
     * 界面诊断对应的不良反应
     * @param webDiagList
     * @return
     */
    Map<String, List<String>> pushUntowardEffect(List<String> webDiagList);


}
