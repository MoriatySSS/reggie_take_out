package com.kmd.reggie.dto;

import com.kmd.reggie.entity.Setmeal;
import com.kmd.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
