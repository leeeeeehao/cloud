package com.example.es.test;

import org.w3c.dom.css.Rect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import javax.swing.JFrame;


/**
 * User: Leehao
 * Date: 2022/2/23
 * Time: 15:20
 * Description:
 */
public class test extends JFrame {

    test() {
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(0, 0, 50, 50);
//        g.fillRect(150, 150, 50, 50);
    }

    /**
     * 判断某一个闭合区域是否包含某一个坐标点，areaX和areaY有序坐标数组是闭合区域的坐标点
     *
     * @param areaX  闭合区域X坐标有序数组
     * @param areaY  闭合区域Y坐标有序数组
     * @param pointX 某一点的X坐标
     * @param pointY 某一点的Y坐标
     * @return true point在area区域  false point不在area区域
     */
    public static boolean areaContains(double areaX[], double areaY[], double pointX, double pointY) {
        Path2D path = new Path2D.Double();
        path.moveTo(areaX[0], areaY[0]);
        for (int i = 1; i < areaX.length; ++i) {
            path.lineTo(areaX[i], areaY[i]);
        }
        path.closePath();

        return path.contains(pointX, pointY);
    }

    public static void main(String[] args) {
        double areaX[] = {10, 10, 20, 20};
        double areaY[] = {10, 20, 20, 10};
        System.out.printf(areaContains(areaX, areaY, 13.34000000, 19.00000000) + "");
    }

}
