package com.kmd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmd.reggie.dto.SetmealDto;
import com.kmd.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐及套餐关联表中的餐品
     *
     * @param ids 路径后传过来的套餐id
     */
    void removeWithDish(List<Long> ids);

    SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);

    /*    void updateWithDish(Long id,SetmealDto setmealDto);*/
}
