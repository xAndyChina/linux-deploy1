package com.cp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.dto.SetmealDto;
import com.cp.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //根据传过来的id批量或者是单个的删除菜品，并判断是否是启售的
    public void removeWithDish(List<Long> id);

    /**
     * 新增套餐同时保存菜品和套餐的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    public SetmealDto getByIdWithDish(Long id);
    /**
     * 修改菜品
     */
    public void updateWithDish(SetmealDto setmealDto);
}
