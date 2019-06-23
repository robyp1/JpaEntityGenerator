package it.gen.make;

import it.gen.exceptions.GenConfigException;
import it.gen.formats.ColumnExtractor;
import it.gen.formats.FieldType;
import it.gen.formats.SourceTypeEnum;
import it.gen.io.StrategyGenerationDelegate;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;

//not threadsafe
public class StrategyGenerator {

    private final static Logger logger = LoggerFactory.getLogger(StrategyGenerator.class);

    private static Configuration config;
    private EntityClassGenerator entityClassGenerator;

    static {
        Configurations configs = new Configurations();
        try {
            File fileproperties = new File(StrategyGenerator.class.getResource("/config.properties").toURI());
            config = configs.properties(fileproperties);

        } catch (ConfigurationException | URISyntaxException ex) {
            logger.error("Error loading configuration, config null", ex);
            ex.printStackTrace();
        }
    }


    public StrategyGenerator(Path folderFiles, String packageNameSourceDest) {
        StrategyGenerationDelegate selectData = new StrategyGenerationDelegate(folderFiles);
        List<String> classKeyList = selectData.getClassKeyList();//list of class names
        logger.info(" class sources will be generated in src package " + packageNameSourceDest);
        for (String classname : classKeyList) {//for every classname found ineach file classname_suffix.list
            try {
                List<Class> typeFieldList = convertToJavaType(selectData.getData(classname, SourceTypeEnum.TYPE_LIST));
                List<String> fieldList = selectData.getData(classname, SourceTypeEnum.FIELD_LIST);
                List<String> columnList = selectData.getData(classname, SourceTypeEnum.COLUMN_LIST);
                if (columnList.isEmpty()){
                    columnList = tryToFindColumnNamefrom(fieldList);
                }
                if (
                        !typeFieldList.isEmpty() && !fieldList.isEmpty() && !columnList.isEmpty() &&
                                typeFieldList.size() != fieldList.size() && columnList.size() != fieldList.size()) {
                    throw new GenConfigException("fieldList size != typeFieldList and columnList size != columnList.size," +
                            "checks file sizes must be the same"
                    + String.format(" typeFieldList.size=%d, fieldList.size=%d, columnList.size=%d",
                            typeFieldList.size(), fieldList.size(), columnList.size()));
                } else {
                    List<FieldType> fieldListList = new ArrayList<>();
                    for (int i = 0; i < fieldList.size(); i++) {
                        fieldListList.add(new FieldType(typeFieldList.get(i), fieldList.get(i), columnList.get(i)));
                    }
                    logger.info(" prepare writing class " + classname);
                    entityClassGenerator = new EntityClassGenerator(classname, fieldListList, config);
                    entityClassGenerator.makeClass(packageNameSourceDest);
                }
            }catch(Exception ex) {
                logger.error(ex.getMessage(), ex);

            } catch (GenConfigException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private List<String> tryToFindColumnNamefrom(List<String> fieldList) {
        List columnNames = new ArrayList();
        for (String fieldName: fieldList) {
            try {
                columnNames.add(ColumnExtractor.extractMatches(fieldName));
            }catch(Exception e){
                columnNames.add(fieldName.toUpperCase());
            }
        }
        return columnNames;
    }


    private List<Class> convertToJavaType(List<String> dataList) throws GenConfigException, ClassNotFoundException {
        List<Class> dataMappingToJavaTypes = new ArrayList();
        Class typeOf =null;
        for (String data : dataList){
            String dbtype = null;
            try {
                dbtype = config.getString(data.toUpperCase());
                if (dbtype == null) {
                    throw new GenConfigException(String.format(" %s convertion type is not found in config.properties", data.toUpperCase()));
                }
                typeOf = Class.forName(dbtype);
            }
            catch (GenConfigException ex){
                throw ex;
            }
            catch (NullPointerException nex) {
                typeOf = null;
                logger.error("Null pointer, configuration is " + config, nex);
                throw nex;
            }
            catch (ClassNotFoundException e) {
                typeOf = null;
                logger.error(String.format("db type %s not supported!", dbtype), e);
                throw e;
            }
            dataMappingToJavaTypes.add(typeOf);
        }
        return dataMappingToJavaTypes;
    }
}
