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
package pl.graniec.atlantis.opengl;

import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class FrameBuffer {
	
	/** Standard texture coordinates */
	private static final TextureCoords TEXTURE_COORDS = new TextureCoords(0, 0, 1, 1);

	/** OpenGL texture sizes */
	private final static int[] TEX_SIZES = {
		2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4092
	};
	
	/** Frame buffer OpenGL object handle */
	private final int handle;
	
	/** Texture that stores frame buffer */
	private final Texture texture;

	/** Logical texture width */
	private final int width;
	/** Logical texture height */
	private final int height;
	
	/**
	 * 
	 */
	public FrameBuffer(GL gl, int width, int height) {
		
		this.width = width;
		this.height = height;
		
		// calculate texture size
		int[] wh = toOpenGLDimension(width, height);
		width = wh[0];
		height = wh[1];
		
		// create a framebuffer object
		final IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenFramebuffersEXT(1, intBuffer);
		handle = intBuffer.get();
		
		// create texture
		texture = TextureIO.newTexture(GL.GL_TEXTURE_2D);
    	texture.bind();
    	texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 4,  width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, null);
		
    	bind(gl);
    	
    	// attach texture
    	gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT, GL.GL_COLOR_ATTACHMENT0_EXT, GL.GL_TEXTURE_2D, texture.getTextureObject(), 0);
    	
    	unbind(gl);
	}
	
	public void bind(GL gl) {
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, handle);
	}
	
	public void dispose() {
		texture.dispose();
		GLContext.getCurrent().getGL().glDeleteFramebuffersEXT(1, IntBuffer.wrap(new int[] { handle }));
	}
	
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	
	public TextureCoords getTextureCoords() {
		return TEXTURE_COORDS;
	}
	
	private int[] toOpenGLDimension(int w, int h) {
		
		for (int s : TEX_SIZES) {
			if (w <= s) {
				w = s;
				break;
			}
		}
		
		for (int s : TEX_SIZES) {
			if (h <= s) {
				h = s;
				break;
			}
		}
		
		return new int[] {w, h};
	}
	
	public void unbind(GL gl) {
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0);
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
}
