package com.kmd.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmd.reggie.common.CustomException;
import com.kmd.reggie.entity.Category;
import com.kmd.reggie.entity.Dish;
import com.kmd.reggie.entity.Setmeal;
import com.kmd.reggie.mapper.CategoryMapper;
import com.kmd.reggie.service.CategoryService;
import com.kmd.reggie.service.DishService;
import com.kmd.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long categoryId) {
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, categoryId);
        if (dishService.count(dishWrapper) > 0) {
            throw new CustomException("当前分类下已关联菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId, categoryId);
        if (setmealService.count(setmealWrapper) > 0) {
            throw new CustomException("当前分类下已关联套餐，无法删除");
        }
        super.removeById(categoryId);
    }
}
