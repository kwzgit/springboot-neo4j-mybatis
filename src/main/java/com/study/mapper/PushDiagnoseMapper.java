package com.study.mapper;

import com.study.model.PushDiagnoseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PushDiagnoseMapper {
    /**
     * 根据输入的词获取诊断
     * @param inputs
     * @return
     */
    List<PushDiagnoseModel> getDiagnose(@Param("inputs") List<String> inputs);

    /**
     * 根据输入的词获取不良反应
     * @param inputs
     * @return
     */
    List<PushDiagnoseModel> getUe(@Param("inputs") List<String> inputs);

    /**
     * 根据输入的词获取指标
     * @param inputs
     * @return
     */
    List<PushDiagnoseModel> getInd(@Param("inputs") List<String> inputs);
}
