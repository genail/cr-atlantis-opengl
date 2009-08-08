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

import java.io.IOException;
import java.util.logging.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import pl.graniec.atlantis.Build;
import pl.graniec.atlantis.EMessage;
import pl.graniec.atlantis.Graphics;
import pl.graniec.atlantis.Utils;
import pl.graniec.atlantis.drawables.ImageSprite;
import pl.graniec.atlantis.effects.Effect;
import pl.graniec.atlantis.opengl.GLGraphics;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class GLImageSprite extends ImageSprite {
	
	private static final Logger logger = Logger.getLogger(GLImageSprite.class.getName());

	/** Texture used to keep the image */
	private Texture texture;
	
	public GLImageSprite(String path) {
		
		try {
			texture = TextureIO.newTexture(Utils.pathToUrl(path), true, "png");
			
			width.set(texture.getWidth());
			height.set(texture.getHeight());
			
		} catch (GLException e) {
			if (Build.DEBUG) {
				logger.severe(EMessage.prepare("cannot load image", e));
			}
		} catch (IOException e) {
			if (Build.DEBUG) {
				logger.severe(EMessage.prepare("cannot load image", e));
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Drawable#draw(pl.graniec.atlantis.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		final GLGraphics glg = (GLGraphics) g;
		final GL gl = glg.getContext();
		final Effect[] effects = getEffectStack();
		final TextureCoords coords = texture.getImageTexCoords();
		
		GLDrawHelper.preDraw(glg, effects);
		
		final double x1 = this.x.get();
		final double y1 = this.y.get();
		final double x2 = x1 + width.get();
		final double y2 = y1 + height.get();

		// actual drawing
		
		texture.enable();
		texture.bind();
		
		
		glg.setColor(0x00FFFFFF + (alpha.get() << 24));
		
		gl.glBegin(GL.GL_QUADS);
		
		gl.glTexCoord2f(coords.left(), coords.top());
		gl.glVertex2d(x1, y1);
		
		gl.glTexCoord2f(coords.left(), coords.bottom());
		gl.glVertex2d(x1, y2);
		
		gl.glTexCoord2f(coords.right(), coords.bottom());
		gl.glVertex2d(x2, y2);
		
		gl.glTexCoord2f(coords.right(), coords.top());
		gl.glVertex2d(x2, y1);
		
		gl.glEnd();
		
		texture.disable();
		
		GLDrawHelper.postDraw(glg, effects);
	}

}
