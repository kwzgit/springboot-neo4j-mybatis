package com.study.service.impl;

import com.study.mapper.*;
import com.study.model.*;
import com.study.model.bean.*;
import com.study.service.Push;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
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
    @Autowired
    private PushTreatMapper pushTreatMapper;

    /**
     * 推送诊断
     *
     * @param inputs  传入的分词集合
     * @param webDiag 传入的界面诊断
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
    public Map<String, Map<String, String>> getFirestCondition(List<String> inputs, String webDiag) {
        List<PushDiagnoseModel> diagnose = null;
        //根据输入的词找诊断依据
        List<String> processInputs = this.processInputs(inputs);
        if (processInputs.size() > 0) {
            //推出确诊，拟诊，警惕
            diagnose = pushDiagnoseMapper.getDiagnose(processInputs);
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
            if (queHighMap != null && queHighMap.size() > 0) {
                Set<String> queHighSet = queHighMap.keySet();
                for (String qh : queHighSet) {
                    diseaseCondition.put(qh, queHighMap.get(qh));
                }
                if (quezhenMap != null && quezhenMap.size() > 0) {
                    Set<String> queDis = quezhenMap.keySet();
                    for (String dis : queDis) {
                        diseaseCondition.put(dis, quezhenMap.get(dis));
                    }
                }
            } else {
                if (quezhenMap != null && quezhenMap.size() > 0) {
                    Set<String> queDis = quezhenMap.keySet();
                    for (String dis : queDis) {
                        diseaseCondition.put(dis, quezhenMap.get(dis));
                    }
                }
                if (highMap != null && highMap.size() > 0) {
                    Set<String> highSet = highMap.keySet();
                    for (String dis : highSet) {
                        diseaseCondition.put(dis, highMap.get(dis));
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
            if (newDis.size() > 0) {
                List<DiseaseModel> emergencyDiagnose = pushDisMapper.getEmergencyDiagnose(newDis);
                if (emergencyDiagnose != null && emergencyDiagnose.size() > 0) {
                    for (DiseaseModel d : emergencyDiagnose) {
                        Map<String, String> stringStringMap = diseaseCondition.get(d.getName());
                        stringStringMap.put("急诊", "");
                        diseaseCondition.put(d.getName(), stringStringMap);
                    }
                }
            }
            //确诊在前，拟诊在后
            Set<String> ll = new LinkedHashSet<String>();
            Set<String> kk = new LinkedHashSet<String>();
            for (Map.Entry<String, Map<String, String>> l : diseaseCondition.entrySet()) {
                if (l.getValue().keySet().contains("确诊")) {
                    ll.add(l.getKey());
                } else {
                    kk.add(l.getKey());
                }
            }
            ll.addAll(kk);
            for (String dis : ll) {
                Map<String, String> stringStringMap = diseaseCondition.get(dis);
                for (Map.Entry<String, String> sd : stringStringMap.entrySet()) {
                    if ("拟诊".equals(sd.getKey())) {
                        stringStringMap.remove(sd.getKey());
                        stringStringMap.put("确诊", "");
                        break;
                    }
                }
                diseaseCondition1.put(dis, stringStringMap);
            }

        }
        return diseaseCondition1;
    }


    /**
     * 推送界面诊断对应的不良反应
     *
     * @param webDiagList 界面诊断列表
     * @return
     */
    public Map<String, List<String>> pushUntowardEffect(List<String> webDiagList) {
        Map<String, List<String>> untowardEffects = new HashMap<String, List<String>>();
        //查找页面诊断中是否有不良反应
        List<UntowardEffectModel> untowardEffectList = untowardEffectMapper.getUntowardEffectList(webDiagList);
        if (untowardEffectList != null && untowardEffectList.size() > 0) {
            String name = null, utName = null;
            List<String> stringList = null;
            for (UntowardEffectModel ut : untowardEffectList) {
                name = ut.getName();
                utName = ut.getUtName();
                stringList = untowardEffects.get(name);
                if (stringList == null) {
                    List<String> s = new ArrayList<String>();
                    s.add(utName);
                    untowardEffects.put(name, s);
                } else {
                    stringList.add(utName);
                    untowardEffects.put(name, stringList);
                }

            }
        }
        return untowardEffects;
    }

    /**
     * 根据输入的词推送不良反应
     *
     * @param inputs
     * @return
     */
    public Set<String> pushUeFromInputs(List<String> inputs) {
        Set<String> ueSet = new HashSet<String>();
        List<String> processInputs = this.processInputs(inputs);
        List<PushDiagnoseModel> ue = pushDiagnoseMapper.getUe(processInputs);
        if (ue.size() > 0 && ue != null) {
            for (PushDiagnoseModel pd : ue
                    ) {
                ueSet.add(pd.getName());
            }
        }

        return ueSet;
    }

    /**
     * 推送治疗方案
     * @param webDiagList
     * @param inputs
     * @param disType
     * @return
     */
    public Map<String, Filnlly> pushTreat(List<String> webDiagList, List<String> inputs,Integer disType) {
        Map<String, Filnlly> diagTreat = new HashMap<String, Filnlly>();
        Map<String, List<String>> webUEMap = this.pushUntowardEffect(webDiagList);
        Set<String> inputsUE = this.pushUeFromInputs(inputs);
        //处理治疗
        Map<String, List<String>> diseFilds = new HashMap<String, List<String>>();
        if (webDiagList.size() > 0) {
            for (int i = 0; i < webDiagList.size(); i++) {
                List<String> fildsList = new ArrayList<String>();
                fildsList.addAll(inputs);
                fildsList.addAll(inputsUE);
                for (int j = 0; j < webDiagList.size(); j++) {
                    if (i != j) {
                        fildsList.add(webDiagList.get(j));
                    }
                }
                diseFilds.put(webDiagList.get(i), fildsList);
            }
        }
        if (diseFilds != null && diseFilds.size() > 0) {
            for (Map.Entry<String, List<String>> df : diseFilds.entrySet()) {
                String dis = df.getKey();
                List<String> inputss = df.getValue();
                //查找诊断对应的类和药物,包括类和药的排序
                List<DrugsMedicationModel> drugsMedication = pushTreatMapper.getDrugsMedication(dis);
                Map<String, LinkedHashMap<String, String>> contentMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
                Map<String, LinkedHashMap<String, String>> newcontentMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
                if (drugsMedication != null && drugsMedication.size() > 0) {
                    String drugs = null, medication = null, meRate = null;
                    for (DrugsMedicationModel dmm : drugsMedication) {
                        drugs = dmm.getDrugs();
                        medication = dmm.getMedication();
                        meRate = dmm.getMeRate();
                        if (contentMap.containsKey(drugs)) {
                            LinkedHashMap<String, String> medicationRateMap = contentMap.get(drugs);
                            medicationRateMap.put(medication, meRate);
                            contentMap.put(drugs, medicationRateMap);
                        } else {
                            LinkedHashMap<String, String> medicationRateMap = new LinkedHashMap<String, String>();
                            medicationRateMap.put(medication, meRate);
                            contentMap.put(drugs, medicationRateMap);
                        }
                    }
                }
                //药类型的大小类拼接
                List<DrugsMedicationModel> drugsBIgShort = pushTreatMapper.getDrugsBIgShort(dis);
                Map<String, String> drugDrugs = new HashMap<String, String>();
                if (drugsBIgShort != null && drugsBIgShort.size() > 0) {
                    for (DrugsMedicationModel dmm : drugsBIgShort) {
                        drugDrugs.put(dmm.getDrugs(), dmm.getMedication());
                    }
                }
                if (contentMap != null && contentMap.size() > 0) {
                    for (Map.Entry<String, LinkedHashMap<String, String>> sd : contentMap.entrySet()
                            ) {
                        String key = sd.getKey();
                        if (drugDrugs.get(key) != null) {
                            newcontentMap.put(drugDrugs.get(key), contentMap.get(key));
                        } else {
                            newcontentMap.put(key, contentMap.get(key));
                        }
                    }
                }
                //处理禁用慎用药类
                List<DrugsMedicationModel> jinShenDrugs = pushTreatMapper.getJinShenDrugs(dis, inputss);
                //处理禁用慎用药
                List<DrugsMedicationModel> jinShenMedication = pushTreatMapper.getJinShenMedication(dis, inputss);
                Map<String, String> drugsUseMap = getJsDM(jinShenDrugs);//key:药类 value:禁忌(慎用)
                Map<String, String> medicationUseMap = getJsDM(jinShenMedication);//key:药名 value:禁忌(慎用)
                ArrayList<Drugs> drugsList = new ArrayList<Drugs>();
                for (Map.Entry<String, LinkedHashMap<String, String>> w : newcontentMap.entrySet()) {
                    int i = 0;
                    Drugs drugs = new Drugs();
                    String drugsName = w.getKey();//药类
                    Map<String, String> bigSubDrugs = bigSubDrugs(drugsName);
                    drugs.setBigdrugsName(bigSubDrugs.get("big"));
                    if (!drugs.getBigdrugsName().equals(bigSubDrugs.get("sub"))) {
                        drugs.setSubdrugsName(bigSubDrugs.get("sub"));
                    }
                    LinkedList<Medicition> medicitionsList = new LinkedList<Medicition>();
                    LinkedHashMap<String, String> meditionRate = w.getValue();
                    String big = drugsUseMap.get(bigSubDrugs.get("big"));
                    String sub = drugsUseMap.get(bigSubDrugs.get("sub"));
                    judgementCategory(medicitionsList,big,sub,meditionRate,i,drugs,medicationUseMap);
                    drugs.setMedicitionsList(medicitionsList);
                    drugsList.add(drugs);
                    System.out.println();

                }
                Filnlly filnlly = new Filnlly();
                if(1 == disType){
                    //诊断下的不良反应列表
                    List<String> ueList = webUEMap.get(dis);
                    if(ueList.size()>0){
                        List<Indicators> indicatorsList1 = new ArrayList<Indicators>();
                        for(String de:ueList){
                            Indicators indicators1 = getAdverse(inputsUE, de);
                            indicatorsList1.add(indicators1);
                        }
                        filnlly.setAdverseEvent(indicatorsList1);
                    }
                }
                filnlly.setTreatment(drugsList);
                diagTreat.put(dis, filnlly);
            }
        }
        return diagTreat;
    }

    /**
     * 指标推送
     * @param inputs
     * @return
     */
    @Override
    public Set<String> pushInd(List<String> inputs) {
        //查找指标推送
        Set<String> indSet = new HashSet<String>();
        //根据输入的词找诊断依据
        List<String> processInputs = this.processInputs(inputs);
        List<PushDiagnoseModel> inds = pushDiagnoseMapper.getInd(processInputs);
        if(inds.size() > 0){
            for (PushDiagnoseModel pdm:inds) {
                indSet.add(pdm.getName());
            }
        }
        return indSet;
    }

    /**
     * 处理输入的词
     *
     * @param inputs
     * @return
     */
    public List<String> processInputs(List<String> inputs) {
        List<String> startList = new ArrayList<String>();
        List<String> newList = new ArrayList<String>();
        List<ConditionModel> conditionModels = null;
        // 查询输入的词所在的number
        conditionModels = pushConditionMapper.firstCondition(inputs);
        if (conditionModels != null) {
            for (ConditionModel condition : conditionModels) {
                startList.add(condition.getName());
            }
        }
        newList.addAll(startList);
        while (conditionModels.size() > 0) {
            //推出符合的诊断依据
            conditionModels = pushConditionMapper.secondCondition(newList, startList);
            if (conditionModels.size() > 0) {
                newList.clear();
                for (ConditionModel condition : conditionModels) {
                    newList.add(condition.getName());
                    startList.add(condition.getName());
                }
            }
        }
        return newList;
    }

    public Map<String, String> getJsDM(List<DrugsMedicationModel> jinShenMedication) {
        Map<String, String> contents = new HashMap<String, String>();
        if (jinShenMedication.size() > 0) {
            for (DrugsMedicationModel dmm : jinShenMedication) {
                String drugs = dmm.getDrugs();
                String medication = dmm.getMedication();
                if (contents.containsKey(drugs)) {
                    if ("慎用".equals(contents.get(drugs)) && "忌用".equals(medication)) {
                        contents.put(drugs, "忌用");
                    }
                } else {
                    contents.put(drugs, medication);
                }
            }
        }
        return contents;
    }

    public Map<String, String> bigSubDrugs(String drugs) {
        Map<String, String> bigSubMap = new HashMap<String, String>();
        String bigDrugs = "", subDrugs = "";
        if (drugs.contains("(")) {
            bigDrugs = drugs.split("\\(")[0];
            subDrugs = drugs.split("\\(")[1].replace(")", "");
        } else {
            bigDrugs = drugs;
            subDrugs = drugs;
        }
        bigSubMap.put("big", bigDrugs);
        bigSubMap.put("sub", subDrugs);
        return bigSubMap;
    }

    public void judgementCategory(List<Medicition> medicitionsList, String big, String sub, LinkedHashMap<String, String> meditionRate, int i, Drugs drugs,Map<String, String> medicationUseMap) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);//设置该百分比数字，保留2位小数;
        nf.setRoundingMode(RoundingMode.HALF_UP); //设置满5向上进位，即四舍五入;
        int sign = 0;
        if ("忌用".equals(big) || "忌用".equals(sub)) {
            sign = 2;
        }else if ("慎用".equals(big) || "慎用".equals(sub)) {
            sign = 1;
        }
        drugs.setDrugsForbidden(sign);//这个药类忌用标志
        for (Map.Entry<String, String> g : meditionRate.entrySet()) {
            Medicition medicition = new Medicition();
            String meditionName = g.getKey().trim();//药名
            String rate = nf.format(Double.parseDouble(g.getValue().trim()));//百分比
            medicition.setMedicitionName(meditionName);
            medicition.setRate(rate);
            if (i < 3) {
                medicition.setIsShow(1);
                i++;
            } else {
                medicition.setIsShow(0);
            }
            if(sign == 0){
                if ("忌用".equals(medicationUseMap.get(meditionName))) {
                    medicition.setForbidden(2);
                } else if ("慎用".equals(medicationUseMap.get(meditionName))) {
                    medicition.setForbidden(1);
                } else {
                    medicition.setForbidden(0);
                }
            }else {
                medicition.setForbidden(sign);
            }
            medicitionsList.add(medicition);
        }
    }
    public Indicators getAdverse(Set<String> dis, String name) {
        List<Detail> detailList1 = new ArrayList<Detail>();
        Indicators indicators1 = new Indicators();
        indicators1.setName(name);
        indicators1.setControlType(2);
        Detail detail1 = new Detail();
        detail1.setName("否");
        detail1.setValue(0);
        Detail detail2 = new Detail();
        detail2.setName("是");
        detail2.setValue(0);
        if (dis.contains(name)) {
            detail2.setValue(1);
        }
        Detail detail3 = new Detail();
        detail3.setName("轻度");
        detail3.setValue(0);
        Detail detail4 = new Detail();
        detail4.setName("中度");
        detail4.setValue(0);
        Detail detail5 = new Detail();
        detail5.setName("重度");
        detail5.setValue(0);
        Detail detail6 = new Detail();
        detail6.setName("偶尔");
        detail6.setValue(0);
        Detail detail7 = new Detail();
        detail7.setName("频繁");
        detail7.setValue(0);
        detailList1.add(detail1);
        detailList1.add(detail2);
        detailList1.add(detail3);
        detailList1.add(detail4);
        detailList1.add(detail5);
        detailList1.add(detail6);
        detailList1.add(detail7);
        indicators1.setDetails(detailList1);
        return indicators1;
    }
}
