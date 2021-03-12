package src.mvc;

//import tools.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model extends Bean {

    private Boolean unsavedChanges = false;
    private String fileName = null;

    public abstract void changed();

}
