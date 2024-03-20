package comp1110.ass2;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Haipeng Yan
 */
public class testMusic {

    // This is a workaround to initialize JavaFX toolkit in JUnit
    @BeforeEach
    public void init() {
        JFXPanel panel = new JFXPanel();
    }

    @Test
    public void testAudioUrlNotNull() {
        testMusic musicApp = new testMusic();
        URL audioUrl = musicApp.getClass().getResource("./Marrakech-Markets.wav");
        assertNotNull(audioUrl, "Audio URL should not be null");
    }

    @Test
    public void testPlayPauseButton() {
        testMusic musicApp = new testMusic();
        URL audioUrl = musicApp.getClass().getResource("./Marrakech-Markets.wav");
        Media backgroundMusic = new Media(audioUrl.toString());
        MediaPlayer musicPlayer = new MediaPlayer(backgroundMusic);

        Button playPauseButton = new Button("Play/Pause");

        // Simulate button click
        playPauseButton.fire();
        assertEquals(MediaPlayer.Status.PLAYING, musicPlayer.getStatus(), "Music should be playing after button is clicked");

        playPauseButton.fire();
        assertEquals(MediaPlayer.Status.PAUSED, musicPlayer.getStatus(), "Music should be paused after button is clicked again");
    }

    // Add more tests as needed
}

