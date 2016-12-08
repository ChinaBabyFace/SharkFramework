package com.shark.wheelpicker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.shark.wheelpicker.test", appContext.getPackageName());
    }

    @Test
    public void buildNumberPicker() throws Exception {
        /*Max>70:00.0,Min>00:05.0*/
//        SuperNumberPicker.Builder builder = new SuperNumberPicker.Builder(InstrumentationRegistry.getTargetContext());
//        builder.addWheel(2).max(65).min(0).triggerMax(70).triggerMin(0).setDefaultValue(45)
//                .create();
    }


}
