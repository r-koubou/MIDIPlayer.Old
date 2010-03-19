
package org.rz.midiplayerplugin.renderer.pianoroll3d;

/**
 *
 * @author rz
 */
public class Point3D
{
    public float tx;
    public float ty;
    public float tz;

    public float rx;
    public float ry;
    public float rz;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Point3D インスタンスを生成する。
     */
    public Point3D()
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void copyTo( Point3D dest )
    {
        dest.tx = tx;
        dest.ty = ty;
        dest.tz = tz;
        dest.rx = rx;
        dest.ry = ry;
        dest.rz = rz;
    }
}
