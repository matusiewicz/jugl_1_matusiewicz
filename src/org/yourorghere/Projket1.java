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
    public static float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat?o otaczajšce
    public static float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat?o rozproszone
    public static float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos[] = { 0.0f, 5.0f, 5.0f, 1.0f };//pozycja ?wiat?a
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Projket1());
        frame.add(canvas);
        frame.setSize(1024, 768);
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
                xrot -= 2.0f;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                xrot +=2.0f;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                yrot += 2.0f;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                yrot -=2.0f;
                if(e.getKeyChar()=='q')
                    ambientLight = new float[]{ambientLight[0]-0.1f,ambientLight[1]-0.1f,ambientLight[2]-0.1f,ambientLight[3]};
                if(e.getKeyChar()=='w')
                    ambientLight = new float[]{ambientLight[0]+0.1f,ambientLight[1]+0.1f,ambientLight[2]+0.1f,ambientLight[3]};
                if(e.getKeyChar()=='a')
                    diffuseLight = new float[]{diffuseLight[0]-0.1f,diffuseLight[1]-0.1f,diffuseLight[2]-0.1f,diffuseLight[3]};
                if(e.getKeyChar()=='s')
                    diffuseLight = new float[]{diffuseLight[0]+0.1f,diffuseLight[1]+0.1f,diffuseLight[2]+0.1f,diffuseLight[3]};
                if(e.getKeyChar()=='z')
                    specular = new float[]{specular[0]-0.1f,specular[1]-0.1f,specular[2]-0.1f,specular[3]};
                if(e.getKeyChar()=='x')
                    specular = new float[]{specular[0]+0.1f,specular[1]+0.1f,specular[2]+0.1f,specular[3]};
                if(e.getKeyChar()=='k')
                    lightPos = new float[]{lightPos[0]-0.1f,lightPos[1]-0.1f,lightPos[2]-0.1f,lightPos[3]};
                if(e.getKeyChar()=='l')
                    lightPos = new float[]{lightPos[0]+0.1f,lightPos[1]+0.1f,lightPos[2]+0.1f,lightPos[3]};
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
        //warto?ci sk?adowe o?wietlenia i koordynaty ?ród?a ?wiat?a
        //(czwarty parametr okre?la odleg?o?? ?ród?a:
        //0.0f-niesko?czona; 1.0f-okre?lona przez pozosta?e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat?o otaczajšce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród?a ?wiat?a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolorów
        //kolory b?dš ustalane za pomocš glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiektów
        float specref[] = { 1.0f, 1.0f, 1.0f, 1.0f }; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR,specref,0);
        
        gl.glMateriali(GL.GL_FRONT,GL.GL_SHININESS,128);

        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
    void walec(GL gl)
 {
//wywo?ujemy automatyczne normalizowanie normalnych
//bo operacja skalowania je zniekszta?ci
gl.glEnable(GL.GL_NORMALIZE);
float x,y,kat;
gl.glBegin(GL.GL_QUAD_STRIP);
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat),0.0f);
gl.glVertex3f(x, y, -1.0f);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
gl.glNormal3f(0.0f,0.0f,-1.0f);
x=y=kat=0.0f;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glVertex3f(x, y, -1.0f);
}
gl.glEnd();
gl.glNormal3f(0.0f,0.0f,1.0f);
x=y=kat=0.0f;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
for(kat = 2.0f*(float)Math.PI; kat > 0.0f ; kat -= (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
}

void stozek(GL gl)
{
//wywo?ujemy automatyczne normalizowanie normalnych
gl.glEnable(GL.GL_NORMALIZE);
float x,y,kat;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = (float)Math.sin(kat);
y = (float)Math.cos(kat);
gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat),-2.0f);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glNormal3f(0.0f,0.0f,1.0f);
gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
for(kat = 2.0f*(float)Math.PI; kat > 0.0f; kat -= (Math.PI/32.0f))
{
x = (float)Math.sin(kat);
y = (float)Math.cos(kat);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
}
void drzewo(GL gl){
     gl.glPushMatrix();
     gl.glColor3f(0.0f,1.0f,0.0f);
     gl.glScalef(0.5f, 0.5f, 0.5f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glScalef(1.2f, 1.2f, 1.0f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glScalef(1.5f, 1.5f, 1.0f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glScalef(1.0f, 1.0f, 1.0f);
        gl.glColor3f(1.0f, 1.0f, 0.5f);
        walec(gl);
        gl.glPopMatrix();
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
        // Zmien swiatlo
        gl.glEnable(GL.GL_LIGHTING);
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat?o otaczajšce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //swiatlo odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        for(int i=1;i<=5;i++){
            gl.glPushMatrix();
            for(int j=1;j<=5;j++){
                drzewo(gl);
                gl.glTranslatef(0.0f, 1.5f, 0.0f);
            }
            gl.glPopMatrix();
            gl.glTranslatef(1.5f, 0.0f, 0.0f);
        }
       // Walec
//        float x,y,kat;
//        gl.glBegin(GL.GL_TRIANGLE_FAN);
//        gl.glColor3f(1.0f, 1.0f, 0.0f);
//        gl.glVertex3f(0.0f,0.0f,1.0f); //?rodek
//        for(kat = (float) (2.0f*Math.PI); kat >= 0.0f;kat-=(Math.PI/32.0f))
//        {
//            x = 1.5f*(float)Math.sin(kat);
//            gl.glVertex3f(x, y, 1.0f); //kolejne punkty
//        }
//        gl.glEnd();
//        
//        float c,v,kat2;
//        gl.glBegin(GL.GL_TRIANGLE_FAN);
//        gl.glColor3f(1.0f, 1.0f, 0.0f);
//        gl.glVertex3f(0.0f,0.0f,-1.0f); //?rodek
//        for(kat2 = 0.0f; kat2 < (2.0f*Math.PI);kat2+=(Math.PI/32.0f))
//        {
//            c = 1.5f*(float)Math.sin(kat2);
//            v = 1.5f*(float)Math.cos(kat2);
//            gl.glVertex3f(c, v, -1.0f); //kolejne punkty
//        }
//        gl.glEnd();
//        
//        float b,n,kat3;
//        gl.glBegin(GL.GL_QUAD_STRIP);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        for(kat3 = 0.0f; kat3 < (2.0f*Math.PI);kat3+=(Math.PI/32.0f))
//        {
//            b = 1.5f*(float)Math.sin(kat3);
//            n = 1.5f*(float)Math.cos(kat3);
//            gl.glVertex3f(b, n, -1.0f); //kolejne punkty
//            gl.glVertex3f(b, n, 1.0f); //kolejne punkty
//        }
//        gl.glEnd();
        //Stozek
//        float x,y,kat;
//        gl.glBegin(GL.GL_TRIANGLE_FAN);
//        gl.glColor3f(1.0f, 1.0f, 0.0f);
//        gl.glVertex3f(0.0f,4.0f,0.0f); //?rodek
//        for(kat = (float) (2.0f*Math.PI); kat >= 0.0f;kat-=(Math.PI/32.0f))
//        {
//            x = 1.5f*(float)Math.sin(kat);
//            y = 1.5f*(float)Math.cos(kat);
//            gl.glVertex3f(x, 1.0f, y); //kolejne punkty
//        }
//        gl.glEnd();
//        float x1,y1,kat1;
//        gl.glBegin(GL.GL_TRIANGLE_FAN);
//        gl.glColor3f(0.0f, 1.0f, 0.0f);
//        gl.glVertex3f(0.0f,1.0f,0.0f); //?rodek
//        for(kat1 = 0.0f; kat1 <(2.0f*Math.PI);kat1+=(Math.PI/32.0f))
//        {
//            x1= 1.5f*(float)Math.sin(kat1);
//            y1 = 1.5f*(float)Math.cos(kat1);
//            gl.glVertex3f(x1, 1.0f, y1); //kolejne punkty
//        }
//        gl.glEnd();
//        
//        
        //ostroslup
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
        //szescian
        //scian przednia
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glEnd();
//        //sciana prawa
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,1.0f,-1.0f);
//        gl.glVertex3f(1.0f,1.0f,1.0f);
//        gl.glEnd();
//        //sciana tylnia
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,1.0f,-1.0f);
//        gl.glVertex3f(1.0f,1.0f,-1.0f);
//        gl.glEnd();
//        //sciana lewa
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,1.0f,1.0f);
//        gl.glVertex3f(-1.0f,1.0f,-1.0f);
//        gl.glEnd();
//        //gora
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(-1.0f,1.0f,1.0f);
//        gl.glVertex3f(1.0f,1.0f,1.0f);
//        gl.glVertex3f(1.0f,1.0f,-1.0f);
//        gl.glVertex3f(-1.0f,1.0f,-1.0f);
//        gl.glEnd();
//        //dol
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f, 0.0f, 0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glEnd();
//        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
