package com.xjx.renderscriptstudy.tasks;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;

import com.xjx.renderscriptstudy.ScriptC_mapping;

import java.util.Arrays;

/**
 * Mapping例子，输入float4的二维数组，输出int二维数组。
 * 将每个float4累加后转成int
 */
public class MappingTask extends ShowResultTask {
    public MappingTask(Context context) {
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
            final int WIDTH = 3, HEIGHT = 2;
            RenderScript RS = RenderScript.create(mContext.get());
            ScriptC_mapping script = new ScriptC_mapping(RS);
            Allocation inputAllocation = Allocation.createTyped(RS, new Type.Builder(RS, Element.F32_4(RS)).setX(WIDTH).setY(HEIGHT).create());
            inputAllocation.copy2DRangeFrom(0, 0, WIDTH, HEIGHT, data); // 直接输入基础类型数组，框架进行封装成float4

            Allocation outputAllocation = Allocation.createTyped(RS, new Type.Builder(RS, Element.I32(RS)).setX(WIDTH).setY(HEIGHT).create());

            script.forEach_floats2int(inputAllocation, outputAllocation);

            int[] result = new int[data.length / 4];
            outputAllocation.copyTo(result);
            RS.destroy();

            return "mapping 结果 " + Arrays.toString(result);
        }
        return null;
    }
}
