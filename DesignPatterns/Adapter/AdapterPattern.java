/**
 * Adapter Design Pattern
 * 
 * Intent: Convert the interface of a class into another interface clients expect.
 * Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.
 * 
 * This example demonstrates both object adapter (using composition) and class adapter (using inheritance)
 * with a media player example that needs to play different audio formats.
 */
package design.patterns.adapter;

public class AdapterPattern {
    
    public static void main(String[] args) {
        // Using the MediaPlayer to play MP3 files (supported natively)
        MediaPlayer mediaPlayer = new AudioPlayer();
        mediaPlayer.play("mp3", "song.mp3");
        
        System.out.println("\n--- Using Object Adapter ---");
        
        // Using the MediaPlayer with adapters to play other formats
        mediaPlayer = new AudioPlayer();
        mediaPlayer.play("mp4", "movie.mp4");
        mediaPlayer.play("vlc", "video.vlc");
        mediaPlayer.play("wav", "audio.wav");
        
        System.out.println("\n--- Using Class Adapter ---");
        
        // Using class adapter for XML to JSON conversion
        XMLData xmlData = new XMLData("<data><name>John</name><age>30</age></data>");
        JSONDataAdapter adapter = new JSONDataAdapter(xmlData);
        
        // Client code works with the adapter using the target interface
        processJSONData(adapter);
        
        // Direct use of the adapter's methods
        System.out.println("\nJSON Data: " + adapter.getJSONData());
    }
    
    // Client code that works with JSON format
    private static void processJSONData(JSONData jsonData) {
        System.out.println("Processing JSON data: " + jsonData.getJSONData());
        System.out.println("JSON data length: " + jsonData.getJSONSize());
    }
}

/**
 * Target Interface: MediaPlayer
 * This is the interface that the client code expects to work with
 */
interface MediaPlayer {
    void play(String audioType, String fileName);
}

/**
 * Adaptee Interface: AdvancedMediaPlayer
 * This is the interface that needs to be adapted
 */
interface AdvancedMediaPlayer {
    void playVlc(String fileName);
    void playMp4(String fileName);
}

/**
 * Concrete Adaptee: VlcPlayer
 */
class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
    
    @Override
    public void playMp4(String fileName) {
        // Do nothing
    }
}

/**
 * Concrete Adaptee: Mp4Player
 */
class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        // Do nothing
    }
    
    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

/**
 * Object Adapter: MediaAdapter
 * Uses composition to adapt AdvancedMediaPlayer to MediaPlayer
 */
class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedMusicPlayer;
    
    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer = new Mp4Player();
        }
    }
    
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer.playMp4(fileName);
        }
    }
}

/**
 * Concrete Target: AudioPlayer
 * Uses the adapter to support additional formats
 */
class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    
    @Override
    public void play(String audioType, String fileName) {
        // Native support for mp3 format
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        }
        // Use adapter for other formats
        else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        }
        else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}

/**
 * Class Adapter Example: XML to JSON Conversion
 */

/**
 * Target Interface: JSONData
 */
interface JSONData {
    String getJSONData();
    int getJSONSize();
}

/**
 * Adaptee: XMLData
 */
class XMLData {
    private String xmlData;
    
    public XMLData(String xmlData) {
        this.xmlData = xmlData;
    }
    
    public String getXMLData() {
        return xmlData;
    }
    
    public int getXMLSize() {
        return xmlData.length();
    }
}

/**
 * Class Adapter: XMLToJSONAdapter
 * Uses inheritance to adapt XMLData to JSONData
 * Note: Java doesn't support multiple inheritance, so we extend XMLData and implement JSONData
 */
class JSONDataAdapter implements JSONData {
    private XMLData xmlData;
    
    public JSONDataAdapter(XMLData xmlData) {
        this.xmlData = xmlData;
    }
    
    @Override
    public String getJSONData() {
        // Convert XML to JSON (simplified for demonstration)
        String xml = xmlData.getXMLData();
        return convertXmlToJson(xml);
    }
    
    @Override
    public int getJSONSize() {
        // The JSON size might be different from XML size
        return getJSONData().length();
    }
    
    private String convertXmlToJson(String xml) {
        // This is a very simplified conversion for demonstration
        // In a real application, you would use a proper XML to JSON converter
        
        // Replace XML tags with JSON format
        String json = xml.replaceAll("<([^>]*)>([^<]*)</([^>]*)>", "\"$1\":\"$2\",");
        
        // Remove the last comma and wrap in braces
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1);
        }
        
        return "{" + json + "}";
    }
}
