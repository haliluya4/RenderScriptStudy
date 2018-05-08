package com.xjx.renderscriptstudy.tasks;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.Int4;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;

import java.util.Arrays;


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

            RS.setMessageHandler(new RenderScript.RSMessageHandler() {
                @Override
                public void run() {
                    // 只能收到数组的0号元素？？？
                    Log.d("Send back", "回传 id=" + mID
                            + " len=" + mLength + " data=" + Arrays.toString(mData));
                }
            });

            ScriptField_Input inputStruct = new ScriptField_Input(RS, data.length);
            for (int i = 0; i < data.length; i++) {
                inputStruct.set_val(i, data[i], false);
            }
            inputStruct.copyAll();  // 统一更新，也可以上面更新，效率没差，里面也是for循环
            Allocation inputAllocation = inputStruct.getAllocation();

            ScriptField_Output output = new ScriptField_Output(RS, 1);
            ScriptField_Output.Item outputItem = new ScriptField_Output.Item();
            outputItem.sum = -1;
            output.set(outputItem, 0, true);    // 如果要用的话，每个元素都要分配空间
            script.bind_outputPoint(output);    // 绑定结构体指针内存

            script.bind_outputSum(Allocation.createTyped(RS,
                    new Type.Builder(RS, Element.I32(RS)).setX(1).create())); // 绑定单个整数指针内存

            Int4 result = script.reduce_findMinAndMax(inputAllocation).get();
            // 获取单个整数的最新值，这个可以获取到rs里设置的
            int[] sumArray = new int[1];
            script.get_outputSum().copyTo(sumArray);

            // 这个却获取不到rs中设置的
            long anotherSum = script.get_outputPoint().get_sum(0);
            RS.destroy();

            return "reduce结果 最小值=" + data[result.x] + " 最大值=" + data[result.z] + " 两个的和=" + anotherSum + " " + sumArray[0];
        }
        return null;
    }
}
