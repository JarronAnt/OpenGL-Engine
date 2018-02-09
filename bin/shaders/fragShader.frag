#version 400 core

in vec2 pass_tex_coords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCamVec;

out vec4 colOut; 

uniform sampler2D textureSampler; 
uniform vec3 lightCol;
uniform float shineDamper;
uniform float reflectivity;


void main(void)
{

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVec = normalize(toLightVector);

	
	float nDot1 = dot(unitNormal, unitLightVec);
	float brightness = max(nDot1, 0.2);
	vec3 diffuse = brightness * lightCol;
	
	vec3 unitCamVec = normalize(toCamVec);
	vec3 lightDirection = -unitLightVec;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	
	float specularFactor = dot(reflectedLightDirection, unitCamVec);
	specularFactor = max(specularFactor,0.0);
	float dampFactor = pow(specularFactor, shineDamper);
	vec3 finalSpec = dampFactor * reflectivity * lightCol;

	colOut = vec4(diffuse,1.0) * texture(textureSampler, pass_tex_coords) + vec4(finalSpec,1.0); 
}