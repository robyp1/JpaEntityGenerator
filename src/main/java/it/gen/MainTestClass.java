package it.gen;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import it.gen.formats.ColumnExtractor;

import javax.lang.model.element.Modifier;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainTestClass {


//TEST LIBRARY JAVA POET
    public static void main(String[] args){
        String colname = "nomeColonnaProprieta1";
        colname= ColumnExtractor.extractMatches(colname);
        System.out.println(colname);
        String s = "(Set)(Value)";
        Pattern compile = Pattern.compile(s);
        Matcher setValue = compile.matcher("SetValue");
        if (setValue.matches()){
            System.out.println(setValue.group(1));
            System.out.println(setValue.group(2));
        }
        if (setValue.lookingAt()){
            System.out.println(setValue.group(0));
        }

//        MethodSpec methodGet = MethodSpec.methodBuilder("getProrpieta1")
//                .addModifiers(Modifier.PUBLIC)
//                .addAnnotation(AnnotationSpec.builder(Column.class).addMember("name", "$S", colname).build())
//                .addStatement("return $N", "proprieta1")
//                .returns(String.class)
//                .build();
//        MethodSpec methodSet = MethodSpec.methodBuilder("setProrpieta1")
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(String.class, "proprieta1")
//                .addStatement("this.$N=$N", "proprieta1", "proprieta1" )
//                .build();
//        MethodSpec defaultConstructor = MethodSpec.constructorBuilder()
//                .addModifiers(Modifier.PUBLIC)
//                .build();
//        MethodSpec constructor = MethodSpec.constructorBuilder()
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(String.class, "proprieta1")
//                .addStatement("this.$N=$N", "proprieta1", "proprieta1" )
//                .build();
//        TypeSpec entityclass = TypeSpec.classBuilder("EntityClassExample")
//                .addAnnotation(AnnotationSpec.builder(Entity.class).build())
//                .addAnnotation(AnnotationSpec.builder(Table.class).addMember("name", "$S", "nomeTabella").build())
//                .addModifiers(Modifier.PUBLIC)
//                .addField(String.class, "proprieta1", Modifier.PRIVATE)
//                .addMethod(defaultConstructor)
//                .addMethod(constructor)
//                .addMethod(methodGet)
//                .addMethod(methodSet)
//                .build();
//        JavaFile javaFile = JavaFile.builder("com.example.jpaclass", entityclass)
//                .build();
//        try {
//            javaFile.writeTo(new File("C:\\programmiMio\\java\\JpaEntityGenerator\\JpaEntitiesGenerator\\src\\main\\java") );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
