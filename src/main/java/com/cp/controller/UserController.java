package com.cp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cp.common.R;
import com.cp.entity.User;
import com.cp.service.UserService;
import com.cp.utils.TencentSMSUtils;
import com.cp.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


//    /**
//     * 发送手机短信验证码
//     * @param user
//     * @return
//     */
//    @PostMapping("/sendMsg")
//    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
//        String phone = user.getPhone();
//        if (phone != null) {
//            //获取手机号
//            //生成随机的验证码
//            String code = ValidateCodeUtils.generateValidateCode(6).toString();//生成6位验证码
//            try {
//                TencentSMSUtils.sendShortMessage(TencentSMSUtils.VALIDATE_CODE, phone, code, TencentSMSUtils.CHINA_NATION_CODE);
//                session.setAttribute(phone, code);
//                return R.success("手机短信验证码发送成功");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return R.error("短信发送失败");
//    }
    //获取验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpSession session, @RequestBody User user){
        //获取邮箱号
        //相当于发送短信定义的String to
        String email = user.getPhone();
        String subject = "瑞吉外卖";
        //StringUtils.isNotEmpty字符串非空判断
        if (StringUtils.isNotEmpty(email)) {
            //发送一个四位数的验证码,把验证码变成String类型
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            String text = "【瑞吉外卖】您好，您的登录验证码为：" + code + "，请尽快登录，如非本人操作，请忽略此邮件。";
            log.info("验证码为：" + code);
            //发送短信
            userService.sendMsg(email,subject,text);
            //将验证码保存到session当中
            //将邮箱作为key，将code最为value保存到session中,，因此邮箱和验证码可以一一对应
            session.setAttribute(email,code);
            return R.success("验证码发送成功");
        }
        return R.error("验证码发送异常，请重新发送");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
                                                                                                                                                                                                   log.info(map.toString());

        //获取邮箱号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        Object codeInfoSession = session.getAttribute(phone);

        //进行验证比对(页面提交的Session中保存的验证码比对)
        if (codeInfoSession != null&&codeInfoSession.equals(code)) {
            //如果能够比对成功，说明登录成功
            //判断当前邮件对应用户是否为新用户，如果是新用户则完成自动注册
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //没查出来是新用户
                //判断当前邮件对应用户是否为新用户，如果是新用户则完成自动注册
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);

                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

}
