package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * Projket1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Projket1 implements GLEventListener {
    
    //statyczne pola okre?laj?ce rotacj? wokó? osi X i Y
 
    private static float xrot = 0.0f, yrot = 0.0f;
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Projket1());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        //Obs?uga klawiszy strza?ek
        frame.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                xrot -= 5.0f;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                xrot +=5.0f;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                yrot += 5.0f;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                yrot -=5.0f;
            }
            public void keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
        });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
        
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);
        //wy??czenie wewn?trzych stron prymitywów
        gl.glEnable(GL.GL_CULL_FACE);
        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    public void trojkat(GLAutoDrawable drawable ,float a, float b, float c,float x,float y, float z,float j,float k,float l){
        GL gl = drawable.getGL();
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex3f(a, b,c);
            gl.glVertex3f(x,y,z);
            gl.glVertex3f(j,k,l);
            gl.glEnd();
    }
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuni?cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó? osi Y
        float x,y,kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(7.0f, 7.0f, 7.0f);
        gl.glVertex3f(0.0f,0.0f,1.0f); //?rodek
        for(kat = (float) (2.0f*Math.PI); kat >= 0.0f;kat-=(Math.PI/32.0f))
        {
            x = 1.5f*(float)Math.sin(kat);
            y = 1.5f*(float)Math.cos(kat);
            gl.glVertex3f(x, y, 1.0f); //kolejne punkty
        }
        gl.glEnd();
        
        float c,v,kat2;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.5f, 0.0f, 9.0f);
        gl.glVertex3f(0.0f,0.0f,-1.0f); //?rodek
        for(kat2 = 0.0f; kat2 < (2.0f*Math.PI);kat2+=(Math.PI/32.0f))
        {
            c = 1.5f*(float)Math.sin(kat2);
            v = 1.5f*(float)Math.cos(kat2);
            gl.glVertex3f(c, v, -1.0f); //kolejne punkty
        }
        gl.glEnd();
        
        float b,n,kat3;
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        for(kat3 = 0.0f; kat3 < (2.0f*Math.PI);kat3+=(Math.PI/32.0f))
        {
            b = 1.5f*(float)Math.sin(kat3);
            n = 1.5f*(float)Math.cos(kat3);
            gl.glVertex3f(b, n, -1.0f); //kolejne punkty
            gl.glVertex3f(b, n, 1.0f); //kolejne punkty
        }
        gl.glEnd();
        

//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glEnd();
//        gl.glBegin(GL.GL_TRIANGLES);
//        //sciana przednia
//        gl.glColor3f(1.0f, 1.0f, 0.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(0.0f,1.0f,0.0f);
//        //sciana prawa
//        gl.glColor3f(1.0f, 1.0f, 1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(0.0f,1.0f,0.0f);
//        //sciana lewa
//        gl.glColor3f(0.0f, 1.0f, 1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(0.0f,1.0f,0.0f);
//        //sciana tylnia
//        gl.glColor3f(1.0f, 0.0f, 1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(0.0f,1.0f,0.0f);
//        gl.glEnd();
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
