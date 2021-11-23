package com.psj.itemcount;

/**
 * 每个工件在图片检测后，都记录为一个框，y轴坐标和面积不变
 * 链表记录面积和y坐标相同的框的数目
 * @author Pishaojun
 *
 */

public class Frame {
    private Integer y;
    private Integer area;

    public Frame(Integer y, Integer area) {
        this.y = y;
        this.area = area;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    // 注意重写hashcode和equals方法
    @Override
    public int hashCode() {
        return (this.getY() + "+" + this.getArea()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Frame frame2 = (Frame)obj;
        return obj instanceof Frame && this.getY() == frame2.getY() && this.getArea() == this.getArea() ;
    }

}
