#pragma version(1)
#pragma rs java_package_name(com.xjx.renderscriptstudy)
#pragma rs_fp_relaxed

float RS_KERNEL doubleIt(float in, uint32_t x) {
  rsDebug("process", in);
  return in * (float)2;
}

#pragma rs reduce(sumUp) \
  accumulator(addUp)

static void addUp(float *accum, float in1) {
  *accum+=in1;
}