package org.summerframework.core.srv;

import com.sun.org.apache.bcel.internal.classfile.AccessFlags;
import javassist.*;
import javassist.bytecode.FieldInfo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.summerframework.model.ExcludeStrategyClass;
import org.summerframework.model.SummerService;
import org.summerframework.core.base.*;
import org.summerframework.model.Summer;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.summerframework.model.SummerServiceBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
class ServiceInit implements ApplicationContextAware{

    private static HashMap<Class<?>, Class<?>> MakedModelClasses = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> excludes = excludePackages(applicationContext);
        List<ExcludeStrategy> strategyBeans = getStrategyBeans(applicationContext);

        String[] names = applicationContext.getBeanNamesForAnnotation(SummerService.class);
        for(int i=0; i<names.length; i++){
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();
            SummerService annotation = targetClass.getAnnotation(SummerService.class);
            if(!annotation.value()) continue;

            HashMap<Class<?>, Method> map = getAopMethodsMap(bean);

            Method[] methods = targetClass.getMethods();
            for(int j=0; j<methods.length; j++){
                Method method = methods[j];
                if(!isSummerStandardMethod(method)) continue;

                SummerService summerService = method.getAnnotation(SummerService.class);
                if(nonNull(summerService) && !summerService.value()) continue;
                Class<?>[] types = method.getParameterTypes();
//                if(types.length != 1) continue;
                Method method1 = map.get(types[0]);
                if(null == method1) continue;
                if(isExclude(excludes, targetClass)) continue;
                if(isExclude(strategyBeans, targetClass, method.getName())) continue;

                install((Class<? extends Summer>) types[0], targetClass, bean, method1);
//                Manager.register(types[0], new ServiceItemImpl(bean, method1));
            }
        }
    }
    private void install(Class<? extends Summer> modelClass, Class<?> beanClass, Object bean, Method method) {
        try {
            Class<?> makeClass = makeClass(modelClass);
            Field service = makeClass.getDeclaredField("service_______summer");
            service.setAccessible(true);
            if(SummerServiceBean.class.isAssignableFrom(beanClass) && "sum".equals(method.getName())){
                Type[] genericInterfaces = beanClass.getGenericInterfaces();
                for(Type face : genericInterfaces){
                    ParameterizedType ptype =  (ParameterizedType)face;
                    if(!ptype.getRawType().equals(SummerServiceBean.class)){ continue;}
                    if(ptype.getActualTypeArguments()[0].equals(modelClass)){
                        service.set(null, bean);
                        return;
                    }
                }
            }
            service.set(null, new MethodService(bean, method));
        } catch (NoSuchFieldException | IllegalAccessException | NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }

    }
    private void install2(Class<?> modelClass, Class<?> beanClass, Object bean, Method method){
        try {
            Field service = modelClass.getDeclaredField("service");
            service.setAccessible(true);
            if(SummerServiceBean.class.isAssignableFrom(beanClass) && "sum".equals(method.getName())){
                Type[] genericInterfaces = beanClass.getGenericInterfaces();
                for(Type face : genericInterfaces){
                    ParameterizedType ptype =  (ParameterizedType)face;
                    if(!ptype.getRawType().equals(SummerServiceBean.class)){ continue;}
                    if(ptype.getActualTypeArguments()[0].equals(modelClass)){
                        service.set(null, bean);
                        return;
                    }
                }
            }
            service.set(null, new MethodService(bean, method));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private Class<?> makeClass(Class<? extends Summer> kls) throws NotFoundException, CannotCompileException {
        scanPackageClass("");

        if(MakedModelClasses.containsKey(kls)) return MakedModelClasses.get(kls);

        Type type1 = ((ParameterizedType) kls.getGenericSuperclass()).getActualTypeArguments()[0];
        String type = ((ParameterizedType)type1).getRawType().getTypeName();
        ClassPool classPool = ClassPool.getDefault();
        CtClass summerServiceBeanClass = classPool.get(SummerServiceBean.class.getName());
        CtClass summerClass = classPool.get(kls.getName());

        CtClass subClass = classPool.makeClass(kls.getName() + "$$Summer", summerClass);
        String code = "private static " + SummerServiceBean.class.getName() + " service_______summer;";
//        String code = "private static int service$$;";
        CtField field = CtField.make(code, subClass);
//        CtField field = new CtField(summerServiceBeanClass, "service_______summer", subClass);
//        field.setModifiers(Modifier.STATIC);
        subClass.addField(field);

        code = "public " + type + " sum(){ service_______summer.sum(this); return this.getResult();}";
        CtMethod method = CtMethod.make(code, subClass);

//        CtMethod method = CtNewMethod.make(Modifier.STATIC|Modifier.PUBLIC, )

        subClass.addMethod(method);

        Class klass = subClass.toClass();
        MakedModelClasses.put(kls, klass);

        return klass;
    }
    private boolean isSummerStandardMethod(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(isNull(parameterTypes) || parameterTypes.length != 1) return false;
        Class<?> parameterType = parameterTypes[0];
        if(!Summer.class.isAssignableFrom(parameterType)) return false;
        return true;
    }
    private HashMap<Class<?>, Method> getAopMethodsMap(Object bean){
        Method[] methods = bean.getClass().getMethods();
        HashMap<Class<?>, Method> map = new HashMap<>();
        for(int j=0; j<methods.length; j++){
            Method method = methods[j];
            if(!isSummerStandardMethod(method)) continue;
            Class<?>[] types = method.getParameterTypes();
//            if(types.length != 1) continue;
            map.put(types[0], method);
        }
        return map;
    }
    private List<ExcludeStrategy> getStrategyBeans(ApplicationContext applicationContext){
        List<ExcludeStrategy> list = new ArrayList<>();
        String[] names = applicationContext.getBeanNamesForAnnotation(ExcludeStrategyClass.class);
        for(int i=0; i<names.length; i++) {
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            list.add((ExcludeStrategy) bean);
        }
        return list;
    }
    private boolean isExclude(List<ExcludeStrategy> strategies, Class<?> kls, String methodName){
        if(isNull(strategies)) return false;
        for(ExcludeStrategy strategy : strategies){
            if(strategy.isExclude(kls, methodName)) return true;
        }
        return false;
    }
    private boolean isExclude(Set<String> excludes, Class<?> kls){
        String name = kls.getName();
        for (String s : excludes) {
            if(name.startsWith(s)) return true;
        }
        return false;
    }

    private EnableSummer getEnableSummer(ApplicationContext applicationContext){
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSummer.class);
        Optional<Object> bean = beans.values()
            .stream()
            .findFirst();
        Class<?> targetClass = AopUtils.getTargetClass(bean.get());
        if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();

        return targetClass.getAnnotation(EnableSummer.class);
    }

    private Set<String> excludePackages(ApplicationContext applicationContext){
        HashSet<String> set = new HashSet<>();
        EnableSummer annotation = getEnableSummer(applicationContext);
        String[] strings = annotation.excludePackages();
        if(nonNull(strings)) {
            set.addAll(Arrays.asList(strings));
        }
        return set;
    }
    private  ArrayList<String> scanPackageClass(String basePackage) {
        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            String DEFAULT_RESOURCE_PATTERN = "**/*.class";
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
            ArrayList<String> returnList = new ArrayList<String>();
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                //检查resource，这里的resource都是class
                if (resource.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    if (metadataReader != null) {
                        ClassMetadata classMetadata = metadataReader.getClassMetadata();
                        String className = classMetadata.getClassName();
                        returnList.add(className);
                        if(true) continue;
                        String superClassName = classMetadata.getSuperClassName();
                        if(isNull(superClassName)) continue;
                        if(className.contains("TestModel2")){
                            String a = "";
                        }
                        if(superClassName.contains("Summer"))
                        returnList.add(className);
                    }
                }
            }
            return returnList;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
