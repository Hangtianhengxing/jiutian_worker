package com.data.linked;

/**
 * Created on 2019-06-13
 *
 * @author :hao.li
 */
public class testSingleLinkedList {
    public static void main(String[] args) {


        SingleLinkedList singleList = new SingleLinkedList();
        singleList.addHead("A");
        singleList.addHead("B");
        singleList.addHead("C");
        singleList.addHead("D");
        //打印当前链表信息
        singleList.display();
        //删除C
        singleList.delete("C");
        singleList.display();
        //查找B
        System.out.println(singleList.find("B"));
    }
}