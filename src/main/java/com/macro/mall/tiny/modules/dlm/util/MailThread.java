package com.macro.mall.tiny.modules.dlm.util;

public class MailThread implements Runnable{
    @Override
    public void run() {
        MailUtil.sendMail();
    }
}
