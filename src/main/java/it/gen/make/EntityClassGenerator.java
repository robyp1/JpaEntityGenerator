package it.gen.make;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import it.gen.formats.FieldType;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class EntityClassGenerator {

    private final static Logger logger = LoggerFactory.getLogger(EntityClassGenerator.class);

    private static Configuration config;
    private String classname;
    private List<FieldType> fieldList;

    public EntityClassGenerator( String classname, List<FieldType> fieldList, Configuration config) {
        this.classname = classname;
        this.fieldList = fieldList;
        this.config = config;

    }

    public void makeClass(String packageName){
        ListIterator<FieldType> fieldTypeListIterator = fieldList.listIterator();
        MethodSpec defaultConstructor =  MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
        TypeSpec.Builder entityclassBuilder = TypeSpec.classBuilder(classname)
                .addJavadoc("TODO: Add missing implementazione Overriding of hashCode and equals using IDE Actions Code Generation..")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Entity.class).build())
                .addAnnotation(AnnotationSpec.builder(Table.class).addMember("name", "$S", classname.toUpperCase()).build())
                .addMethod(defaultConstructor);
        MethodSpec.Builder constructorBuilder = null;
        MethodSpec.Builder toStringBuilder = null;
        logger.info(" Waiting.. ");
        while (fieldTypeListIterator.hasNext()) {
            FieldType field = fieldTypeListIterator.next();
            if (field.getFieldType() != null) {
                addField(entityclassBuilder, field);
                constructorBuilder = addToConstructor(constructorBuilder, field);
                String fieldName = field.getFieldName();
                makeSetter(entityclassBuilder, field.getFieldType(), fieldName);
                makeGetter(entityclassBuilder, field.getFieldType(), fieldName, field.getColumnName());
                toStringBuilder = addAndOverrideToString(toStringBuilder, field, !fieldTypeListIterator.hasNext());
            }
            else {
                logger.info(String.format("Couldn't create field %s, beacuse type is null!",field.getFieldType()));
            }

        }
        entityclassBuilder.addMethod(constructorBuilder.build());
        entityclassBuilder.addMethod(toStringBuilder.addStatement("return resultString.toString()").build());
        entityclassBuilder.build();
        TypeSpec entityclass = entityclassBuilder.build();
        //write source file to destination
        JavaFile javaFile = JavaFile.builder(packageName, entityclass)
                    .build();
        try {
            String targetDestinationSourceDir = config.getString("SOURCE_PATH_OUTPUT");
            javaFile.writeTo(new File(targetDestinationSourceDir));
            logger.info("End Writing class " + entityclass.name);
        } catch (IOException e) {
            logger.error("Error writing class " + entityclass.name, e);
        }

    }

    private MethodSpec.Builder addAndOverrideToString(MethodSpec.Builder cb, FieldType field, boolean end) {
        if (cb==null){
            cb = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(String.class)
                .addStatement("StringBuilder resultString = new StringBuilder($S).append($S).append($S).append($S).append($N).append($S)",  classname, "{", String.valueOf(field.getFieldName()), "=",  field.getFieldName(),"," );
        }
        else if(end){
            cb.addStatement("resultString.append($S).append($S).append($N).append($S)", String.valueOf(field.getFieldName()), "=", field.getFieldName(), "}");
        }
        else {
            cb.addStatement("resultString.append($S).append($S).append($N).append($S)", String.valueOf(field.getFieldName()), "=", field.getFieldName(), ",");
        }
        return cb;

    }

    private MethodSpec.Builder addToConstructor(MethodSpec.Builder cb, FieldType field) {
        if (cb==null){
            cb = MethodSpec.constructorBuilder();
        }
        return cb
                .addModifiers(Modifier.PUBLIC)
                .addParameter(field.getFieldType(), field.getFieldName())
                .addStatement("this.$N=$N", field.getFieldName(), field.getFieldName());
    }

    private void addField(TypeSpec.Builder entityclassBuilder, FieldType field) {
        entityclassBuilder.addField(field.getFieldType(), field.getFieldName(), Modifier.PRIVATE).build();
    }

    private void makeGetter(TypeSpec.Builder entityclassBuilder, Class fieldType, String fieldName, String colname) {
        entityclassBuilder.addMethod(MethodSpec.methodBuilder("get" + upperCaseFirstChar(fieldName))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Column.class).addMember("name", "$S", colname).build())
                .addStatement("return $N", fieldName)
                .returns(fieldType)
                .build());
    }


    private void makeSetter(TypeSpec.Builder entityclassBuilder, Class fieldType, String fieldName) {

        entityclassBuilder.addMethod(MethodSpec.methodBuilder("set" + upperCaseFirstChar(fieldName))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(fieldType, fieldName)
                .addStatement("this.$N=$N", fieldName, fieldName )
                .build());
    }

    private String upperCaseFirstChar(String fieldName) {
        return fieldName.substring(0,1).toUpperCase().concat(fieldName.substring(1));
    }

}
