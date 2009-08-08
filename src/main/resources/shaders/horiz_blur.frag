/**
 * Copyright (c) 2009, Coral Reef Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  * Neither the name of the Coral Reef Project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Horizontal blur shader
 * Author: Piotr Korzuszek <piotr.korzuszek@gmail.com>
 */

// texture id that should be blurred
uniform sampler2D inputTexture;
// with of texture in pixels
uniform int textureWidth;
// height of texture in pixels
uniform int textureHeight;
// blur radius
uniform int radius;

vec2 toTextureCoords(in vec2 texCoords)
{
        return vec2(texCoords.x * float(textureWidth), texCoords.y * float(textureHeight));
}

vec2 toOpenGLCoords(in vec2 texCoords)
{
        return vec2(texCoords.x / float(textureWidth), texCoords.y / float(textureHeight));
}

void main()
{
        
        if (radius == 0) {
        	gl_FragColor = texture2D(inputTexture, gl_TexCoord[0].st);
        	return;
        }
        
        vec2 screenCoords = toTextureCoords(gl_TexCoord[0].st);
        float maxLen = length(vec2(radius/2, radius/2));
        float sum = 0.0;
        
        vec4 color = vec4(0.0, 0.0, 0.0, 0.0);
        vec4 pickColor;
        //int opaqueCount = 0;
        
        for (int x = -radius; x <= radius; ++x) {
        
        	if (x < 0 || x >= textureWidth) {
        		continue;
        	}
        
                float weight = float(radius) - length(vec2(x, 0)) / 2.0;
                
                pickColor = texture2D(inputTexture, toOpenGLCoords(screenCoords + vec2(x, 0)));
                
                color += vec4(pickColor.r * pickColor.a, pickColor.g * pickColor.a, pickColor.b * pickColor.a, pickColor.a) * weight;
                sum += weight; 
        }
        
        if (sum != 0.0) {
        	color /= sum;
        }
        
        //color.a = (float) opaqueCount / (float) (radius * 2 + 1);
        
        gl_FragColor = color;
}
