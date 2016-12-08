package com.soaringcloud.kit;

import com.soaringcloud.kit.box.LogKit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        LogKit.e(this, "addition_isCorrect error");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLogKit() throws Exception {
        LogKit.e(this, "testLogKit");
    }
}