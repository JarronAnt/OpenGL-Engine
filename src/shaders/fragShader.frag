#version 400 core

in vec2 pass_tex_coords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 colOut; 

uniform sampler2D textureSampler; 
uniform vec3 lightCol;



void main(void)
{

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVec = normalize(toLightVector);
	
	float nDot1 = dot(unitNormal, unitLightVec);
	float brightness = max(nDot1, 0.0);
	vec3 diffuse = brightness * lightCol;

	colOut = vec4(diffuse,1.0) * texture(textureSampler, pass_tex_coords); 
}