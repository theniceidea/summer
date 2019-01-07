package org.summerframework.core.srv;

import javassist.*;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.summerframework.model.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ClassRewriter {
    private static Logger logger = Logger.getLogger(ClassRewriter.class.getName());

    protected static HashSet<Class<?>> SummerModelClasses = new HashSet<>();

    private static ClassPool CLASS_POOL = null;
    static {
        CLASS_POOL = ClassPool.getDefault();
        ClassClassPath classClassPath = new ClassClassPath(ClassRewriter.class);
        CLASS_POOL.insertClassPath(classClassPath);
    }

    public static void rewrite(String ... scanSummerPackages){
        ClassRewriter classRewriter = new ClassRewriter();
        List<ScanSummerItem> summers = classRewriter.scanSummers(scanSummerPackages);
        logger.info("======rewrite summer=================================================");
        summers.forEach(classRewriter::rewriteClass);
        logger.info("======rewrite summer=================================================");
    }

    private void rewriteClass(ScanSummerItem scanSummerItem) {
        try {
            rewriteClass_(scanSummerItem);
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
    private void rewriteClass_(ScanSummerItem scanSummerItem) throws NotFoundException, CannotCompileException {

        String kls = scanSummerItem.getClassName();


        CtClass summerClass = CLASS_POOL.get(kls);
        CtClass[] interfaces = summerClass.getInterfaces();
        for(CtClass face : interfaces){
            if(SkipRewrite.class.getName().equals(face.getName())) {
                logger.info("rewrite summer class:"+summerClass.getName()+"; skiped");
                return;
            }
        }

        String codeServiceField = "private static " + SummerServiceBean.class.getName() + " SERVICE = "+UnInstallSummerServiceBean.class.getName()+".Instance;";
        CtField field = CtField.make(codeServiceField, summerClass);
        summerClass.addField(field);

        String codeSumMethod;
        if(scanSummerItem.isAsync()) {
            codeSumMethod = "public Object sum(){ try{ SERVICE.sum(this); }catch(RuntimeException e){ this.fireException(e); } return null;}";
        }else{
            codeSumMethod = "public Object sum(){ SERVICE.sum(this); return this.getSummerResult(); }";
        }
//        codeSumMethod = "public Object sum(){ return this.getSummerResult();}";
        CtMethod method = CtMethod.make(codeSumMethod, summerClass);

        try {
            CtMethod sum = summerClass.getDeclaredMethod("sum", new CtClass[]{});
            if(nonNull(sum)){
                throw new RuntimeException("Summer class 的子类 不允许Override sum() 方法, 当前类:"+kls);
            }
        }catch (NotFoundException e){ }

        summerClass.addMethod(method);

        ClassLoader classLoader = ClassRewriter.class.getClassLoader();
        ProtectionDomain protectionDomain = ClassRewriter.class.getProtectionDomain();
        Class newCls = summerClass.toClass(classLoader, protectionDomain);
        SummerModelClasses.add(newCls);
        logger.info("rewrite summer class:"+summerClass.getName()+"; classLoader:"+classLoader);
    }
    private void scanPackageClass(String basePackage, Consumer<ClassMeta> consumer) {
        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            String DEFAULT_RESOURCE_PATTERN = "**/*.class";
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
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
    private List<ScanSummerItem> scanSummers(String ... scanSummerPackages){
        List<String> pkgList = new ArrayList<>(Arrays.asList(scanSummerPackages));
        pkgList.add("org.summerframework");
        if(pkgList.size() <= 0) pkgList.add("");

        HashMap<String, String> classMap = new HashMap<>();
        List<String> classes = new ArrayList<>();

        pkgList.forEach(pkg->{
            scanPackageClass(pkg, classMetadata -> {
                classMap.put(classMetadata.getClassName(), classMetadata.getSuperName());
            });
        });

        Set<String> asyncs = new HashSet<>();
        asyncs.add(AsyncSummer.class.getName());

        Set<String> parentClasses = new HashSet<>();
        parentClasses.add(Summer.class.getName());
        while(parentClasses.size()>0){

            Set<String> finalParentClasses = parentClasses;
            Set<String> subClasses = classMap.entrySet()
                .stream()
                .filter(entry -> finalParentClasses.contains(entry.getValue()))
                .map(entry->{
                    if(asyncs.contains(entry.getValue())){
                        asyncs.add(entry.getKey());
                    }
                    return entry.getKey();
                })
                .collect(Collectors.toSet());
            if(!CollectionUtils.isEmpty(subClasses)) {
                classes.addAll(subClasses);
            }
            parentClasses = subClasses;
        }
        List<ScanSummerItem> collect = classes
            .stream()
            .map(s -> {
                ScanSummerItem item = new ScanSummerItem();
                item.setClassName(s);
                item.setAsync(asyncs.contains(s));
                return item;
            })
            .collect(Collectors.toList());
        return collect;
    }

    private static class ScanSummerItem{
        private boolean async;
        private String className;

        public boolean isAsync() {
            return async;
        }

        public void setAsync(boolean async) {
            this.async = async;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }
}
