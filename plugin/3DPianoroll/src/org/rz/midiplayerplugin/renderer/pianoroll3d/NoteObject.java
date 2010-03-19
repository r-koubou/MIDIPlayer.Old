
package org.rz.midiplayerplugin.renderer.pianoroll3d;

import com.sun.opengl.util.GLUT;
import java.awt.Color;
import javax.media.opengl.GL;

/**
 *
 * @author rz
 */
public class NoteObject
{

    static final float SPEED = 1f;

    protected float x, y, z, z2;

    volatile boolean visible;
    volatile boolean noteOn;

    volatile int channel;
    volatile int noteNo;

    volatile private Color activeColor    = Color.RED;
    volatile private Color deactiveColor  = activeColor.darker();

    volatile float scale = 1.0f;

    private float[][] defaultVertices =
    {
        {
            1, 1, 1
        },
        {
            -1, 1, 1
        },
        {
            -1, -1, 1
        },
        {
            1, -1, 1
        },

        {
            1, 1, -1
        },
        {
            -1, 1, -1
        },
        {
            -1, -1, -1
        },
        {
            1, -1, -1
        }
    };

    private final float[][] normals =
    {
        { 0, 0,  1 },   // front
        { 0, 0, -1 },   // rear
        { 1, 0, 0  },   // right
        { -1, 0, 0  },  // left
        { 0, 1, 0  },   // top
        { 0, -1, 0  },  // bottom
    };

    private float[][] vertices =
    {
        {
            1, 1, 1
        },
        {
            -1, 1, 1
        },
        {
            -1, -1, 1
        },
        {
            1, -1, 1
        },

        {
            1, 1, -1
        },
        {
            -1, 1, -1
        },
        {
            -1, -1, -1
        },
        {
            1, -1, -1
        }
    };

    static private final float[] VEL2SCALE_TABLE =
    {
        0.25f,
        0.50f,
        0.75f,
        1.0f,
    };

    public NoteObject()
    {
        super();
        reset();
    }

    public void reset()
    {
        x = y = z = 0;
        visible = false;
        noteOn  = false;
        channel = 0;
        noteNo  = 0;

        for( int i = 0; i < 8; i++ )
        {
            vertices[ i ][ 0 ] = defaultVertices[ i ][ 0 ];
            vertices[ i ][ 1 ] = defaultVertices[ i ][ 1 ];
            vertices[ i ][ 2 ] = defaultVertices[ i ][ 2 ];
        }

    }

    public void noteOn( float sx, float sy, int vel )
    {
        x       = sx;
        y       = sy;
        z       = 0;
        z2      = 0;
        visible = true;
        noteOn  = true;

        vertices[ 4 ][ 2 ] = SPEED;
        vertices[ 5 ][ 2 ] = SPEED;
        vertices[ 6 ][ 2 ] = SPEED;
        vertices[ 7 ][ 2 ] = SPEED;

        if( vel <= 0 )
        {
            vel = 1;
        }

        //scale = (float)vel / 127;
        scale = VEL2SCALE_TABLE[  vel >> 5 ];

    }

    public void noteOff()
    {
        noteOn  = false;
    }

    public void update()
    {
        if( visible )
        {
            float delta;

            delta =SPEED;
            
            z = delta / scale;

            if( noteOn )
            {
                vertices[ 0 ][ 2 ] += z;
                vertices[ 1 ][ 2 ] += z;
                vertices[ 2 ][ 2 ] += z;
                vertices[ 3 ][ 2 ] += z;
            }
            else
            {
                z2 += delta;
                vertices[ 0 ][ 2 ] += z;
                vertices[ 1 ][ 2 ] += z;
                vertices[ 2 ][ 2 ] += z;
                vertices[ 3 ][ 2 ] += z;
                vertices[ 4 ][ 2 ] += z;
                vertices[ 5 ][ 2 ] += z;
                vertices[ 6 ][ 2 ] += z;
                vertices[ 7 ][ 2 ] += z;

                if( z2  > 800f )
                {
                    reset();
                }
            }
        }
    }

    float[] color = { 1.0f, 0f, 0f, 1 };

    synchronized public void render( GL gl, GLUT glut )
    {
        int r = 0, g = 0, b = 0;

        if( ! visible )
        {
            return;
        }

        {
            Color c = noteOn ? activeColor : deactiveColor;
            r = c.getRed();
            g = c.getGreen();
            b = c.getBlue();

            color[ 0 ] = (float)r / 255;
            color[ 1 ] = (float)g / 255;
            color[ 2 ] = (float)b / 255;

            gl.glLightfv( GL.GL_LIGHT0, GL.GL_DIFFUSE, color, 0 );
        }


        gl.glPushMatrix();
        gl.glTranslatef( x, y, -250 );
        gl.glScalef( scale, scale, scale );

        gl.glBegin( GL.GL_QUADS );
        gl.glNormal3fv( normals[ 0 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[0], 0 );
        gl.glVertex3fv( vertices[1], 0 );
        gl.glVertex3fv( vertices[2], 0 );
        gl.glVertex3fv( vertices[3], 0 );
        gl.glNormal3fv( normals[ 1 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[7], 0 );
        gl.glVertex3fv( vertices[6], 0 );
        gl.glVertex3fv( vertices[5], 0 );
        gl.glVertex3fv( vertices[4], 0 );
        gl.glNormal3fv( normals[ 2 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[0], 0 );
        gl.glVertex3fv( vertices[3], 0 );
        gl.glVertex3fv( vertices[7], 0 );
        gl.glVertex3fv( vertices[4], 0 );
        gl.glNormal3fv( normals[ 3 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[5], 0 );
        gl.glVertex3fv( vertices[6], 0 );
        gl.glVertex3fv( vertices[2], 0 );
        gl.glVertex3fv( vertices[1], 0 );
        gl.glNormal3fv( normals[ 4 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[4], 0 );
        gl.glVertex3fv( vertices[5], 0 );
        gl.glVertex3fv( vertices[1], 0 );
        gl.glVertex3fv( vertices[0], 0 );
        gl.glNormal3fv( normals[ 5 ], 0 );
        gl.glColor3ub( (byte)r, (byte)g, (byte)b );
        gl.glVertex3fv( vertices[6], 0 );
        gl.glVertex3fv( vertices[7], 0 );
        gl.glVertex3fv( vertices[3], 0 );
        gl.glVertex3fv( vertices[2], 0 );

        gl.glEnd();
        gl.glPopMatrix();

    }

    synchronized public void setColor( Color c )
    {
        activeColor    = c;
        deactiveColor  = c.darker().darker();
    }

}
