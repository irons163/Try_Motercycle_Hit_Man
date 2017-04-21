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
     
    private static boolean musicSt = true; //?³æ??‹é?
    private static boolean soundSt = true; //?³æ??‹é?
    private static Context context;
     
    private static final int[] musicId = {R.raw.mainmenu_bg_music, R.raw.game_map_music, R.raw.game_main_music, R.raw.time_count_music, R.raw.gameover_music, R.raw.v2};
    private static Map<Integer,Integer> soundMap; //?³æ?è³‡æ?id?‡è??¥é?å¾Œç??³æ?id?„æ?å°„é?ä¿‚è¡¨
     
    /**
     * ?å??–æ–¹æ³?
     * @param c
     */
    public static void init(Context c)
    {
        context = c;
 
        initMusic();
         
        initSound();
    }
     
    //?å??–éŸ³?ˆæ’­?¾æ?
    private static void initSound()
    {
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,100);
         
        soundMap = new HashMap<Integer,Integer>();
        soundMap.put(R.raw.bomb_music, soundPool.load(context, R.raw.bomb_music, 1));
        soundMap.put(R.raw.brick_hit_music, soundPool.load(context, R.raw.brick_hit_music, 1));
    }
     
    //?å??–éŸ³æ¨‚æ’­?¾æ?
    private static void initMusic()
    {
//        int r = new Random().nextInt(musicId.length);
//        music = MediaPlayer.create(context,musicId[r]);
    	music = MediaPlayer.create(context,musicId[0]);
        music.setLooping(true);
    }
    
    /**
     * ?­æ”¾?³æ?
     * @param resId ?³æ?è³‡æ?id
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
     * ?­æ”¾?³æ?
     * @param resId ?³æ?è³‡æ?id
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
     * ?«å??³æ?
     */
    public static void pauseMusic()
    {
        if(music.isPlaying())
            music.pause();
    }
     
    /**
     * ?­æ”¾?³æ?
     */
    public static void startMusic()
    {
        if(musicSt)
            music.start();
    }
     
    /**
     * ?‡æ?ä¸???³æ?ä¸¦æ’­??
     */
    public static void changeAndPlayMusic()
    {
        if(music != null)
            music.release();
        initMusic();
        startMusic();
    }
     
    /**
     * ?²å??³æ??‹é????
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }
     
    /**
     * è¨­ç½®?³æ??‹é?
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
     * ?²å??³æ??‹é????
     * @return
     */
    public static boolean isSoundSt() {
        return soundSt;
    }
 
    /**
     * è¨­ç½®?³æ??‹é?
     * @param soundSt
     */
    public static void setSoundSt(boolean soundSt) {
    	AudioUtil.soundSt = soundSt;
    }
    
//    static AudioManager mgr;
//    //?­æ”¾?³æ?
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
     * ?¼å‡º?†ç‚¸?„è²??
     */
    public static void boom()
    {
        playSound(R.raw.bomb_music);
    }
    
    /**
     * ?¼å‡ºä¸ç??²éŸ³
     */
    public static void brickHit()
    {
        playSound(R.raw.brick_hit_music);
    }
}

