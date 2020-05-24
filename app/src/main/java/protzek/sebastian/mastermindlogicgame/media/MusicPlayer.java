package protzek.sebastian.mastermindlogicgame.media;

import android.content.Context;
import android.media.MediaPlayer;

import protzek.sebastian.mastermindlogicgame.R;

public class MusicPlayer {

    private static MusicPlayer musicPlayer = null;

    private MediaPlayer player;

    public static MusicPlayer getInstance() {
        if (musicPlayer == null) {
            musicPlayer = new MusicPlayer();
        }
        return musicPlayer;
    }

    public void getMusic(Context context) {
        if (musicPlayer.getPlayer() == null) {
            musicPlayer.setPlayer(MediaPlayer.create(context, R.raw.music_background_erokia_ambient_soundscape));
            musicPlayer.getPlayer().setLooping(true);
        }
        if (!musicPlayer.getPlayer().isPlaying()) {
            musicPlayer.getPlayer().start();
        }
    }

    public void stopMusic() {
        if (musicPlayer.getPlayer() != null) {
            musicPlayer.getPlayer().stop();
            musicPlayer.getPlayer().release();
        }
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }
}
