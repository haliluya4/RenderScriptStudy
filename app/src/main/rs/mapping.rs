#pragma version(1)
#pragma rs java_package_name(com.xjx.renderscriptstudy)
#pragma rs_fp_relaxed

int RS_KERNEL floats2int(float4 in, uint32_t x, uint32_t y) {
    float res = in.s0 + in.s1 + in.s2 + in.s3;
    return (int)res;
}