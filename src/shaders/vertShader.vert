#version 400 core

in vec3 pos; 
in vec2 texCoords;

out vec2 pass_tex_coords;

uniform mat4 transformMatrix; 
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void)
{
	gl_Position =  projectionMatrix * viewMatrix *  transformMatrix * vec4(pos.xyz,1.0);
	pass_tex_coords = texCoords; 
}
