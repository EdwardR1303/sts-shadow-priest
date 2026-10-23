#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D u_texture;
uniform float u_form;
uniform float u_silhouette;
varying vec4 v_color;
varying vec2 v_texCoords;
void main() {
    vec4 tex = texture2D(u_texture, v_texCoords);
    vec3 shadow = tex.rgb * vec3(0.67, 0.58, 0.88);
    vec3 empowered = tex.rgb * vec3(1.05, 0.72, 1.35) + vec3(0.12, 0.02, 0.20);
    vec3 body = mix(shadow, empowered, u_form);
    gl_FragColor = vec4(mix(body, vec3(1.0), u_silhouette), tex.a) * v_color;
}
