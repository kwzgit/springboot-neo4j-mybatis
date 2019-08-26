package com.study.mapper;

import com.study.model.DrugsMedicationModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PushTreatMapper {
    //获取疾病对应的药类和药
    List<DrugsMedicationModel> getDrugsMedication(@Param("disName") String disName);
    //获取组合药类
    List<DrugsMedicationModel> getDrugsBIgShort(@Param("disName") String disName);
}
