package mvc;

//import tools.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model extends Bean {

    private Boolean unsavedChanges = false;
    private String fileName = null;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String newFileName) {
        fileName = newFileName;
    }

    public void changed() {
        unsavedChanges = true;
        firePropertyChange("unsavedChanges", false, true);
    }

    public void saved() {
        unsavedChanges = false;
    }

    public Boolean isSaved() {
        return !unsavedChanges;
    }

}
