package com.liulin.framework.web.domain;

/**
 * @Author: LinLiu
 * @Date: 2021/8/26 10:22 上午
 */
public class MailDomain {

    private String from;

    private String receiver;

    /**
     * 秘密抄送 用逗号隔开
     */
    private String bcc;

    private String content;

    private String subject;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
