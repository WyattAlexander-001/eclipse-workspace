package Assignment_4_Onward;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.io.IOException;

public class FakeFileSystem implements Device {
    private final RandomAccessFile[] files = new RandomAccessFile[10];

    @Override
    public int Open(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty or null");
        }
        
        int index = -1;
        for (int i = 0; i < files.length; i++) {
            if (files[i] == null) {
                try {
                    files[i] = new RandomAccessFile(filename, "rw");
                    index = i;
                    break;
                } catch (IOException e) {
                    e.printStackTrace(); 
                }
            }
        }
        return index; // Return -1 if no empty spot is found
    }

    @Override
    public void Close(int id) {
        if (id >= 0 && id < files.length && files[id] != null) {
            try {
                files[id].close();
                files[id] = null; // Clear the slot after closing the file
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }

    @Override
    public byte[] Read(int id, int size) {
        if (id >= 0 && id < files.length && files[id] != null && size > 0) {
            byte[] buffer = new byte[size];
            try {
                int bytesRead = files[id].read(buffer);
                if (bytesRead < size) {
                    // If fewer bytes were read than requested, copy the bytes read into a smaller buffer
                    return Arrays.copyOf(buffer, bytesRead);
                }
                return buffer;
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
        return new byte[0]; 
    }

    @Override
    public void Seek(int id, int to) {
        if (id >= 0 && id < files.length && files[id] != null) {
            try {
                files[id].seek(to);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }

    @Override
    public int Write(int id, byte[] data) {
        if (id >= 0 && id < files.length && files[id] != null && data != null) {
            try {
                files[id].write(data);
                return data.length;
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
        return 0; 
    }
}
