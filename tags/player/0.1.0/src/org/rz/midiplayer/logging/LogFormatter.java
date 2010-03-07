
package org.rz.midiplayer.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author rz
 */
public class LogFormatter extends Formatter
{

    private final StringBuffer stringBuffer = new StringBuffer( 2048 );

    ////////////////////////////////////////////////////////////////////////////
    /**
     * LogFormatter インスタンスを生成する。
     */
    public LogFormatter()
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @see Formatter#format(java.util.logging.LogRecord) 
     */
    @Override
    synchronized public String format( LogRecord lr )
    {
        Throwable err = lr.getThrown();
        StringBuffer sb = stringBuffer;

        sb.delete( 0, stringBuffer.length() );

        String nl  = System.getProperty( "line.separator", "\n" );

        if( err != null )
        {
            sb.append( "#################### EXCEPTION ####################" ).append( nl );
        }

        sb.append( String.format( "[%s] <%s#%s>: %s%s",
                                    lr.getLevel(),
                                    lr.getSourceClassName(),
                                    lr.getSourceMethodName(),
                                    lr.getMessage(),
                                    nl
        ));

        if( err != null )
        {
            StackTraceElement[] stack = err.getStackTrace();
            if( stack != null )
            {
                String indent1 = "    ";

                int len = stack.length;
                sb.append( err.getClass().getName() ).append( " : " ).append( err.getMessage() ).append( nl );

                for( int i = 0; i < len; i++ )
                {
                    sb.append( indent1 );
                    sb.append( stack[ i ].toString() ).append( nl );
                }
            }
        }

        return sb.toString();
    }

}
