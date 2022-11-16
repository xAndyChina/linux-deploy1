package com.cp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.common.CustomException;
import com.cp.dto.SetmealDto;
import com.cp.entity.Dish;
import com.cp.entity.Setmeal;
import com.cp.entity.SetmealDish;
import com.cp.mapper.SetmealMapper;
import com.cp.service.SetmealDisheService;
import com.cp.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDisheService setmealDisheService;

    /**
     * 根据id进行删除套餐操作
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> id) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //先查询该套餐是否在售卖，如果是则抛出业务异常
        queryWrapper.in(Setmeal::getId, id);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);

        if (count>0){
            throw new CustomException("套餐正在售卖中，不可删除");
        }
        //可以删除，先删除套餐表中的数据-setmeal
        this.removeByIds(id);
        //删除关系表中的数据--setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,id);
        setmealDisheService.remove(lambdaQueryWrapper);
    }

    /**
     * 新增套餐同时保存菜品和套餐的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息操作setmeal，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品关联的信息，操作setmeal_dish,执行insert操作
        setmealDisheService.saveBatch(setmealDishes);
    }

    /**
     * 根据套餐id查询套餐中的菜品
     */
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishList = setmealDisheService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishList);

        return setmealDto;
    }
    /**
     * 修改菜品
     * @param setmealDto
     */
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        //更新套餐表的套餐
        this.updateById(setmealDto);

        //查询并删除旧的套餐中的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDisheService.remove(queryWrapper);

        //更新传过来的新的菜品，并将其赋予套餐的id
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes=setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //将新传过来的套餐中的菜品其保存到setmeal_dish表中
        setmealDisheService.saveBatch(setmealDishes);
    }


    }




