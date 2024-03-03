package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @Auther: chaosssock
 * @Date: 2024/2/1 23:49
 * @Description: 课程基本信息管理业务接口
 */
public interface CourseBaseInfoService {
    /**
     * @description 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 条件条件
     * @author chaosssock
     */
   PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

   /**
    * @description 添加课程基本信息
    * @param companyId  教学机构id
    * @param addCourseDto  课程基本信息
    * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
    * @author Mr.M
    * @date 2022/9/7 17:51
    */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

}
