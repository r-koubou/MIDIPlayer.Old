
package org.rz.midiplayer.context;

import java.util.Hashtable;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.xmlmodule.deviceinfo.InstType;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;
import org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentMap;

/**
 *
 * @author rz
 */
public class InstrumentList implements Loggable
{
    /** 検索して音色が見つからない場合の代替 */
    static public final Instrument nullInstrument;

    /** 音色リスト格納用 */
    private final Hashtable< String, Hashtable< HashKey, Instrument >> meloInstrumentTable = new Hashtable<String, Hashtable< HashKey, Instrument >>();
    //private final Hashtable< HashKey, Instrument > meloInstrumentTable = new Hashtable<HashKey, Instrument>( 2048 );

    /** ドラム音色リスト格納用 */
    private final Hashtable< String, Hashtable< HashKey, Instrument >> drumInstrumentTable = new Hashtable<String, Hashtable< HashKey, Instrument >>();
    //private final Hashtable< HashKey, Instrument > drumInstrumentTable = new Hashtable<HashKey, Instrument>( 512 );

    /** 検索用のワ?ク */
    static private final HashKey hashKey = new HashKey();

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * static initializer
     */
    static
    {
        nullInstrument = new Instrument();
        nullInstrument.setLsb( 0 );
        nullInstrument.setMsb( 0 );
        nullInstrument.setPc( 0 );
        nullInstrument.setName( "--------" );
        nullInstrument.setMapName( "--------" );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * InstrumentList インスタンスを生成する。
     */
    InstrumentList()
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void clear()
    {
        meloInstrumentTable.clear();
        drumInstrumentTable.clear();
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * 音色マップの登録
     */
    void add( String mode, InstrumentMap map  )
    {
        switch( map.getType() )
        {
            case MELO: { meloInstrumentTable.put( mode, generateMap( map, 8192 ) ); } break;
            case DRUM: { drumInstrumentTable.put( mode, generateMap( map, 1024  ) ); } break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * 音色マップの登録
     */
    private Hashtable<HashKey, Instrument> generateMap( InstrumentMap map, int initialCapacity )
    {
        Hashtable<HashKey, Instrument> ret = new Hashtable<HashKey, Instrument>( initialCapacity );

        for( Instrument i : map.getInstrument() )
        {
            HashKey key = new HashKey();
            key.pc  = i.getPc();
            key.msb = i.getMsb();
            key.lsb = i.getLsb();
            ret.put( key, i );
        }

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public Instrument search( String mode, InstType type, int pc, int msb, int lsb )
    {
        Instrument ret = null;

        synchronized( hashKey )
        {
            hashKey.pc  = pc;
            hashKey.msb = msb;
            hashKey.lsb = lsb;

            Hashtable<HashKey, Instrument> table = null;

            switch( type )
            {
                case MELO: table = meloInstrumentTable.get( mode ); break;
                case DRUM: table = drumInstrumentTable.get( mode ); break;
            }

            if( table == null )
            {
                return nullInstrument;
            }

            ret = table.get( hashKey );
        }

        if( ret == null )
        {
            return nullInstrument;
        }

        return ret;

    }

    static private class HashKey
    {
        int pc;
        int msb;
        int lsb;

        @Override
        public boolean equals( Object obj )
        {
            if( obj == null || !( obj instanceof HashKey ) )
            {
                return false;
            }

            HashKey src = (HashKey)obj;

            return this.pc  == src.pc  &&
                   this.msb == src.msb &&
                   this.lsb == src.lsb;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 89 * hash + this.pc;
            hash = 89 * hash + this.msb;
            hash = 89 * hash + this.lsb;
            return hash;
        }

        @Override
        public String toString()
        {
            return "pc="  + pc  + "," +
                   "msb=" + msb + "," +
                   "lsb=" + lsb;
        }


    }

}
