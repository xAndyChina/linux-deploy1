package com.cp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.entity.SetmealDish;
import com.cp.mapper.SetmealDisheMapper;
import com.cp.service.SetmealDisheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDisheImpl extends ServiceImpl<SetmealDisheMapper,SetmealDish> implements SetmealDisheService {
}
