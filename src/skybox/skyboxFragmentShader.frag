#version 400

in vec3 textureCoords;
out vec4 out_Color;

uniform samplerCube cubeMap1;
uniform samplerCube cubeMap2;
uniform float blendFactor;

uniform vec3 fogColour; 

const float lowerLimit = 0.0;
const float upperLimit = 30;

void main(void){
	vec4 tex1 = texture(cubeMap1, textureCoords);
	vec4 tex2 = texture(cubeMap2, textureCoords);
	
    vec4 finalColour = mix(tex1, tex2, blendFactor);
    
    float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);
    
    out_Color = mix(vec4(fogColour,1.0), finalColour, factor);
}