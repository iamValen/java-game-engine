package gameManager;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Responsável por reproduzir os sons no jogo
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 24/05/2025
 */
public class SoundPlayer {

    private static final HashMap<String, Clip> cache = new HashMap<>();
    private static Clip clip;

    /**
     * Carrega um som e guarda no cache 
     * 
     * @param key Chave para aceder à cache
     * @param filePath Caminho para o ficheiro
     */
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

    /**
     * Toca um som carregado do cache com volume ajustado
     * 
     * @param key chave do som no cache
     * @param volume valor do volume (0-100)
     */
    public static void playLoadedSound(String key, int volume) {
        Clip clip = cache.get(key);
        if (clip == null) return;

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume - 100);
        }

        clip.stop();
        clip.setFramePosition(0); // Reinicia o som
        clip.start();
    }

    /**
     * Toca um som diretamente do arquivo, sem cache, com volume ajustado
     * 
     * @param filePath caminho do arquivo de áudio
     * @param volume volume do som (0-100)
     */
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
            System.err.println("Erro ao tocar som: " + filePath + " -> " + e.getMessage());
        }
    }

    /**
     * Toca música de fundo com opção de loop e volume ajustável
     * 
     * @param filePath caminho do arquivo de música
     * @param loop indica se deve repetir em loop
     * @param volume volume da música (0-100)
     */
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
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pàra a musica de fundo
     */
    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
