package it.gen.io;


import it.gen.formats.SourceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static it.gen.formats.SourceTypeEnum.*;

public class StrategyGenerationDelegate {

    private Map<String, FileSourceLoader> structureLoadingMap = new HashMap<>();
    private List<String> classKeyList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(StrategyGenerationDelegate.class);

    public StrategyGenerationDelegate(Path rootPath){
        mapFiles(rootPath);
    }

    private void mapFiles(Path rootPath){
        File rootFile = rootPath.toFile();
        if (rootFile.isDirectory()){
            try {
                Files.walkFileTree(rootPath, new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        String fileName = file.toFile().getName();
                        if (fileName.endsWith(".list")){
                            String classMapKey = fileName.substring(0, fileName.indexOf("_"));
                            if (fileName.endsWith(FIELD_LIST.getFileSuffix())){
                                structureLoadingMap.put(fileName, new FileSourceLoader(FIELD_LIST, file));
                            }
                            else if (fileName.endsWith(TYPE_LIST.getFileSuffix())){
                                structureLoadingMap.put(fileName, new FileSourceLoader(TYPE_LIST, file));
                            }
                            else if (fileName.endsWith(COLUMN_LIST.getFileSuffix())) {
                                structureLoadingMap.put(fileName, new FileSourceLoader(COLUMN_LIST, file));
                            }
                            if (!structureLoadingMap.containsKey(classMapKey)) {
                                    classKeyList.add(classMapKey);
                                    structureLoadingMap.put(classMapKey,null);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        return FileVisitResult.TERMINATE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                //todo: gestire o mettere log
            }

        }
        else {
            //todo: messaggio di errore
        }
    }

    public List<String> getData(String classNameKey,SourceTypeEnum fileType) {
        Map<SourceTypeEnum, List<String>> sourceTypeEnumListMap = new HashMap<>();
        String key = classNameKey +fileType.getFileSuffix();
        if (structureLoadingMap.containsKey(key)) {
            sourceTypeEnumListMap = structureLoadingMap.get(key).loadData();
        }
        else {
           logger.error(String.format("key %s not found in map type %s",key, fileType.getFileSuffix()));
        }
        return (sourceTypeEnumListMap.get(fileType)!=null ? sourceTypeEnumListMap.get(fileType) : Collections.emptyList());
    }

    public void setStructureLoadingMap(Map<String, FileSourceLoader> structureLoadingMap) {
        this.structureLoadingMap = structureLoadingMap;
    }

    public List<String> getClassKeyList() {
        return classKeyList;
    }
}
