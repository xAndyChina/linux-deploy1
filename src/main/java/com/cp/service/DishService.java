package com.cp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.dto.DishDto;
import com.cp.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品,同时插入口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据id来查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //修改菜品信息同时更改对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //根据传过来的id批量或者是单个的删除菜品，并判断是否是启售的
    public void deleteById(List<Long> id);

    //菜品批量删除和单个删除，删除时用到deleteByIds方法删除菜品
    public boolean deleteInSetmeal(List<Long> id);

}
