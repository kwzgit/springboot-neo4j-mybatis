package com.study.service.impl;

import com.study.mapper.PushConditionMapper;
import com.study.mapper.PushDiagnoseMapper;
import com.study.mapper.PushDisMapper;
import com.study.mapper.UntowardEffectMapper;
import com.study.model.ConditionModel;
import com.study.model.DiseaseModel;
import com.study.model.PushDiagnoseModel;
import com.study.model.UntowardEffectModel;
import com.study.service.Push;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
@Service
public class PushImpl implements Push {
    @Autowired
    private PushConditionMapper pushConditionMapper;
    @Autowired
    private PushDiagnoseMapper pushDiagnoseMapper;
    @Autowired
    private PushDisMapper pushDisMapper;
    @Autowired
    private UntowardEffectMapper untowardEffectMapper;

    /**
     * 推送诊断
     * @param inputs   传入的分词集合
     * @param webDiag  传入的界面诊断
     * @return
     */
    public Map<String, Map<String, String>> pushDiagnose(List<String> inputs, String webDiag) {
        Map<String, Map<String, String>> firestCondition = this.getFirestCondition(inputs, webDiag);
        return firestCondition;
    }
    /**
     * @param inputs 输入的词
     * @return
     */
    public Map<String, Map<String, String>>  getFirestCondition(List<String> inputs, String webDiag) {
        List<PushDiagnoseModel> diagnose = null;
        List<ConditionModel> conditionModels = null;
        // 查询输入的词所在的number
        conditionModels = pushConditionMapper.firstCondition(inputs);
        inputs.clear();
        if (conditionModels != null) {
            for (ConditionModel condition : conditionModels) {
                inputs.add(condition.getName());
            }
        }
        while (conditionModels.size() > 0) {
            //推出符合的诊断依据
            conditionModels = pushConditionMapper.secondCondition(inputs);
            if (conditionModels.size() > 0) {
                inputs.clear();
                for (ConditionModel condition : conditionModels) {
                    inputs.add(condition.getName());
                }
            }
        }
        if (inputs.size() > 0) {
            //推出确诊，拟诊，警惕
            diagnose = pushDiagnoseMapper.getDiagnose(inputs);
        }
        Map<String, Map<String, String>> diagnoseMap = this.processDiagnose(diagnose, webDiag);
        return diagnoseMap;
    }

