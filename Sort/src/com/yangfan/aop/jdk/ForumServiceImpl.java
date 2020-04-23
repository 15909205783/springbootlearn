package com.yangfan.aop.jdk;

public class ForumServiceImpl implements ForumService {
    @Override
    public void removeTopic(int topicId) {
        System.out.println("模拟删除Topi记录："+topicId);
        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeForum(int forumId) {
        System.out.println("模拟删除Forum记录："+forumId);
        try {
            Thread.currentThread().sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
