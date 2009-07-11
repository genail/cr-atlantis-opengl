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

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;

import pl.graniec.atlantis.opengl.FrameBuffer;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class EffectFrameBuffer {

	/** Static instance of this class */
	private static final EffectFrameBuffer instance = new EffectFrameBuffer();
	
	public static EffectFrameBuffer getInstance() {
		return instance;
	}
	
	/** Two framebuffers for shader rendering */
	private FrameBuffer source, target;
	
	/** Is target binded? */
	private boolean targetBinded;
	
	private EffectFrameBuffer() {
	}
	
	public void bind(GL gl) {
		target.bind(gl);
		targetBinded = true;
	}
	
	public void dispose() {
		// FIXME: Make a call from proper place
		if (source != null) {
			source.dispose();
			target.dispose();
		}
	}
	
	public Texture getSourceTexture() {
		return source.getTexture();
	}
	
	public TextureCoords getSourceTextureCoords() {
		return source.getTextureCoords();
	}
	
	public void rebuild(GL gl, int width, int height) {
		
		if (source != null && (source.getWidth() != width || source.getHeight() != height)) {
			source.dispose();
			target.dispose();
			
			source = null;
			target = null;
		}
		
		if (source == null) {
			source = new FrameBuffer(gl, width, height);
			target = new FrameBuffer(gl, width, height);
		}
	}
	
	public void swap() {
		if (targetBinded) {
			throw new RuntimeException("target is still binded");
		}
		
		final FrameBuffer temp = source;
		source = target;
		target = temp;
	}
	
	public void unbind(GL gl) {
		target.unbind(gl);
		targetBinded = false;
	}
}
