#version 400 core

in vec4 clipSpace;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;


void main(void) {

	vec2 ndc = (clipSpace.xy/clipSpace.w) /2 + 0.5;
	vec2 refractCoords = vec2(ndc.x, ndc.y);
	vec2 reflectCoords = vec2(ndc.x, -ndc.y);
	
	vec4 reflectCol = texture(reflectionTexture, reflectCoords);
	vec4 refractCol = texture(refractionTexture, refractCoords);
	

	out_Color = mix(reflectCol, refractCol, 0.5);

}