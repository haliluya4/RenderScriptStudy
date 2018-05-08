#pragma version(1)
#pragma rs java_package_name(com.xjx.renderscriptstudy.tasks)
#pragma rs_fp_relaxed

#define LONG_MAX (long)((1UL << 63) - 1) // long最大值，63个1
#define LONG_MIN (long)(1UL << 63) // 最高位为1，补码最小值

#pragma rs reduce(findMinAndMax) \
  initializer(fMMInit) accumulator(fMMAccumulator) \
  combiner(fMMCombiner) outconverter(fMMOutConverter)

typedef struct Input { // Input作为对外暴露的结构名
  long val;
} Input_inner; // 自定义输入结构，带inner后缀作为内部使用结构名

typedef struct Output {
  long sum;
} Output_inner; // 自定义输出结构，用于指针例子
Output_inner *outputPoint;

int* outputSum;

typedef struct {
  long val;
  int idx;     // -1 indicates INITVAL
  int idy;     // -1 indicates INITVAL
} IndexedVal;

typedef struct {
  IndexedVal min, max;
} MinAndMax; // 累加数据项结构

// 初始化函数，用于配置累加数据项初始值
static void fMMInit(MinAndMax *accum) {
  accum->min.val = LONG_MAX;
  accum->min.idx = -1;
  accum->min.idy = -1;
  accum->max.val = LONG_MIN;
  accum->max.idx = -1;
  accum->max.idy = -1;
  rsDebug("LONG_MAX", LONG_MAX);
  rsDebug("LONG_MIN", LONG_MIN);
}

// 累加函数，in是输入数组中的，x、y是下标索引
static void fMMAccumulator(MinAndMax *accum, Input_inner in, int x, int y) {
  IndexedVal me;
  me.val = in.val;
  me.idx = x;
  me.idy = y; // 自定义结构没找到像基础类型那样的2D、3D输入数据填充方式，y固定为0

  if (me.val <= accum->min.val)
    accum->min = me;
  if (me.val >= accum->max.val)
    accum->max = me;
}

static void fMMCombiner(MinAndMax *accum,
                        const MinAndMax *val) {
  if ((accum->min.idx < 0) || (val->min.val < accum->min.val)) // 只判断idx足矣，因为idy是一起赋值的
    accum->min = val->min;
  if ((accum->max.idx < 0) || (val->max.val > accum->max.val))
    accum->max = val->max;
}

static void fMMOutConverter(int4 *result,
                            const MinAndMax *val) {
  result->s0 = val->min.idx;
  result->s1 = val->min.idy;
  result->s2 = val->max.idx;
  result->s3 = val->max.idy;

  rsDebug("sum before", outputPoint[0].sum);
  outputPoint[0].sum = val->min.val + val->max.val; // 写入内存，这样Java层无法读到结果？？？
  *outputSum = val->min.val + val->max.val; // 写入内存，这样Java层能读到结果
  rsDebug("sum after", outputPoint[0].sum);

  int data[2];
  data[0] = 4;
  data[1] = 5; // 无论数组多大，只有0号元素能在上层收到？？？
  rsSendToClientBlocking(6, data, 2);
}