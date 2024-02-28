import java.util.Random;

public class RandomDevice implements Device {
    private final Random[] devices = new Random[10];

    @Override
    public int Open(String s) {
        int index = -1;
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] == null) {
                index = i;
                if (s != null && !s.isEmpty()) {
                    try {
                        long seed = Long.parseLong(s);
                        devices[i] = new Random(seed);
                    } catch (NumberFormatException e) {
                        devices[i] = new Random(); // Default constructor if conversion fails
                    }
                } else {
                    devices[i] = new Random();
                }
                break;
            }
        }
        return index; // Return -1 if no empty spot is found
    }

    @Override
    public void Close(int id) {
        if (id >= 0 && id < devices.length) {
            devices[id] = null;
        }
    }

    @Override
    public byte[] Read(int id, int size) {
        if (id >= 0 && id < devices.length && devices[id] != null && size > 0) {
            byte[] buffer = new byte[size];
            devices[id].nextBytes(buffer);
            return buffer;
        }
        return new byte[0]; // Return empty array if conditions are not met
    }

    @Override
    public void Seek(int id, int to) {
        // Reading random bytes but not returning them
        if (id >= 0 && id < devices.length && devices[id] != null && to > 0) {
            byte[] buffer = new byte[to];
            devices[id].nextBytes(buffer);
            // Essentially discards the bytes, mimicking a seek operation
        }
    }

    @Override
    public int Write(int id, byte[] data) {
        // Write operation does not make sense for RandomDevice, so it does nothing
        return 0; // Return 0 length, indicating no data was written
    }
}
