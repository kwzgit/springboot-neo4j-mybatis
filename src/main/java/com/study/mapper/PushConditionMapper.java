package com.study.mapper;

import com.study.model.ConditionModel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PushConditionMapper {

    List<ConditionModel> firstCondition(@Param("inputs") List<String> inputs);
    List<ConditionModel> secondCondition(@Param("inputs") List<String> inputs);
}
