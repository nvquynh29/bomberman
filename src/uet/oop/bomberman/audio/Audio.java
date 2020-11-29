package uet.oop.bomberman.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.io.File;
import java.nio.file.Paths;

public class Audio {
    public static final String buffSoundPath = "res/audio/buff.wav";
    public static final String deadPath = "res/audio/dead.mp3";
    public static final String explosionPath = "res/audio/explosion.wav";
    public static final String foot1Path = "res/audio/footstep1.mp3";
    public static final String foot2Path = "res/audio/footstep2.mp3";
    public static final String portalPath = "res/audio/portal.wav";
    public static void MakeSomeNoise(String path) {

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }
}
