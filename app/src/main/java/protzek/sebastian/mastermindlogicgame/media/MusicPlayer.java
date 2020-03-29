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

    public void getSound(Context context) {
        if (musicPlayer.getPlayer() == null) {
            // TODO: add more soundtracks?
            musicPlayer.setPlayer(MediaPlayer.create(context, R.raw.gary_glitter_rock_roll_part_2));
            musicPlayer.getPlayer().setLooping(true);
        }
        if (!musicPlayer.getPlayer().isPlaying()) {
            musicPlayer.getPlayer().start();
        }
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }
}
