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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import pl.graniec.atlantis.DisplayMode;
import pl.graniec.atlantis.Stage;
import pl.graniec.atlantis.Window;

/**
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
public class GLWindow implements Window {
	
	private class GLFrame extends Frame {

		private static final long serialVersionUID = -4320317195175577637L;
		
	}
	
	private static final long serialVersionUID = 3946413970058762607L;

	private final DisplayMode DEFAULT_DISPLAY_MODE = new DisplayMode(640, 480, false);
	
	/** OpenGL Canvas */
	private final GLCanvas canvas = new GLCanvas();
	
	/** AWT Frame */
	private final GLFrame frame = new GLFrame();
	
	/** GLU */
	private final GLU glu = new GLU();
	
	public GLWindow() {
		frame.add(canvas);
		setDisplayMode(DEFAULT_DISPLAY_MODE);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		canvas.addGLEventListener(new GLEventListener() {
			
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				
				GL func = drawable.getGL();
				
				func.glMatrixMode(GL.GL_PROJECTION);
				func.glLoadIdentity();
				glu.gluOrtho2D(0, canvas.getWidth(), canvas.getHeight(), 0);
				func.glMatrixMode(GL.GL_MODELVIEW);
			}
			
			public void init(GLAutoDrawable drawable) {
				
				GL func = drawable.getGL();
				
				func.glMatrixMode(GL.GL_PROJECTION);
				func.glLoadIdentity();
				glu.gluOrtho2D(0, canvas.getWidth(), canvas.getHeight(), 0);
				func.glMatrixMode(GL.GL_MODELVIEW);

				
				func.glShadeModel(GL.GL_SMOOTH);
				func.glClearColor(0.0f, 0.5f, 0.0f, 1.0f);
				func.glClearDepth(1.0f);
				
				func.glEnable(GL.GL_BLEND);
				func.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			}
			
			public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
			}
			
			public void display(GLAutoDrawable drawable) {
				
				final GL func = drawable.getGL();
				
				func.glClear(GL.GL_COLOR_BUFFER_BIT);
				func.glClear(GL.GL_DEPTH_BUFFER_BIT);
				
				func.glLoadIdentity();
				
				Stage.repaintStage(new GLGraphics(func));

			}
		});
		
		canvas.requestFocus();
	}
	
	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Window#getAvailableDisplayModes()
	 */
	public DisplayMode[] getAvailableDisplayModes() {
		return new DisplayMode[] { DEFAULT_DISPLAY_MODE };
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Window#setDisplayMode(pl.graniec.atlantis.DisplayMode)
	 */
	public void setDisplayMode(DisplayMode mode) {
		canvas.setSize(new Dimension(mode.getWidth(), mode.getHeight()));
		frame.pack();
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Window#show()
	 */
	public void show() {
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Window#getTitle()
	 */
	public String getTitle() {
		return frame.getTitle();
	}

	/* (non-Javadoc)
	 * @see pl.graniec.atlantis.Window#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}

}
