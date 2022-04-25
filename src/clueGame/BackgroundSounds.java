package clueGame;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class BackgroundSounds implements Runnable {
	String soundFile;
    // Reference Credits
    // https://stackoverflow.com/questions/18088603/how-to-make-calling-a-method-as-a-background-process-in-java
    // https://soundbible.com/search.php?q=movement
    // https://stackoverflow.com/questions/10645594/what-audio-format-should-i-use-for-java
    public BackgroundSounds(String soundType) {    	
    	String prepath = "";
    	//String prepath = "ClueGame/";
    	
    	if (soundType.equals("ComputerMove")) {
    		soundFile = prepath + "sounds/MoveComputerPlayer.wav";
    	}
    	else if (soundType.equals("HumanMove")) {
    		soundFile = prepath + "sounds/MoveHumanPlayer.wav";
    	}
    	else if (soundType.equals("OpeningMurder")) {
    		soundFile = prepath + "sounds/Murder.wav";
    	}    	
    	else if (soundType.equals("DoorOpen")) {
    		soundFile = prepath + "sounds/DoorOpen.wav";
    	}    	
    	else if (soundType.equals("Suggestion")) {
    		soundFile = prepath + "sounds/Suggestion.wav";
    	}    	
    	else if (soundType.equals("Accusation")) {
    		soundFile = prepath + "sounds/Accusation.wav";
    	}    	
    }

    public void run() {
		File f = new File(soundFile);
        Clip clip = null;
		try {
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	        clip = AudioSystem.getClip();
	        clip.open(audioIn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		if (clip != null) {
			clip.start();
		}       
    }
}
