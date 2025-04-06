/**
 * Facade Design Pattern
 * 
 * Intent: Provide a unified interface to a set of interfaces in a subsystem.
 * Facade defines a higher-level interface that makes the subsystem easier to use.
 * 
 * This example demonstrates a home theater facade that simplifies the process of
 * watching a movie by coordinating multiple complex subsystems.
 */
package design.patterns.facade;

public class FacadePattern {
    
    public static void main(String[] args) {
        // Create the subsystem components
        Amplifier amplifier = new Amplifier();
        Tuner tuner = new Tuner();
        StreamingPlayer streamingPlayer = new StreamingPlayer();
        Projector projector = new Projector();
        Screen screen = new Screen();
        TheaterLights lights = new TheaterLights();
        PopcornPopper popcornPopper = new PopcornPopper();
        
        // Create the facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
                amplifier, tuner, streamingPlayer, projector, screen, lights, popcornPopper);
        
        // Use the simplified interface provided by the facade
        System.out.println("--- Starting Movie ---");
        homeTheater.watchMovie("Inception");
        
        System.out.println("\n--- Ending Movie ---");
        homeTheater.endMovie();
        
        System.out.println("\n--- Listening to Radio ---");
        homeTheater.listenToRadio(93.5);
        
        System.out.println("\n--- Turning Radio Off ---");
        homeTheater.endRadio();
    }
}

/**
 * Facade: HomeTheaterFacade
 * Provides a simplified interface to the complex subsystem
 */
class HomeTheaterFacade {
    private Amplifier amplifier;
    private Tuner tuner;
    private StreamingPlayer streamingPlayer;
    private Projector projector;
    private Screen screen;
    private TheaterLights lights;
    private PopcornPopper popcornPopper;
    
    public HomeTheaterFacade(Amplifier amplifier, Tuner tuner, StreamingPlayer streamingPlayer,
                            Projector projector, Screen screen, TheaterLights lights,
                            PopcornPopper popcornPopper) {
        this.amplifier = amplifier;
        this.tuner = tuner;
        this.streamingPlayer = streamingPlayer;
        this.projector = projector;
        this.screen = screen;
        this.lights = lights;
        this.popcornPopper = popcornPopper;
    }
    
    /**
     * Simplified method to watch a movie
     * Coordinates multiple subsystems
     */
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popcornPopper.on();
        popcornPopper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amplifier.on();
        amplifier.setStreamingPlayer(streamingPlayer);
        amplifier.setSurroundSound();
        amplifier.setVolume(5);
        streamingPlayer.on();
        streamingPlayer.play(movie);
    }
    
    /**
     * Simplified method to end a movie
     * Coordinates shutting down multiple subsystems
     */
    public void endMovie() {
        System.out.println("Shutting down the movie theater...");
        popcornPopper.off();
        lights.on();
        screen.up();
        projector.off();
        amplifier.off();
        streamingPlayer.stop();
        streamingPlayer.off();
    }
    
    /**
     * Simplified method to listen to the radio
     */
    public void listenToRadio(double frequency) {
        System.out.println("Tuning in to the radio...");
        tuner.on();
        tuner.setFrequency(frequency);
        amplifier.on();
        amplifier.setTuner(tuner);
        amplifier.setVolume(5);
    }
    
    /**
     * Simplified method to end radio
     */
    public void endRadio() {
        System.out.println("Shutting down the radio...");
        tuner.off();
        amplifier.off();
    }
}

/**
 * Subsystem: Amplifier
 */
class Amplifier {
    private Tuner tuner;
    private StreamingPlayer streamingPlayer;
    private int volume;
    
    public void on() {
        System.out.println("Amplifier is ON");
    }
    
    public void off() {
        System.out.println("Amplifier is OFF");
    }
    
    public void setStreamingPlayer(StreamingPlayer streamingPlayer) {
        this.streamingPlayer = streamingPlayer;
        System.out.println("Amplifier set to Streaming Player");
    }
    
    public void setTuner(Tuner tuner) {
        this.tuner = tuner;
        System.out.println("Amplifier set to Tuner");
    }
    
    public void setStereoSound() {
        System.out.println("Amplifier set to stereo sound");
    }
    
    public void setSurroundSound() {
        System.out.println("Amplifier set to surround sound (5.1)");
    }
    
    public void setVolume(int level) {
        this.volume = level;
        System.out.println("Amplifier volume set to " + level);
    }
}

/**
 * Subsystem: Tuner
 */
class Tuner {
    private double frequency;
    
    public void on() {
        System.out.println("Tuner is ON");
    }
    
    public void off() {
        System.out.println("Tuner is OFF");
    }
    
    public void setFrequency(double frequency) {
        this.frequency = frequency;
        System.out.println("Tuner frequency set to " + frequency);
    }
    
    public void setAm() {
        System.out.println("Tuner set to AM mode");
    }
    
    public void setFm() {
        System.out.println("Tuner set to FM mode");
    }
}

/**
 * Subsystem: StreamingPlayer
 */
class StreamingPlayer {
    private String movie;
    
    public void on() {
        System.out.println("Streaming Player is ON");
    }
    
    public void off() {
        System.out.println("Streaming Player is OFF");
    }
    
    public void play(String movie) {
        this.movie = movie;
        System.out.println("Streaming Player playing \"" + movie + "\"");
    }
    
    public void stop() {
        System.out.println("Streaming Player stopped \"" + movie + "\"");
    }
    
    public void pause() {
        System.out.println("Streaming Player paused \"" + movie + "\"");
    }
}

/**
 * Subsystem: Projector
 */
class Projector {
    public void on() {
        System.out.println("Projector is ON");
    }
    
    public void off() {
        System.out.println("Projector is OFF");
    }
    
    public void wideScreenMode() {
        System.out.println("Projector in widescreen mode (16:9 aspect ratio)");
    }
    
    public void tvMode() {
        System.out.println("Projector in TV mode (4:3 aspect ratio)");
    }
}

/**
 * Subsystem: Screen
 */
class Screen {
    public void up() {
        System.out.println("Screen is going UP");
    }
    
    public void down() {
        System.out.println("Screen is going DOWN");
    }
}

/**
 * Subsystem: TheaterLights
 */
class TheaterLights {
    private int brightness;
    
    public void on() {
        brightness = 100;
        System.out.println("Theater Lights are ON");
    }
    
    public void off() {
        brightness = 0;
        System.out.println("Theater Lights are OFF");
    }
    
    public void dim(int level) {
        brightness = level;
        System.out.println("Theater Lights dimming to " + level + "%");
    }
}

/**
 * Subsystem: PopcornPopper
 */
class PopcornPopper {
    public void on() {
        System.out.println("Popcorn Popper is ON");
    }
    
    public void off() {
        System.out.println("Popcorn Popper is OFF");
    }
    
    public void pop() {
        System.out.println("Popcorn Popper is popping popcorn!");
    }
}
