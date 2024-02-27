package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: chaosssock
 * @Date: 2024/2/26 21:29
 * @Description: 数据字典 前端控制器
 */
@Slf4j
@RestController
public class CourseCategoryController {
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return null;
    }

}