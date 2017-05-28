package org.ciencialabart.wordchainresolver.context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DictionaryLoader implements Supplier<List<String>> {

    public static final String USER_DIR_SYSTEM_PROPERTY_NAME = "user.dir";
    
    private final String dictionaryFilePath;
    
    public DictionaryLoader(String dictionaryFileUserDirRelativePath) {
        dictionaryFilePath = System.getProperty(USER_DIR_SYSTEM_PROPERTY_NAME) + 
        dictionaryFileUserDirRelativePath;
    }

    @Override
    public List<String> get() {
        List<String> dictionary = null;
        
        try {
            dictionary = Files.readAllLines(Paths.get(dictionaryFilePath));
            
            Collections.sort(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        return dictionary;
    }

}
