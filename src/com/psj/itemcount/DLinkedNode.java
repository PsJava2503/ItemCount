package com.psj.itemcount;

import java.util.List;

/**
 * 双向链表，同LRU
 * @author Pishaojun
 *
 */

public class DLinkedNode {

    Frame frame;
    List<Frame> frameList;
    DLinkedNode prev;
    DLinkedNode next;

    public DLinkedNode() {
    }

    public DLinkedNode(Frame frame, List<Frame> frameList) {
        this.frame = frame;
        this.frameList = frameList;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public List<Frame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }
}
