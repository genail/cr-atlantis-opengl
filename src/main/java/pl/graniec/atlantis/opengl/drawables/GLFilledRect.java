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

import pl.graniec.atlantis.Graphics;
import pl.graniec.atlantis.drawables.FilledRect;
import pl.graniec.atlantis.opengl.GLGraphics;

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
		final GL func = glg.getContext();
		
		func.glBegin(GL.GL_QUADS);
		
		glg.setColor(fillColor.get());
		
		final double x1 = this.x.get();
		final double y1 = this.y.get();
		final double x2 = x1 + width.get();
		final double y2 = y1 + height.get();
		
		func.glVertex2d(x1, y1);
		func.glVertex2d(x1, y2);
		func.glVertex2d(x2, y2);
		func.glVertex2d(x2, y1);
		
		func.glEnd();
	}

}
