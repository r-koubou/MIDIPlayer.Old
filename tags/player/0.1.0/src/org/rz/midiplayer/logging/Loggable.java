
package org.rz.midiplayer.logging;

import java.util.logging.Logger;

/**
 * アプリケーション共有のロガーを使用可能にするためのインタフェース。
 * @author rz
 */
public interface Loggable
{
    Logger  logger       = Log.logger;
}
