#version 400 core

in vec2 pass_tex_coords;

out vec4 colOut; 

uniform sampler2D textureSampler; 

void main(void)
{
	colOut = texture(textureSampler, pass_tex_coords); 
}