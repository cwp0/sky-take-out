package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.admin
 * @Class: DishController
 * @Description: 菜品管理
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 13:26
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * @Description: 新增菜品
     * @Param: dishDTO      {com.sky.dto.DishDTO}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 13:29
     */
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) { // @RequestBody注解用于接收前端传递的json数据
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * @Description: 菜品分页查询
     * @Param: dishPageQueryDTO      {com.sky.dto.DishPageQueryDTO}
     * @Return: com.sky.result.Result<com.sky.result.PageResult>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 15:57
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Description: 根据ids批量删除菜品
     * @Param: ids      {java.util.List<java.lang.Long>}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 16:29
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result delete(@RequestParam List<Long> ids) { // 加了 @RequestParam 注解后，MVC框架会动态解析字符串，并把id提取出来，封装到ids中
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBanch(ids);
        return Result.success();
    }

    /**
     * @Description: 根据id查询菜品详情
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.result.Result<com.sky.vo.DishVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 19:15
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) { // @PathVariable注解用于获取url中的参数
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * @Description: 根据id修改菜品信息和对应的口味信息
     * @Param: dishDTO      {com.sky.dto.DishDTO}
     * @Return: com.sky.result.Result<com.sky.dto.DishDTO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 19:26
     */
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result<DishDTO> update(@RequestBody DishDTO dishDTO) { // @RequestBody注解用于接收前端传递的json数据
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * @Description: 修改菜品状态
     * @Param: id      {java.lang.Long}
     * @Param: status      {java.lang.Integer}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 19:45
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改菜品状态")
    public Result editStatus(@RequestParam Long id, @PathVariable Integer status) {
        log.info("修改菜品状态：id={}, status={}", id, status);
        dishService.editStatus(id, status);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

}

