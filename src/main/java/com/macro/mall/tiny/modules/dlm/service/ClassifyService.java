package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.ClassifyMapper;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.Classify;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("classifyService")
public class ClassifyService extends ServiceImpl<ClassifyMapper,Classify> {
    @Resource
    private ClassifyMapper classifyMapper;

    //拖拽更新，批量更新
    public List<Classify> updateClassifyListByPull(String classifyListStr){
        List<Classify> classifyListParam = null;
        try {
            classifyListParam = JSONArray.parseArray(classifyListStr,Classify.class);
            this.updateBatchById(classifyListParam);
        }catch (Exception e){
            throw new MyException(MyEumException.CLASSIFY_JSON_FORMAT_FAILURE);
        }

        return classifyListParam;
    }

    public List<Classify> getClassifyList(){
        return classifyMapper.getClassifyListBySort();
    }

    public Classify insertClassify(Classify classify){
        if(CommonUtil.StringIsEmpty(classify.getId())){
            classify.setId(CommonUtil.getUUID32());
            classifyMapper.insert(classify);
        }else{
            classifyMapper.updateById(classify);
        }
        return classify;
    }

    public Classify deleteClassify(Classify classify){
        classifyMapper.deleteById(classify.getId());
        return classify;
    }
}
