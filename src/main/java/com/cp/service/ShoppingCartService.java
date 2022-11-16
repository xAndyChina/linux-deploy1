package com.cp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    //客户端的套餐或者是菜品数量减少设置
    public ShoppingCart sub(ShoppingCart shoppingCart);
    /**
     * 清空购物车
     * @return
     */
    public void  clean();
}
