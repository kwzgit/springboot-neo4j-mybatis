package com.study.controller;

import com.study.mapper.PushDisMapper;
import com.study.model.DiseaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pushDisease")
public class SearchDisController {
    @Autowired
    private PushDisMapper pushDisMapper;

    @GetMapping("/getAll")
    public List<DiseaseModel> getAll() {
        List<DiseaseModel> models = pushDisMapper.getAll();
        return models;
    }
    /**
     * 根据id查询（含节点关系）
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public DiseaseModel getById(@PathVariable Long id) {
        DiseaseModel model = pushDisMapper.getById(id);
        return model;
    }
    @PostMapping("/add")
    public int add(@RequestBody DiseaseModel model) {
        int i = pushDisMapper.add(model);
        return i;
    }

    @PostMapping("/update")
    public int update(@RequestBody DiseaseModel model) {
        int i = pushDisMapper.update(model);
        return i;
    }

    @DeleteMapping("/deleteById/{disId}")
    public int deleteById(@PathVariable Long disId) {
        int i = pushDisMapper.deleteById(disId);
        return i;
    }
}
