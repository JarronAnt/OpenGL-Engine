#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D backgroundTex;
uniform sampler2D rTex;
uniform sampler2D gTex;
uniform sampler2D bTex;
uniform sampler2D blendMap;


uniform vec3 lightColour[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 attenuation[4];


void main(void){

	vec4 blendMapCol = texture(blendMap, pass_textureCoordinates);
	float backTexAmount = 1 - (blendMapCol.r + blendMapCol.g + blendMapCol.b);
	vec2 tiledCoords = pass_textureCoordinates * 40.0;
	
	vec4 bgTexCol = texture(backgroundTex, tiledCoords) * backTexAmount;
	vec4 rTexCol = texture(rTex, tiledCoords) * blendMapCol.r;	
	vec4 gTexCol = texture(gTex, tiledCoords) * blendMapCol.g;
	vec4 bTexCol = texture(bTex, tiledCoords) * blendMapCol.b;
	
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	
	vec4 totalCol = bgTexCol + rTexCol + gTexCol + bTexCol;
	vec3 unitNormal = normalize(surfaceNormal);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for(int i = 0; i < 4; i++){
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDotl = dot(unitNormal,unitLightVector);
		float brightness = max(nDotl,0.2);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
		float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		totalDiffuse = totalDiffuse  + (brightness * lightColour[i])/attFactor;
		totalSpecular + totalSpecular +  (dampedFactor * reflectivity * lightColour[i])/attFactor;
	}
	
		totalDiffuse = max(totalDiffuse, 0.2);

	out_Color =  vec4(totalDiffuse,1.0) * totalCol + vec4(totalSpecular,1.0);

}