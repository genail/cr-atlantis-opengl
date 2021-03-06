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
package pl.graniec.atlantis.opengl.effects;

import javax.media.opengl.GL;

import pl.graniec.atlantis.Stage;
import pl.graniec.atlantis.opengl.effects.shaders.Shader;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class GLEffectBasic implements GLEffect {

	/** Shader of this effect */
	Shader shader;
	
	public GLEffectBasic(Class<? extends Shader> shaderClass) {
//		shader = new Shader(GLContext.getCurrent().getGL(), shaderName);
		shader = ShaderCache.getInstance().getShader(shaderClass);
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.opengl.effects.GLEffect#load(javax.media.opengl.GL, int)
	 */
	public void load(GL gl) {
		shader.setUniformInt("inputTexture", 0);
		shader.useProgram();
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.opengl.effects.GLEffect#unload(javax.media.opengl.GL)
	 */
	public void unload(GL gl) {
		shader.unuseProgram();
	}

}
