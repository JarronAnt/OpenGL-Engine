#version 400 core

in vec3 pos; 
in vec2 texCoords;
in vec3 normal;


out vec2 pass_tex_coords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCamVec; 


uniform mat4 transformMatrix; 
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;


void main(void)
{

	vec4 worldPos =   transformMatrix * vec4(pos.xyz,1.0);
	gl_Position =  projectionMatrix * viewMatrix * worldPos;
	pass_tex_coords = texCoords; 
	
	surfaceNormal = (transformMatrix * vec4(normal,0.0)).xyz;
	toLightVector = lightPos - worldPos.xyz;
	toCamVec = (inverse(viewMatrix) * vec4(0,0,0,1.0)).xyz - worldPos.xyz;
}
