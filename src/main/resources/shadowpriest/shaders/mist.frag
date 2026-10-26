#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D u_texture;
uniform float u_time;
uniform float u_form;
varying vec4 v_color;
varying vec2 v_texCoords;
float hash(vec2 p) { return fract(sin(dot(p, vec2(127.1,311.7))) * 43758.5453); }
float noise(vec2 p) {
    vec2 i=floor(p), f=fract(p); f=f*f*(3.0-2.0*f);
    return mix(mix(hash(i),hash(i+vec2(1.0,0.0)),f.x),
               mix(hash(i+vec2(0.0,1.0)),hash(i+vec2(1.0)),f.x),f.y);
}
float cloud(vec2 p) {
    float n=0.0, a=0.55;
    for(int i=0;i<4;i++) { n+=a*noise(p); p=p*2.03+vec2(5.2,1.3); a*=0.5; }
    return n;
}
void main() {
    vec2 p=v_texCoords*2.0-1.0;
    float t=u_time*0.35;
    vec2 flow=vec2(cloud(p*2.0+vec2(t,-t)),cloud(p*2.0+vec2(-t,t+4.0)))-0.5;
    float n=cloud(p*4.0+flow*2.8+vec2(t,-t*1.8));
    float radius=length(p*vec2(1.05,0.95)+flow*0.24);
    float envelope=1.0-smoothstep(0.3,0.96,radius);
    float alpha=envelope*smoothstep(0.18,0.64,n)*0.95;
    float light=smoothstep(0.38,0.8,n);
    vec3 color=mix(vec3(0.025,0.008,0.065),vec3(0.44,0.13,0.72),light);
    color+=vec3(0.15,0.025,0.22)*u_form*light;
    // Sample the white texture to keep the ordinary SpriteBatch sampler contract.
    gl_FragColor=vec4(color,alpha)*v_color*texture2D(u_texture,v_texCoords);
}
