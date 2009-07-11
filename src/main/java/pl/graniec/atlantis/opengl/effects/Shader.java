/*
 * Copyright (c) 2009, GLShaders
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the name of the Flock nor the names of its contributors may be
 *      used to endorse or promote products derived from this software without
 *      specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package pl.graniec.atlantis.opengl.effects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class Shader {
	
	private static final String DEFAULT_SEARCH_PATH = "/shaders";
	
	final int shaderProgram;
	
	/**
	 * 
	 */
	public Shader(GL context, String name) {
		
		final String[] vertexFragmentShader = getShaders(name);
		boolean hasVertexShader = vertexFragmentShader[0] != null;
		
		int v = 0, f = 0;
		
		if (hasVertexShader) {
			
			v = context.glCreateShader(GL.GL_VERTEX_SHADER);
			
			context.glShaderSource(v, 1, new String[] { vertexFragmentShader[0] }, (int[]) null, 0);
	    	context.glCompileShader(v);
		}
		
		f = context.glCreateShader(GL.GL_FRAGMENT_SHADER);
		
		context.glShaderSource(f, 1, new String[] { vertexFragmentShader[1] }, (int[]) null, 0);
    	context.glCompileShader(f);
    
    	
    	shaderProgram = context.glCreateProgram();
    	
    	if (hasVertexShader) {
    		context.glAttachShader(shaderProgram, v);
    	}
    	
    	context.glAttachShader(shaderProgram, f);
    	context.glLinkProgram(shaderProgram);
    	context.glValidateProgram(shaderProgram);
    	
    	System.out.println("shader " + name + ": " + context.glGetError());
	}
	
	private String[] getShaders(String name) {
		
		String fragmentSource = "", vertexSource = "";
		
		InputStream fragmentStream = null;
		InputStream vertexStream = null;
		
		try {
			 fragmentStream = getClass().getResourceAsStream(DEFAULT_SEARCH_PATH + "/" + name + ".frag");
			
			if (fragmentStream == null) {
				throw new RuntimeException("shader not found: " + name);
			}
			
			vertexStream = getClass().getResourceAsStream(DEFAULT_SEARCH_PATH + "/" + name + ".vert");
			
	    	String line;
	    	
	    	final BufferedReader fragmentReader = new BufferedReader(new InputStreamReader(fragmentStream));
	    	while ((line = fragmentReader.readLine()) != null) {
	    	  fragmentSource += line + "\n";
	    	}
	    	
	    	if (vertexStream != null) {
	    		final BufferedReader vertexReader = new BufferedReader(new InputStreamReader(fragmentStream));
		    	while ((line = vertexReader.readLine()) != null) {
		    	  vertexSource += line + "\n";
		    	}
	    	}
		} catch (IOException e) {
			
		} finally {
			if (fragmentStream != null) {
				try {
					fragmentStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (vertexStream != null) {
				try {
					vertexStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		final String[] result = new String[2];
		
		if (!vertexSource.isEmpty()) {
			result[0] = vertexSource;
		}
		
		result[1] = fragmentSource;
		
		return result;
	}
	
	public void unuseProgram() {
		getGL().glUseProgram(0);
	}
	
	public void useProgram() {
		getGL().glUseProgram(shaderProgram);
	}
	
	public void setUniformInt(String name, int value) {
		final GL gl  = getGL();
		gl.glUniform1i(gl.glGetUniformLocation(shaderProgram, name), value);
	}
	
	private GL getGL() {
		return GLContext.getCurrent().getGL();
	}
}
