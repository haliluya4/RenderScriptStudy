package com.xjx.renderscriptstudy.tasks;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Int4;
import android.renderscript.RenderScript;

import com.xjx.renderscriptstudy.ScriptC_reduce_struct;
import com.xjx.renderscriptstudy.ScriptField_Input;

/**
 * Google文档里的求最大最小值位置的扩展，输入由long一维数组改成自定义结构二维数组，返回值同时扩展y坐标
 */
public class ReduceStructTask extends ShowResultTask {
    public ReduceStructTask(Context context) {
        super(context);
    }

    @Override
    protected String doInBackground(Object... objects) {
        if (mContext.get() != null) {
            long[] data = {
                    1321L, -123123L, 1331112121L,
                    8191L, 0, -1123122L};
            RenderScript RS = RenderScript.create(mContext.get());
            ScriptC_reduce_struct script = new ScriptC_reduce_struct(RS);
            ScriptField_Input inputStruct = new ScriptField_Input(RS, data.length);
            for (int i = 0; i < data.length; i++) {
                inputStruct.set_val(i, data[i], false);
            }
            inputStruct.copyAll();  // 统一更新，也可以上面更新，效率没差，里面也是for循环
            Allocation inputAllocation = inputStruct.getAllocation();

            Int4 result = script.reduce_findMinAndMax(inputAllocation).get();

            RS.destroy();

            return "reduce结果 最小值=" + data[result.x] + " 最大值=" + data[result.z];
        }
        return null;
    }
}
