package org.summerframework.core.srv;

import javassist.*;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ClassRewriter {

    protected static HashSet<Class<?>> SummerModelClasses = new HashSet<>();

    public void rewrite(String ... scanSummerPackages){
        List<String> summers = scanSummers(scanSummerPackages);
        summers.forEach(this::rewriteClass);
    }

    private void rewriteClass(String kls) {
        try {
            rewriteClass_(kls);
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
    private void rewriteClass_(String kls) throws NotFoundException, CannotCompileException {

        ClassPool classPool = ClassPool.getDefault();
        CtClass summerServiceBeanClass = classPool.get(SummerServiceBean.class.getName());
        CtClass summerClass = classPool.get(kls);
        CtClass subClass = summerClass;

        String code = "private static " + SummerServiceBean.class.getName() + " service_______summer;";
//        String code = "private static int service$$;";
        CtField field = CtField.make(code, subClass);
//        CtField field = new CtField(summerServiceBeanClass, "service_______summer", subClass);
//        field.setModifiers(Modifier.STATIC);
        subClass.addField(field);

        code = "public Object sum(){ service_______summer.sum(this); return this.getResult();}";
//        code = "public Object sum(){ return this.getResult();}";
        CtMethod method = CtMethod.make(code, subClass);

//        CtMethod method = CtNewMethod.make(Modifier.STATIC|Modifier.PUBLIC, )
        try {
            CtMethod sum = subClass.getDeclaredMethod("sum", new CtClass[]{});
            if(nonNull(sum)){
                throw new RuntimeException("Summer class 的子类 不允许Override sum() 方法, 当前类:"+kls);
            }
        }catch (NotFoundException e){ }

        subClass.addMethod(method);

        SummerModelClasses.add(subClass.toClass());
    }
    private void scanPackageClass(String basePackage, Consumer<ClassMeta> consumer) {
        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            SimpleMetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(resourcePatternResolver);
            String DEFAULT_RESOURCE_PATTERN = "**/*.class";
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                //检查resource，这里的resource都是class
                if (resource.isReadable()) {
                    try(BufferedInputStream is = new BufferedInputStream(resource.getInputStream())){
                        ClassReader classReader = new ClassReader(is);
                        ClassMeta classMeta = new ClassMeta();
                        classMeta.setClassName(convertResourcePathToClassName(classReader.getClassName()));
                        classMeta.setSuperName(convertResourcePathToClassName(classReader.getSuperName()));
                        consumer.accept(classMeta);
                    }
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static String convertResourcePathToClassName(String resourcePath) {
        if(StringUtils.isEmpty(resourcePath)) return "";
        return resourcePath.replace('/', '.');
    }
    private List<String> scanSummers(String ... scanSummerPackages){
        List<String> pkgList = new ArrayList<>(Arrays.asList(scanSummerPackages));
        if(pkgList.size() <= 0) pkgList.add("");

        HashMap<String, String> classMap = new HashMap<>();
        List<String> classes = new ArrayList<>();

        pkgList.forEach(pkg->{
            scanPackageClass(pkg, classMetadata -> {
                classMap.put(classMetadata.getClassName(), classMetadata.getSuperName());
            });
        });

        Set<String> parentClasses = new HashSet<>();
        parentClasses.add(Summer.class.getName());
        while(parentClasses.size()>0){

            Set<String> finalParentClasses = parentClasses;
            Set<String> subClasses = classMap.entrySet()
                .stream()
                .filter(entry -> finalParentClasses.contains(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
            if(!CollectionUtils.isEmpty(subClasses)) {
                classes.addAll(subClasses);
            }

            parentClasses = subClasses;
        }
        return classes;
    }
}
