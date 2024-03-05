package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @Auther: chaosssock
 * @Date: 2024/3/5 21:33
 * @Description: 课程计划管理相关接口
 */
public interface TeachplanService {
    /**
     * @description 查询课程计划树型结构
     * @param courseId  课程id
     * @return List<TeachplanDto>
     * @author Mr.M
     * @date 2022/9/9 11:13
     */
    public List<TeachplanDto> findTeachplanTree(long courseId);

    /**
     * @description 保存课程计划
     * @param teachplanDto  课程计划信息
     * @return void
     * @author Mr.M
     * @date 2022/9/9 13:39
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);

    void deleteTeachplan(Long teachplanId);

    void orderByTeachplan(String moveType, Long teachplanId);
}
