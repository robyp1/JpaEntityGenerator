package it.gen;


import it.gen.make.StrategyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws URISyntaxException {
        Path pathFiles = Paths.get(Main.class.getResource("/").toURI());
        logger.info("Finding files in path" + pathFiles.toString());
        new StrategyGenerator(pathFiles,"com.example.jpaclass");
    }
}
