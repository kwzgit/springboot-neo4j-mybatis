package com.study.mapper;

import com.study.model.DiseaseModel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PushDisMapper {
    List<DiseaseModel> getAll();
    DiseaseModel getById(@Param("id") Long id);
    int add(@Param("model") DiseaseModel model);
    int update(@Param("model") DiseaseModel model);
    int deleteById(@Param("disId") Long disId);
    //查找鉴别诊断
    List<DiseaseModel> getDifferentDiagnose(@Param("mainDiag") String mainDiag);

    //查找急诊
    List<DiseaseModel> getEmergencyDiagnose(@Param("disList") List<String> disList);
}
