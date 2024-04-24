package Assignment_4_Onward;


public class VirtualToPhysicalMapping {
    public int physicalPageNumber;
    public int diskPageNumber;

    public VirtualToPhysicalMapping() {
        this.physicalPageNumber = -1; // No physical mapping initially
        this.diskPageNumber = -1; // No disk mapping initially
    }
}
