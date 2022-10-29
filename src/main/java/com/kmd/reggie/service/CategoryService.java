package com.kmd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmd.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long categoryId);
}
