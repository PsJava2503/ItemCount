package com.psj.itemcount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 帧差法中图像检测后对图像中工件的一个计数实现
 * 基于LRU思想
 * @author Pishaojun
 *
 */

public class LruCount {
    // 图像中同时并行穿过最大为20个，所以定容为32
    private Map<Frame, DLinkedNode> cache = new HashMap<Frame, DLinkedNode>(32);
    private static int capacity = 32;
    private int size;
    private DLinkedNode head, tail;
    private static int count;

    public LruCount() {
        this.size = 0;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
        count = 0;
    }

    // 其实没有用get，但还是写了
    public List<Frame> get(Frame key) {
        DLinkedNode node = cache.get(key);
        if (node == null)
            return null;
        moveToHead(node);
        return node.getFrameList();
    }

    // 每张图片都有一堆Frame
    public void put(Frame frame) {
        DLinkedNode node = cache.get(frame);
        if (node == null) {
            ArrayList<Frame> frameList = new ArrayList<>();
            frameList.add(frame);
            DLinkedNode newNode = new DLinkedNode(frame, frameList);
            cache.put(frame, newNode);
            addToHead(newNode);
            size++;
            if (size > capacity) {
                DLinkedNode remNode = removeTail();
                cache.remove(remNode.getFrame());
                size--;
                // 图像中同时并行穿过最大为20个，定容为32
                // 当超过32时说明改该工件以穿过录制范围，移除并且判断录制范围内五帧数中是否出现过3帧，实则计数
                if (remNode.getFrameList().size() >= 3) {
                    count++;
                }
            }
        } else {
            List<Frame> frameList = node.getFrameList();
            frameList.add(frame);
            moveToHead(node);
        }
    }

    // LRU对链表的一些操作实现
    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }


    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode remnode = tail.prev;
        removeNode(remnode);
        return remnode;
    }

    // 当关闭程序时触发，遍历链表查询出现过3帧的Frame，计数
    public void shutdown() {
        DLinkedNode cur = head.next;
        while (cur != null) {
            List<Frame> frameList = cur.getFrameList();
            if (frameList.size() >= 3) {
                count++;
            }
            cur = cur.next;
        }
    }
}



