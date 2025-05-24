package gui;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundPlayer {
    private static final HashMap<String, Clip> cache = new HashMap<>();
    private static Clip clip;

    public static void loadSound(String key, String filePath) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            cache.put(key, clip);
        } catch (Exception e) {
            System.err.println("Não consegui carregar som " + filePath + " → " + e.getMessage());
        }
    }

    public static void playLoadedSound(String key, int volume) {
        Clip clip = cache.get(key);
        if (clip == null) return;
        // ajusta volume
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume - 100);
        }
        // “reinicia” o clip e toca
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playSound(String filePath, float volume) {
        try {
        File file = new File(filePath);
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume - 100);
        }

        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        System.err.println("Erro ao tocar som: " + filePath + " → " + e.getMessage());
    }
    }


    public static void playMusic(String filePath, boolean loop, int volume) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume - 100);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
