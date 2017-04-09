package guitests;

import org.junit.Test;

public class ChangeStoragePathCommandTest extends ToDoListGuiTest {
    @Test
    public void selectTask_emptyList() {
        changeStoragePath("");
    }

    public void changeStoragePath(String path) {
        commandBox.runCommand("exportsave " + path);
        assertResultMessage("Storage path changed to: " + path + "/todolist.xml");
    }
}