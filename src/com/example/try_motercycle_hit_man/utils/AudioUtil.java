package com.example.try_motercycle_hit_man.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.try_motercycle_hit_man.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioUtil {
    private static MediaPlayer music;
    private static SoundPool soundPool;
     
    private static boolean musicSt = true; //?��??��?
    private static boolean soundSt = true; //?��??��?
    private static Context context;
     
    private static final int[] musicId = {R.raw.mainmenu_bg_music, R.raw.game_map_music, R.raw.game_main_music, R.raw.time_count_music, R.raw.gameover_music, R.raw.v2};
    private static Map<Integer,Integer> soundMap; //?��?資�?id?��??��?後�??��?id?��?射�?係表
     
    /**
     * ?��??�方�?
     * @param c
     */
    public static void init(Context c)
    {
        context = c;
 
        initMusic();
         
        initSound();
    }
     
    //?��??�音?�播?��?
    private static void initSound()
    {
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,100);
         
        soundMap = new HashMap<Integer,Integer>();
        soundMap.put(R.raw.bomb_music, soundPool.load(context, R.raw.bomb_music, 1));
        soundMap.put(R.raw.brick_hit_music, soundPool.load(context, R.raw.brick_hit_music, 1));
    }
     
    //?��??�音樂播?��?
    private static void initMusic()
    {
//        int r = new Random().nextInt(musicId.length);
//        music = MediaPlayer.create(context,musicId[r]);
    	music = MediaPlayer.create(context,musicId[0]);
        music.setLooping(true);
    }
    
    /**
     * ?�放?��?
     * @param resId ?��?資�?id
     */
    public static void playMusic(int resId)
    {
        if(musicSt == false)
            return;
         
    	music = MediaPlayer.create(context, resId);
        music.setLooping(true);
        startMusic();
    }
     
    /**
     * ?�放?��?
     * @param resId ?��?資�?id
     */
    public static void playSound(int resId)
    {
        if(soundSt == false)
            return;
         
        Integer soundId = soundMap.get(resId);
        if(soundId != null)
            soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
 
    /**
     * ?��??��?
     */
    public static void pauseMusic()
    {
        if(music.isPlaying())
            music.pause();
    }
     
    /**
     * ?�放?��?
     */
    public static void startMusic()
    {
        if(musicSt)
            music.start();
    }
     
    /**
     * ?��?�???��?並播??
     */
    public static void changeAndPlayMusic()
    {
        if(music != null)
            music.release();
        initMusic();
        startMusic();
    }
     
    /**
     * ?��??��??��????
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }
     
    /**
     * 設置?��??��?
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt) {
    	AudioUtil.musicSt = musicSt;
        if(musicSt)
            music.start();
        else
            music.stop();
    }
 
    /**
     * ?��??��??��????
     * @return
     */
    public static boolean isSoundSt() {
        return soundSt;
    }
 
    /**
     * 設置?��??��?
     * @param soundSt
     */
    public static void setSoundSt(boolean soundSt) {
    	AudioUtil.soundSt = soundSt;
    }
    
//    static AudioManager mgr;
//    //?�放?��?
//    public static void PlaySoundPool(int resid){
//     if(soundSt==false){
//      return;
//     }
//     Integer soundId = soundMap.get(resid);
//     if(soundId!=null && soundSt){
//    	 mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//      soundPool.play(soundId, 
//        mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 
//        mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 1, 0, 1f);
//      System.out.println("1--->"+resid);
//     }
//    }
     
    /**
     * ?�出?�炸?�聲??
     */
    public static void boom()
    {
        playSound(R.raw.bomb_music);
    }
    
    /**
     * ?�出丁�??�音
     */
    public static void brickHit()
    {
        playSound(R.raw.brick_hit_music);
    }
}

