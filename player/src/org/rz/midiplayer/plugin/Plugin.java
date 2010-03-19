
package org.rz.midiplayer.plugin;

import java.io.File;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.logging.Loggable;

/**
 * プラグインを実装する際の基底となるインタフェース。
 * @author rz
 */
public interface Plugin extends Loggable
{
    ////////////////////////////////////////////////////////////////////////////////
    /**
     * インスタンスが生成後に１度呼び出される。適宜初期化処理を実行する。
     * @param ctx プレーヤーコンテキスト
     * @param  pluginDir  このプラグインが配備されているディレクトリへのパス文字列
     */
    void onInit( Context ctx, String pluginDir );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * インスタンスが使用されなくなる際に１度呼び出される。適宜インスタンスの破棄、設定情報のファイル出力などを実行する。
     * @param ctx プレーヤーコンテキスト
     */
    void onDispose( Context ctx );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * MIDI ファイルが再生を開始する直前に呼び出される。
     * @param  midiFile これから再生される予定の MIDI ファイル名
     */
    void onMidiPlayingBefore( File midiFile );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * ユーザーが MIDI 再生を停止する操作を行った時に呼び出される。
     */
    void onMidiStoped();
}
