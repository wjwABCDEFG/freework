package com.wjw;

import org.junit.Test;

/**
 * @author wjw
 * @date 2021/4/15 1:08
 */
public class BitTest {

    @Test
    public void test(){
        int a = 6;
        while (a != 0){
            int i = a & 1;
            System.out.println(i);
            a = a >> 1;
        }
    }
}
