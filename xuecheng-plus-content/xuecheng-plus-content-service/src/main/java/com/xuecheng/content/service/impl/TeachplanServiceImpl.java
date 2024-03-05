package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chaosssock
 * @Date: 2024/3/5 21:34
 * @Description: 课程计划service接口实现类
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        // 通过课程计划id判断是新增还是修改
        Long teachplanDtoId = saveTeachplanDto.getId();
        if (null == teachplanDtoId) {
            // 新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            // 确定排序字段
            Long parentid = saveTeachplanDto.getParentid();
            Long courseId = saveTeachplanDto.getCourseId();
            int teachplanCount = getTeachplanCount(courseId, parentid);
            teachplan.setOrderby(teachplanCount+1);
            teachplanMapper.insert(teachplan);
        }else {
            // 修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanDtoId);
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }
    }

    @Override
    public void deleteTeachplan(Long teachplanId) {
        if (teachplanId == null)
            XueChengPlusException.cast("课程计划id为空");
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        // 判断当前课程计划是章还是节
        Integer grade = teachplan.getGrade();
        // 当前课程计划为章
        if (grade == 1) {
            // 查询当前课程计划下是否有小节
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            // select * from teachplan where parentid = {当前章计划id}
            queryWrapper.eq(Teachplan::getParentid, teachplanId);
            // 获取一下查询的条目数
            Integer count = teachplanMapper.selectCount(queryWrapper);
            // 如果当前章下还有小节，则抛异常
            if (count > 0)
                XueChengPlusException.cast("课程计划信息还有子级信息，无法操作");
            teachplanMapper.deleteById(teachplanId);
        } else {
            // 课程计划为节
            teachplanMapper.deleteById(teachplanId);
            LambdaQueryWrapper<TeachplanMedia> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TeachplanMedia::getTeachplanId, teachplanId);
            teachplanMediaMapper.delete(queryWrapper);
        }
    }

    /**
     * @description 获取最新的排序号
     * @param courseId  课程id
     * @param parentId  父课程计划id
     * @return int 最新排序号
     * @author Mr.M
     * @date 2022/9/9 13:43
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        return teachplanMapper.selectCount(queryWrapper);
    }

}
