package com.shark.framework;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /**
     * 整个类的初始化方法
     */
    @BeforeClass
    public void init() {
    }

    /**
     * 整个类最后执行的方法
     */
    @AfterClass
    public void destory() {

    }

    /**
     * 当任何一个测试方法执行前都会调用的前置方法，初始化
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {


    }

    /**
     * 当任何一个测试方法执行后都会调用的后置方法，资源回收
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {


    }

    /**
     * 测试方法，一个测试类中可以有多个测试方法
     *
     * @throws Exception
     */
    @Test
    public void testFunction() throws Exception {

    }
}