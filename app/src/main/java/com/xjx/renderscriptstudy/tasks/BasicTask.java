package com.xjx.renderscriptstudy.tasks;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;

import com.xjx.renderscriptstudy.ScriptC_study;

import java.util.Arrays;

/**
 * 将浮点数数组每个元素乘2，然后累加得到结果
 */

public class BasicTask extends ShowResultTask {
    public BasicTask(Context context) {
        super(context);
    }

    @Override
    protected String doInBackground(Object... objects) {
        if (mContext.get() != null) {
            float[] data = {1, 31, 5152, 1231, 123, 123.1f, 1, -14, 123.8912213231f};
            RenderScript RS = RenderScript.create(mContext.get());
            ScriptC_study script = new ScriptC_study(RS);
            Allocation inputAllocation = Allocation.createTyped(RS, new Type.Builder(RS, Element.F32(RS)).setX(data.length).create());
            inputAllocation.copy1DRangeFrom(0, data.length, data);

            Allocation outputAllocation = Allocation.createTyped(RS, inputAllocation.getType());
            script.forEach_doubleIt(inputAllocation, outputAllocation);
            float[] result = new float[data.length];
            outputAllocation.copyTo(result);
            Log.d("xx", "mapping 结果 " + Arrays.toString(result));
            float total = script.reduce_sumUp(outputAllocation).get();
            RS.destroy();
            return Float.toString(total);
        }
        return null;
    }
}
