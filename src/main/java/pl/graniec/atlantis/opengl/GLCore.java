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

import pl.graniec.atlantis.Core;
import pl.graniec.atlantis.Window;
import pl.graniec.atlantis.drawables.FilledRect;
import pl.graniec.atlantis.effects.ColorInvert;
import pl.graniec.atlantis.opengl.drawables.GLFilledRect;
import pl.graniec.atlantis.opengl.effects.GLColorInvert;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class GLCore extends Core {

	/**
	 * Creates a OpenGL Core implementation.
	 */
	public GLCore() {
		if (Core.getCurrent() == null) {
			makeCurrent();
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Core#newColorInvert()
	 */
	@Override
	public ColorInvert newColorInvert() {
		return new GLColorInvert();
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Core#newFilledRect()
	 */
	@Override
	public FilledRect newFilledRect() {
		return new GLFilledRect();
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Core#newWindow()
	 */
	@Override
	public Window newWindow() {
		return new GLWindow();
	}
}
