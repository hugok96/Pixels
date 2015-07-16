#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform vec3 lightColour;

void main(void) {

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1*1.1, 0.2);
	vec3 diffuse = brightness * lightColour;

	out_Colour = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoords);//
	
	//Pixelated FragmentShader
	//vec4 pixelColour = texture(textureSampler, pass_textureCoords);
	//float x = 1.9;//2.675;
	//bool doRound = false;
	//vec4(floor((pixelColour.r*x)+(doRound==true?0.5:0))/x, floor((pixelColour.g*x)+(doRound==true?0.5:0))/x, floor((pixelColour.b*x)+(doRound==true?0.5:0))/x, 1.0);	
}