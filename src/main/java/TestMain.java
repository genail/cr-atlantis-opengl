import java.awt.Color;

import pl.graniec.atlantis.Core;
import pl.graniec.atlantis.Scene;
import pl.graniec.atlantis.Stage;
import pl.graniec.atlantis.Window;
import pl.graniec.atlantis.animation.Easing;
import pl.graniec.atlantis.drawables.FilledRect;
import pl.graniec.atlantis.drawables.ImageSprite;
import pl.graniec.atlantis.drawables.Line;
import pl.graniec.atlantis.effects.HorizontalBlur;
import pl.graniec.atlantis.effects.VerticalBlur;
import pl.graniec.atlantis.opengl.GLCore;

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

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class TestMain {
	
	private static class MyScene extends Scene {

		FilledRect rect;
		
		/* (non-Javadoc)
		 * @see pl.graniec.atlantis.Scene#load()
		 */
		@Override
		public void load() {
			
			final Core core = Core.getCurrent();
			
			final FilledRect fill = core.newFilledRect();
			fill.setFillColor(0x10000000);
			add(fill);
			
			rect = core.newFilledRect();
			rect.setFillColor(0xCCFFFFFF);
			rect.setGeometry(10, 10, 200, 100);
			
			rect.x.animate(10, 400, 5000, Easing.REGULAR_IN_OUT, 2000);
			rect.y.animate(10, 400, 5000, Easing.REGULAR_IN_OUT, 2000);
//			rect.fillColor.animate(0xFFFF0000, 0xFF0000FF, 5000);
			
			rect.addEffect(Core.getCurrent().newColorInvert());
			rect.addEffect(Core.getCurrent().newColorDesaturate());
			
			
			
			ImageSprite sprite = core.newImageSprite("/home/chudy/Desktop/a.png");
			
//			sprite.addEffect(core.newColorDesaturate());
//			sprite.addEffect(core.newColorInvert());
			final HorizontalBlur hblur = core.newHorizontalBlur();
			hblur.radius.animate(30, 0, 2000);
			
			final VerticalBlur vblur = core.newVerticalBlur();
			vblur.radius.animate(30, 0, 2000);
			
			sprite.addEffect(hblur);
			sprite.addEffect(vblur);
			
			add(sprite);
			
			add(rect);
			
			final Line line = core.newLine();
			line.setPosition(10, 10, 200, 100);
			line.setColor(0xFF0000FF, 0xFFFF0000);
			
			add(line);
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Core core = new GLCore();
		final Window window = core.newWindow();
		window.setTitle("Test");
		window.show();
		
		final Scene scene = new MyScene();
		Stage.setScene(scene);
	}

}
