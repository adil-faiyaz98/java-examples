/**
 * I/O Decorator Example
 * 
 * This example demonstrates a more practical use of the Decorator pattern
 * by implementing a simple I/O framework similar to Java's I/O classes.
 * 
 * It shows how decorators can be used to add functionality like buffering,
 * encryption, and compression to basic I/O operations.
 */
package design.patterns.decorator;

import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class IODecoratorExample {
    
    public static void main(String[] args) {
        // Sample data to process
        String data = "This is a test string that will be processed through various decorators.";
        System.out.println("Original data: " + data);
        System.out.println("Length: " + data.length() + " bytes");
        System.out.println();
        
        // Create a basic data source
        DataSource source = new StringDataSource(data);
        
        // Read from the basic source
        System.out.println("Reading from basic source:");
        System.out.println(source.read());
        System.out.println();
        
        // Add encryption
        System.out.println("Reading from encrypted source:");
        DataSource encryptedSource = new EncryptionDecorator(new StringDataSource(data));
        String encryptedData = encryptedSource.read();
        System.out.println(encryptedData);
        System.out.println("Length: " + encryptedData.length() + " bytes");
        System.out.println();
        
        // Add compression
        System.out.println("Reading from compressed source:");
        DataSource compressedSource = new CompressionDecorator(new StringDataSource(data));
        String compressedData = compressedSource.read();
        System.out.println(compressedData);
        System.out.println("Length: " + compressedData.length() + " bytes");
        System.out.println();
        
        // Combine decorators: compression + encryption
        System.out.println("Reading from compressed and encrypted source:");
        DataSource compressedEncryptedSource = new EncryptionDecorator(
                new CompressionDecorator(new StringDataSource(data)));
        String compressedEncryptedData = compressedEncryptedSource.read();
        System.out.println(compressedEncryptedData);
        System.out.println("Length: " + compressedEncryptedData.length() + " bytes");
        System.out.println();
        
        // Writing with decorators
        System.out.println("Writing with decorators:");
        String newData = "This is new data to be written with decorators.";
        
        // Create a writable data source
        WritableDataSource writableSource = new FileDataSource("test.txt");
        
        // Add buffering and encryption for writing
        WritableDataSource bufferedEncryptedSource = new BufferedWriteDecorator(
                new EncryptionDecorator(writableSource));
        
        // Write the data
        bufferedEncryptedSource.write(newData);
        System.out.println("Data written successfully with buffering and encryption.");
    }
}

/**
 * Component: Basic interface for reading data
 */
interface DataSource {
    String read();
}

/**
 * Extended Component: Interface for reading and writing data
 */
interface WritableDataSource extends DataSource {
    void write(String data);
}

/**
 * Concrete Component: String-based data source
 */
class StringDataSource implements DataSource {
    private String data;
    
    public StringDataSource(String data) {
        this.data = data;
    }
    
    @Override
    public String read() {
        return data;
    }
}

/**
 * Concrete Component: File-based data source
 */
class FileDataSource implements WritableDataSource {
    private String filename;
    private String data;
    
    public FileDataSource(String filename) {
        this.filename = filename;
    }
    
    @Override
    public String read() {
        // In a real implementation, this would read from a file
        System.out.println("Reading from file: " + filename);
        return data;
    }
    
    @Override
    public void write(String data) {
        // In a real implementation, this would write to a file
        System.out.println("Writing to file: " + filename);
        this.data = data;
    }
}

/**
 * Decorator: Base decorator for DataSource
 */
abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;
    
    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }
    
    @Override
    public String read() {
        return wrappee.read();
    }
}

/**
 * Decorator: Base decorator for WritableDataSource
 */
abstract class WritableDataSourceDecorator implements WritableDataSource {
    protected WritableDataSource wrappee;
    
    public WritableDataSourceDecorator(WritableDataSource source) {
        this.wrappee = source;
    }
    
    @Override
    public String read() {
        return wrappee.read();
    }
    
    @Override
    public void write(String data) {
        wrappee.write(data);
    }
}

/**
 * Concrete Decorator: Adds encryption/decryption
 */
class EncryptionDecorator extends DataSourceDecorator implements WritableDataSource {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }
    
    public EncryptionDecorator(WritableDataSource source) {
        super(source);
        this.wrappee = source;
    }
    
    @Override
    public String read() {
        // Decrypt the data
        return decrypt(wrappee.read());
    }
    
    @Override
    public void write(String data) {
        // Encrypt the data before writing
        ((WritableDataSource) wrappee).write(encrypt(data));
    }
    
    private String encrypt(String data) {
        // Simple Base64 encoding for demonstration
        System.out.println("Encrypting data...");
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    
    private String decrypt(String data) {
        // Simple Base64 decoding for demonstration
        try {
            System.out.println("Decrypting data...");
            return new String(Base64.getDecoder().decode(data));
        } catch (IllegalArgumentException e) {
            // If the data is not encrypted, return as is
            return data;
        }
    }
}

/**
 * Concrete Decorator: Adds compression/decompression
 */
class CompressionDecorator extends DataSourceDecorator implements WritableDataSource {
    public CompressionDecorator(DataSource source) {
        super(source);
    }
    
    public CompressionDecorator(WritableDataSource source) {
        super(source);
        this.wrappee = source;
    }
    
    @Override
    public String read() {
        // Decompress the data
        return decompress(wrappee.read());
    }
    
    @Override
    public void write(String data) {
        // Compress the data before writing
        ((WritableDataSource) wrappee).write(compress(data));
    }
    
    private String compress(String data) {
        // Simple compression simulation using Base64 for demonstration
        System.out.println("Compressing data...");
        byte[] input = data.getBytes();
        
        // In a real implementation, this would use actual compression
        // For demonstration, we'll just encode it to simulate compression
        return "COMPRESSED:" + Base64.getEncoder().encodeToString(input);
    }
    
    private String decompress(String data) {
        // Simple decompression simulation for demonstration
        try {
            System.out.println("Decompressing data...");
            if (data.startsWith("COMPRESSED:")) {
                String compressedData = data.substring("COMPRESSED:".length());
                return new String(Base64.getDecoder().decode(compressedData));
            }
            return data;
        } catch (Exception e) {
            // If the data is not compressed, return as is
            return data;
        }
    }
}

/**
 * Concrete Decorator: Adds buffering for write operations
 */
class BufferedWriteDecorator extends WritableDataSourceDecorator {
    private static final int BUFFER_SIZE = 1024;
    private StringBuilder buffer;
    
    public BufferedWriteDecorator(WritableDataSource source) {
        super(source);
        this.buffer = new StringBuilder(BUFFER_SIZE);
    }
    
    @Override
    public void write(String data) {
        // Add data to buffer
        buffer.append(data);
        
        // If buffer is full, flush it
        if (buffer.length() >= BUFFER_SIZE) {
            flush();
        }
        
        System.out.println("Data added to buffer. Buffer size: " + buffer.length());
    }
    
    public void flush() {
        System.out.println("Flushing buffer...");
        wrappee.write(buffer.toString());
        buffer.setLength(0);
    }
    
    // Additional method to ensure all data is written when done
    public void close() {
        if (buffer.length() > 0) {
            flush();
        }
    }
}
