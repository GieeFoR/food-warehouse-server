package foodwarehouse.startup;

import foodwarehouse.core.data.storage.Storage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class StoragesRunningOutOfSpace {

    private final static List<Storage> runningOutOfSpace = new LinkedList<>();

    public static void storeRunningOutOfSpace(List<Storage> storages) {
        if(storages == null) return;
        runningOutOfSpace.addAll(storages);
    }

    public static void removeRunningOutOfSpace(Storage storage) {
        runningOutOfSpace.remove(storage);
    }

    public static List<Storage> getRunningOutOfSpace() {
        return Collections.unmodifiableList(runningOutOfSpace);
    }

    public static void clear() {
        runningOutOfSpace.clear();
    }
}
