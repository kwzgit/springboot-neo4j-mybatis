package com.study.mapper;

import com.study.model.UntowardEffectModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UntowardEffectMapper {

    List<UntowardEffectModel> getUntowardEffectList(@Param("disList") List<String> disList);
}
