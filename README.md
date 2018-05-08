# RenderScriptStudy
学习RenderScript过程中写的一些例子，包括：
## study.rs
一个mapping，将float数组中每个元素乘2

一个reduce，将内容累加（在Java层会将mapping结果传进来计算）

## mapping.rs
将一个float4数组的每一项，以累加的方式转成一个int数组

## single_source.rs
两个mapping，一个是计算float4的长度，一个是将长度转成平面坐标系中的45度的线段的x、y坐标

一个直接调用函数，在其内部调用两个mapping

最终实现输入二维float4数组，输出二维float2数组

## reduce_struct.rs
基于开发者文档例子改造，原版是查找一维long数组中最大最小值的x坐标。

改造后，输入扩展成自定义结构体数组（一维），输出包含最大最小值的x和y坐标。

因为没找到以二维或三维填充自定义结构体Allocation的方式（只有create，没法copy或set，copy类型不对，set只对x维度起作用），实际输出的y坐标是没用的。

## 疑问
- 使用rsSendToClientBlocking从运行时往Android框架层回传时，只有数组的第一个元素上层能收到，其余都是0
- int指针双向都能设置及获取数据，但是自定义结构体的指针，在rs中修改后，上层却无法获取到最新值，变得跟普通全局变量似的