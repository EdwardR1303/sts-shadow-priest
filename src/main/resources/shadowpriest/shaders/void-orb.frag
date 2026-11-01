#ifdef GL_ES
precision mediump float;
#endif
uniform float u_time;
uniform float u_form;
uniform float u_shadow;
varying vec4 v_color;
varying vec2 v_texCoords;
void main() {
    vec2 p = v_texCoords * 2.0 - 1.0;
    float r = length(p);
    if (u_shadow > 0.5) {
        gl_FragColor = vec4(0.03, 0.01, 0.07, (1.0-smoothstep(0.15,1.0,r))*v_color.a);
        return;
    }
    float a = atan(p.y,p.x);
    float wave = sin(a*5.0-u_time*(1.7+u_form))*0.018;
    float ring = exp(-abs(r-0.62-wave)*42.0);
    float glow = exp(-abs(r-0.62)*8.0);
    float core = 1.0-smoothstep(0.56,0.64,r);
    float spiral = pow(max(0.0,sin(a*3.0+r*16.0-u_time*2.0)),10.0)*core*r;
    vec3 hue = mix(vec3(0.23,0.28,0.94),vec3(0.72,0.16,1.0),u_form);
    vec3 color = vec3(0.012,0.005,0.04)*core + hue*(ring*1.4+glow*0.32+spiral*0.22);
    float alpha = max(core, min(1.0,ring+glow*0.65)) * (1.0-smoothstep(0.84,1.0,r));
    gl_FragColor = vec4(color,alpha)*v_color;
}