    /**
     * 处理推出来的诊断（确诊、拟诊、警惕）
     *
     * @param diagnose
     * @param webDiag  //界面诊断
     */
    public Map<String, Map<String, String>> processDiagnose(List<PushDiagnoseModel> diagnose, String webDiag) {
        Map<String, Map<String, String>> diseaseCondition = new LinkedHashMap<String, Map<String, String>>();
        Map<String, Map<String, String>> neoPushMap = new HashMap<String, Map<String, String>>();
        Map<String, Map<String, String>> diseaseCondition1 = new LinkedHashMap<String, Map<String, String>>(16, 0.75f, true);
        //把同一诊断名的不同诊断放到同一个map里
        if (diagnose != null && diagnose.size() > 0) {
            Map<String, String> typeMap = null;
            String name = null, type = null;
            for (PushDiagnoseModel pumodel : diagnose) {
                name = pumodel.getName();
                type = pumodel.getType();
                typeMap = neoPushMap.get(name);
                if (typeMap != null && typeMap.keySet().size() > 0) {
                    if (typeMap.containsKey("拟诊") && "确诊".equals(type)) {
                        typeMap.remove("拟诊");
                        typeMap.put("确诊", "");
                        neoPushMap.put(name, typeMap);
                    } else if ("警惕".equals(type)) {
                        typeMap.put("警惕", "");
                        neoPushMap.put(name, typeMap);
                    } else if ("拟诊".equals(type)) {
                        typeMap.put("拟诊", "");
                        neoPushMap.put(name, typeMap);
                    }
                } else {
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put(type, "");
                    neoPushMap.put(name, newMap);
                }
            }
            /**
             * 把其他警惕去除
             */
            Map<String, Map<String, String>> queHighMap = new HashMap<String, Map<String, String>>();
            Map<String, Map<String, String>> highMap = new HashMap<String, Map<String, String>>();
            Map<String, Map<String, String>> quezhenMap = new HashMap<String, Map<String, String>>();
            String disName = null;
            Set<String> types = null;
            for (Map.Entry<String, Map<String, String>> l : neoPushMap.entrySet()) {
                disName = l.getKey();
                types = l.getValue().keySet();
                if (types.size() == 2) {//包含确诊和警惕
                    queHighMap.put(disName, l.getValue());
                }
                if (types.size() == 1 && (types.contains("确诊") || types.contains("拟诊"))) {
                    quezhenMap.put(disName, l.getValue());
                }
                if (types.size() == 1 && types.contains("警惕")) {
                    highMap.put(disName, l.getValue());
                }
            }
            if(queHighMap != null && queHighMap.size()>0){
                Set<String> queHighSet = queHighMap.keySet();
                for (String qh:queHighSet) {
                    diseaseCondition.put(qh,queHighMap.get(qh));
                }
                if(quezhenMap != null && quezhenMap.size()>0){
                    Set<String> queDis = quezhenMap.keySet();
                    for (String dis:queDis) {
                        diseaseCondition.put(dis,quezhenMap.get(dis));
                    }
                }
            }else {
                if(quezhenMap != null && quezhenMap.size()>0){
                    Set<String> queDis = quezhenMap.keySet();
                    for (String dis:queDis) {
                        diseaseCondition.put(dis,quezhenMap.get(dis));
                    }
                }
                if(highMap != null && highMap.size()>0){
                    Set<String> highSet = highMap.keySet();
                    for (String dis:highSet) {
                        diseaseCondition.put(dis,highMap.get(dis));
                    }
                }
            }

            //所有的推送，包括确诊，拟诊，警惕
            Set<String> queSet = diseaseCondition.keySet();
            //查找鉴别诊断
            if (!StringUtils.isEmpty(webDiag)) {
                String mainDiag = webDiag.split(",")[0];//主诊断
                List<DiseaseModel> differentDiagnose = pushDisMapper.getDifferentDiagnose(mainDiag);
                if (differentDiagnose != null && differentDiagnose.size() > 0) {
                    for (DiseaseModel dd : differentDiagnose) {
                        name = dd.getName();
                        if (queSet.contains(name)) {
                            Map<String, String> stringStringMap = diseaseCondition.get(name);
                            stringStringMap.put("鉴别诊断", "");
                            diseaseCondition.put(name, stringStringMap);
                        } else {
                            Map<String, String> diffMap = new HashMap<String, String>();
                            diffMap.put("鉴别诊断", "");
                            diseaseCondition.put(name, diffMap);
                        }

                    }
                }
            }
            //查找急诊 单独的鉴别诊断和警惕不参与
            List<String> newDis = new ArrayList<String>();
            for (Map.Entry<String, Map<String, String>> fs : diseaseCondition.entrySet()) {
                boolean b = (fs.getValue().keySet().contains("确诊") || fs.getValue().keySet().contains("拟诊")) ? newDis.add(fs.getKey()) : false;
            }
            if(newDis.size() > 0){
                List<DiseaseModel> emergencyDiagnose = pushDisMapper.getEmergencyDiagnose(newDis);
                if(emergencyDiagnose != null && emergencyDiagnose.size() > 0){
                    for (DiseaseModel d:emergencyDiagnose) {
                        Map<String, String> stringStringMap = diseaseCondition.get(d.getName());
                        stringStringMap.put("急诊","");
                        diseaseCondition.put(d.getName(),stringStringMap);
                    }
                }
            }
            //确诊在前，拟诊在后
            Set<String> ll = new LinkedHashSet<String>();
            Set<String> kk = new LinkedHashSet<String>();
            for (Map.Entry<String,Map<String,String>> l:diseaseCondition.entrySet()) {
                if(l.getValue().keySet().contains("确诊")){
                    ll.add(l.getKey());
                }else {
                    kk.add(l.getKey());
                }
            }
            ll.addAll(kk);
            for (String dis:ll) {
                Map<String, String> stringStringMap = diseaseCondition.get(dis);
                for (Map.Entry<String,String>sd:stringStringMap.entrySet()) {
                    if("拟诊".equals(sd.getKey())){
                        stringStringMap.remove(sd.getKey());
                        stringStringMap.put("确诊","");
                        break;
                    }
                }
                diseaseCondition1.put(dis,stringStringMap);
            }

        }
        return diseaseCondition1;
    }

    /**
     * 推送界面诊断对应的不良反应
     * @param webDiagList 界面诊断列表
     * @return
     */
    public Map<String, List<String>> pushUntowardEffect(List<String> webDiagList) {
        Map<String, List<String>> untowardEffects = new HashMap<String, List<String>>();
        //查找页面诊断中是否有不良反应
        List<UntowardEffectModel> untowardEffectList = untowardEffectMapper.getUntowardEffectList(webDiagList);
        if(untowardEffectList != null && untowardEffectList.size()>0){
            String name =null,utName = null;
            List<String> stringList =null;
            for (UntowardEffectModel ut:untowardEffectList) {
                name = ut.getName();
                utName = ut.getUtName();
                stringList = untowardEffects.get(name);
                if(stringList ==null){
                    List<String> s= new ArrayList<String>();
                    s.add(utName);
                    untowardEffects.put(name,s);
                }else {
                    stringList.add(utName);
                    untowardEffects.put(name,stringList);
                }

            }
        }
        return untowardEffects;
    }
}
