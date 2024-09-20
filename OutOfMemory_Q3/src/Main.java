import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
            System.out.println("Running OutOfMemory with no GC...");
            causeOutOfMemoryWithNoGC();//commment out this to execute runningout of memory with GC

            System.out.println("Running OutOfMemory with GC...");
            causeOutOfMemoryWithGC();
        }

        private static void causeOutOfMemoryWithNoGC () {
            List<StringBuffer> buffers = new ArrayList<>();
            final int MAX_BUFFERS = 10000; // Limit the number of buffers
            final int APPEND_SIZE = 1000000; // Size of data to append

            try {
                for (int i = 0; i < MAX_BUFFERS; i++) {
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < APPEND_SIZE; j++) {
                        buffer.append("a");
                    }
                    buffers.add(buffer);

                    // Print status to observe progress
                    if (buffers.size() % 500 == 0) {
                        System.out.println("Number of StringBuffer objects: " + buffers.size());
                        buffers.clear(); // Clear the list to release memory
                    }
                }
            } catch (OutOfMemoryError e) {
                System.err.println("OutOfMemoryError occurred without GC: " + e.getMessage());
            }
        }

        private static void causeOutOfMemoryWithGC () {
            List<StringBuffer> buffers = new ArrayList<>();
            final int MAX_BUFFERS = 10000; // Limit the number of buffers
            final int APPEND_SIZE = 1000000; // Size of data to append

            try {
                for (int i = 0; i < MAX_BUFFERS; i++) {
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < APPEND_SIZE; j++) {
                        buffer.append("a");
                    }
                    buffers.add(buffer);

                    // Periodically clear memory and suggest garbage collection
                    if (buffers.size() % 500 == 0) {
                        System.out.println("Number of StringBuffer objects: " + buffers.size());
                        buffers.clear(); // Clear the list to release memory

                        // Suggest garbage collection (this does not guarantee GC happens)
                        System.gc();
                        System.out.println("Garbage collection suggested...");
                    }
                }
            } catch (OutOfMemoryError e) {
                System.err.println("OutOfMemoryError occurred with GC: " + e.getMessage());
            }
        }
    }
