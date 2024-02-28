import java.util.ArrayList;
import java.util.List;

public class VFS implements Device {
    private List<DeviceMapping> mappings = new ArrayList<>();
    private RandomDevice randomDevice = new RandomDevice(); // Assuming you have this device implemented
    private FakeFileSystem fakeFileSystem = new FakeFileSystem(); // Assuming you have this device implemented

    @Override
    public int Open(String input) {
        String[] parts = input.split(" ", 2);
        String deviceName = parts[0];
        String deviceInput = parts.length > 1 ? parts[1] : "";

        Device device;
        switch (deviceName.toLowerCase()) {
            case "random":
                device = randomDevice;
                break;
            case "file":
                device = fakeFileSystem;
                break;
            default:
                throw new IllegalArgumentException("Unsupported device type: " + deviceName);
        }

        int deviceSpecificId = device.Open(deviceInput);
        DeviceMapping mapping = new DeviceMapping(device, deviceSpecificId);
        mappings.add(mapping);

        return mappings.size() - 1; // Return the index of the new mapping as the VFS ID
    }

    @Override
    public void Close(int vfsId) {
        if (vfsId >= 0 && vfsId < mappings.size()) {
            DeviceMapping mapping = mappings.get(vfsId);
            mapping.device.Close(mapping.deviceId);
            mappings.set(vfsId, null); // Remove the mapping
        }
    }

    @Override
    public byte[] Read(int vfsId, int size) {
        if (vfsId >= 0 && vfsId < mappings.size()) {
            DeviceMapping mapping = mappings.get(vfsId);
            return mapping.device.Read(mapping.deviceId, size);
        }
        return new byte[0];
    }

    @Override
    public void Seek(int vfsId, int to) {
        if (vfsId >= 0 && vfsId < mappings.size()) {
            DeviceMapping mapping = mappings.get(vfsId);
            mapping.device.Seek(mapping.deviceId, to);
        }
    }

    @Override
    public int Write(int vfsId, byte[] data) {
        if (vfsId >= 0 && vfsId < mappings.size()) {
            DeviceMapping mapping = mappings.get(vfsId);
            return mapping.device.Write(mapping.deviceId, data);
        }
        return 0;
    }
}
