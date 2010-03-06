package org.rz.midiplayerplugin.renderer;

/**
 *
 * @author rz
 */
public class NoteObjectPool
{

    private NoteObject[] pool;
    private final int size;
    private int position;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * NoteObjectPool インスタンスを生成する。
     */
    public NoteObjectPool( int size_ )
    {
        size = size_;
        pool = new NoteObject[ size ];
        for( int i = 0; i < size; i++ )
        {
            pool[ i ] = new NoteObject();
        }
        position = 0;
    }

    public NoteObject create()
    {
        NoteObject ret = pool[ position ];
        position++;
        position %= size;
        ret.reset();
        return ret;
    }


}
