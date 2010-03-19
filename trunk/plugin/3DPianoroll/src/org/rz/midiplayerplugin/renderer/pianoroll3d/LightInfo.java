
package org.rz.midiplayerplugin.renderer.pianoroll3d;

/**
 *
 * @author rz
 */
public class LightInfo
{
    private final float[] lightPosition =
    {
        0.0f, 0.7f, 1.0f, 0.0f
    };
    private final float[] lightAmbient =
    {
        0.25f, 0.25f, 0.25f
    };
    private final float[] lightSpecular =
    {
        //1.0f, 1.0f, 1.0f
        0.6f, 0.6f, 0.6f
    };

    ////////////////////////////////////////////////////////////////////////////
    /**
     * LightInfo インスタンスを生成する。
     */
    public LightInfo()
    {
    }

    /**
     * @return the lightPosition
     */
    public float[] getLightPosition()
    {
        return lightPosition;
    }

    /**
     * @return the lightAmbient
     */
    public float[] getLightAmbient()
    {
        return lightAmbient;
    }

    /**
     * @return the lightSpecular
     */
    public float[] getLightSpecular()
    {
        return lightSpecular;
    }

}
