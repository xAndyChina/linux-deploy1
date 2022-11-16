package com.cp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.entity.User;
import com.cp.mapper.UserMapper;
import com.cp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private JavaMailSender javaMailSender;
    //发送人
    //把yml配置的邮箱号赋值到from
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendMsg(String to,String subject,String context) {
//        SimpleMailMessage message=new SimpleMailMessage();
        //        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(context);
        try {
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(context,true);

//            //添加附件
//            File f1=new File("D:\\Program\\SpringBoot\\springboot_18_mail\\target\\springboot_18_mail-0.0.1-SNAPSHOT.jar");
//            File f2=new File("D:\\Program\\SpringBoot\\springboot_18_mail\\src\\test\\java\\com\\cp\\Springboot18MailApplicationTests.java");
//            helper.addAttachment(f1.getName(),f1);
//            helper.addAttachment("测试文件",f2);
            javaMailSender.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }

}}
