package com.study.mapper;

import com.study.model.PushDiagnoseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PushDiagnoseMapper {
    List<PushDiagnoseModel> getDiagnose(@Param("inputs") List<String> inputs);
}
