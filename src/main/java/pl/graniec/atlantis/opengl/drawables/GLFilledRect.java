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
package pl.graniec.atlantis.opengl.drawables;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;

import pl.graniec.atlantis.Graphics;
import pl.graniec.atlantis.Stage;
import pl.graniec.atlantis.drawables.FilledRect;
import pl.graniec.atlantis.effects.Effect;
import pl.graniec.atlantis.opengl.GLGraphics;
import pl.graniec.atlantis.opengl.effects.EffectFrameBuffer;
import pl.graniec.atlantis.opengl.effects.GLEffect;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class GLFilledRect extends FilledRect {

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Drawable#draw(pl.graniec.atlantis.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		final GLGraphics glg = (GLGraphics) g;
		final GL gl = glg.getContext();
		final Effect[] effects = getEffectStack();
		
		preDraw(glg, effects);

		// actual drawing
		
		gl.glBegin(GL.GL_QUADS);
		
		glg.setColor(fillColor.get());
		
		final double x1 = this.x.get();
		final double y1 = this.y.get();
		final double x2 = x1 + width.get();
		final double y2 = y1 + height.get();
		
		gl.glVertex2d(x1, y1);
		gl.glVertex2d(x1, y2);
		gl.glVertex2d(x2, y2);
		gl.glVertex2d(x2, y1);
		
		gl.glEnd();
		
		
		postDraw(glg, effects);
	}
	
	private void preDraw(GLGraphics g, Effect[] effects) {
		if (effects.length > 0) {
			final EffectFrameBuffer efb = EffectFrameBuffer.getInstance();
			final GL gl = g.getContext();
			
			efb.rebuild(gl, g.getWidth(), g.getHeight());
			efb.bind(gl);
			
			// make the framebuffer clean
			g.clear(0);
		}
	}
	
	private void postDraw(GLGraphics g, Effect[] effects) {
		if (effects.length > 0) {
			final EffectFrameBuffer efb = EffectFrameBuffer.getInstance();
			final GL gl = g.getContext();
			
			efb.unbind(gl);
			processEffects(g, effects);
		}
		
	}

	/**
	 * @param effectStack
	 */
	private void processEffects(GLGraphics g, Effect[] effectStack) {
		
		final EffectFrameBuffer efb = EffectFrameBuffer.getInstance();
		final GL gl = g.getContext();
		GLEffect gle;
		
		int i = 0;
		for (Effect e : effectStack) {
			
			gle = (GLEffect) e;
			
			efb.swap();
			
			if (i + 1 < effectStack.length) {
				efb.bind(gl);
				g.clear(0);
			}
			
			final Texture texture = efb.getSourceTexture();
			final TextureCoords coords = efb.getSourceTextureCoords();
			
			gle.load(gl, texture.getTextureObject());
			
			gl.glBegin(GL.GL_QUADS);
			gl.glColor3f(1, 1, 1);
			
			gl.glTexCoord2f(coords.left(), coords.top());
			gl.glVertex2i(0, 0);
			
			gl.glTexCoord2f(coords.left(), coords.bottom());
			gl.glVertex2i(0, Stage.getHeight());
			
			gl.glTexCoord2f(coords.right(), coords.bottom());
			gl.glVertex2i(Stage.getWidth(), Stage.getHeight());
			
			gl.glTexCoord2f(coords.right(), coords.top());
			gl.glVertex2i(Stage.getWidth(), 0);
			
			gl.glEnd();
			
			gle.unload(gl);
			
			if (i + 1 < effectStack.length) {
				efb.unbind(gl);
			}
			
			++i;
		}
	}

}
