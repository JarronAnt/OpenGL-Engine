#version 400 core

in vec3 pos; 

out vec3 col; 

void main(void)
{
	gl_Position = vec4(pos.xyz,1.0);
	col = vec3(pos.x+0.5, 1.0, pos.y+0.5);
}
