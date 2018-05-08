package com.xjx.renderscriptstudy.tasks;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;

import com.xjx.renderscriptstudy.ScriptC_single_source;

import java.util.Arrays;

/**
 * SingleSource例子，将4维坐标的长度开根号后转成二维45度的。同时支持设置rs中变量的值。
 */
public class SingleSourceTask extends ShowResultTask {
    public SingleSourceTask(Context context) {
        super(context);
    }

    @Override
    protected String doInBackground(Object... objects) {
        if (mContext.get() != null) {
            float[] data = {
                    1.1f, 2.2f, 3.3f, 4.4f,
                    5.5f, 6.6f, 7.7f, 8.8f,
                    9.9f, 10.1f, 11.11f, 12.12f,
                    13.13f, 14.14f, 15.15f, 16.16f,
                    17.17f, 18.18f, 19.19f, 20.2f,
                    21.21f, 22.22f, 23.23f, 24.24f};
            final int WIDTH = 6;
            RenderScript RS = RenderScript.create(mContext.get());
            ScriptC_single_source script = new ScriptC_single_source(RS);
            Allocation inputAllocation = Allocation.createTyped(RS, new Type.Builder(RS, Element.F32_4(RS)).setX(WIDTH).create());
            inputAllocation.copy1DRangeFrom(0, WIDTH, data); // 直接输入基础类型数组，框架进行封装成float4

            Allocation outputAllocation = Allocation.createTyped(RS, new Type.Builder(RS, Element.F32_2(RS)).setX(WIDTH).create());

            Log.d("Param", "before: " + script.get_param());
            script.set_param(5);
            script.invoke_process(inputAllocation, outputAllocation, 1, 3, 5, -11);
            Log.d("Param", "after: " + script.get_param());

            float[] result = new float[WIDTH * 2];
            outputAllocation.copyTo(result);
            RS.destroy();

            return "single source 结果 " + Arrays.toString(result);
        }
        return null;
    }
}
