package protzek.sebastian.mastermindlogicgame.media;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

import protzek.sebastian.mastermindlogicgame.R;
import protzek.sebastian.mastermindlogicgame.mainmenu.options.Default;

public class SoundPlayer {
    private SoundPool soundPool;
    private ArrayList<Integer> soundList;
    private boolean soundOn;


    public SoundPlayer(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        soundOn = prefs.getBoolean(context.getResources().getString(R.string.sound_key), Default.VOLUME);

        int sound1 = soundPool.load(context, R.raw.pressed_button, 1);
        int sound2 = soundPool.load(context, R.raw.are_you_sure, 1);
        int sound3 = soundPool.load(context, R.raw.start_game, 1);
        int sound4 = soundPool.load(context, R.raw.drop_ball, 1);
        int sound5 = soundPool.load(context, R.raw.next_turn, 1);
        int sound6 = soundPool.load(context, R.raw.you_lost, 1);
        int sound7 = soundPool.load(context, R.raw.you_won, 1);
        int sound8 = soundPool.load(context, R.raw.option_changed, 1);
        int sound9 = soundPool.load(context, R.raw.reset, 1);
        int sound10 = soundPool.load(context, R.raw.quit_game, 1);
        int sound11 = soundPool.load(context, R.raw.info_button, 1);

        soundList = new ArrayList<>(Arrays.asList(sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9, sound10, sound11));
    }

    public void playSound(int sound) {
        if (soundOn) {
            soundPool.play((soundList.get(sound - 1)), 1, 1, 1, 0, 1);
        }
    }
}