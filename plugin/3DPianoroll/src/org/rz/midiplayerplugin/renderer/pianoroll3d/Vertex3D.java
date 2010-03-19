
package org.rz.midiplayerplugin.renderer.pianoroll3d;

/**
 *
 * @author rz
 */
public class Vertex3D
{

    float x, y, z;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Vertex3D インスタンスを生成する。
     */
    public Vertex3D()
    {
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Vertex3D インスタンスを生成する。
     */
    public Vertex3D( float x_, float y_, float z_ )
    {
        x = x_;
        y = y_;
        z = z_;
    }
}
