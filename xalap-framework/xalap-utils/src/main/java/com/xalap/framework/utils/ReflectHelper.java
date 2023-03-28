/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class ReflectHelper {

    private static final String SETTER_METHOD_PREFIX = "set";
    private static final String GETTER_METHOD_PREFIX = "get";
    private static final String BOOLEAN_GETTER_METHOD_PREFIX = "is";

    /**
     * @return Убираем get, is из названия метода и все приводит к нижнему регистру
     */
    public static String getGetterFieldName(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith(BOOLEAN_GETTER_METHOD_PREFIX)) {
            return StringUtils.uncapitalize(methodName.substring(BOOLEAN_GETTER_METHOD_PREFIX.length()));
        } else if (methodName.startsWith(GETTER_METHOD_PREFIX)) {
            return StringUtils.uncapitalize(methodName.substring(GETTER_METHOD_PREFIX.length()));
        }
        throw new IllegalArgumentException("Method is not getter:" + methodName);
    }

    /**
     * @return true, если метод называется так как геттер
     */
    public static boolean isGetter(Method method) {
        String methodName = method.getName();
        return method.getParameterCount() == 0 &&
                (methodName.startsWith(BOOLEAN_GETTER_METHOD_PREFIX) || methodName.startsWith(GETTER_METHOD_PREFIX));
    }

    public static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(annotationClass)) {
                return ((T) annotation);
            }
        }
        return null;
    }

    public static Object executeMethod(Object object, String methodName, Class<?>[] params, Object[] args) {
        try {
            Method method = object.getClass().getMethod(methodName, params);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> Class<T> classForName(String name) {
        try {
            ClassLoader contextClassLoader = Thread.currentThread()
                    .getContextClassLoader();
            if (contextClassLoader != null) {
                return (Class<T>) contextClassLoader.loadClass(name);
            }
            return (Class<T>) Class.forName(name);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static <T> Class<T> getClass(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> Class<T> getClass(T object) {
        return (Class<T>) object.getClass();
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("Error on create instance of class:" + clazz, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getComponentType(T[] a) {
        Class<?> k = a.getClass().getComponentType();
        return (Class<? extends T>) k; // unchecked cast
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<? extends T> k, int size) {
        if (k.isPrimitive())
            throw new IllegalArgumentException("Argument cannot be primitive: "
                    + k);
        Object a = Array.newInstance(k, size);
        return (T[]) a; // unchecked cast
    }

    public static <T> T[] newArray(T[] a, int size) {
        return newArray(getComponentType(a), size);
    }

    public static String getObjectInfo(Object object)
            throws IllegalAccessException {
        // System.out.println("get object:"+object);
        StringBuilder buf = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(object);
            String valueText = getObjectCaption(value);
            buf.append(fieldName).append("=").append(valueText).append(",");
        }
        return buf.toString();
    }

    /**
     * @param clazz класс
     * @return является ли класс абстрактным
     */
    public static boolean isAbstract(Class clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    private static String getObjectCaption(Object object)
            throws IllegalAccessException {
        // System.out.println(object);
        if (object == null)
            return "null";
        if (object instanceof String || object instanceof Long
                || object instanceof Integer || object instanceof Boolean
                || object instanceof Double) {
            return object.toString();
        }
        if (object instanceof List) {
            List<?> list = (List<?>) object;
            StringBuilder buf = new StringBuilder();
            buf.append("\n");
            buf.append("[");
            for (Object aList : list) {
                buf.append(getObjectCaption(aList)).append(",");
            }
            buf.append("]");
            return buf.toString();
        }
        if (object instanceof Enum) {
            return object.toString();
        }
        return "{" + getObjectInfo(object) + "}";
    }

    /**
     * @return Название текущего выполняемого метода
     */
    public static String getCurrentMethodName() {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        return elements[0].getMethodName();
    }

    /**
     * @return Название текущего выполняемого класса
     */
    public static String getCurrentClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    /**
     * Получение текущего stack trace в виде строки
     */
    public static String getCurrentStackTrace() {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement element : elements) {
            buf.append(element.getClassName()).append(".").append(element.getMethodName()).append(":").append(element.getLineNumber()).append(",\n");
        }
        return buf.toString();
    }

    /**
     * @param clazz           класс который содержит метод(method)
     * @param method          метод для которого нужно найти аннотацию.
     * @param clazzAnnotation класс искомой аннотации
     * @param <A>             Класс конкретной искомой аннотации
     * @return вернёт аннотацию clazzAnnotation, если переданный метод имеет аннотацию или метод родительского класса имеет аннотацию и тд.
     * Если аннотация не найдена во всей ветке иерархии класса clazz, то вернёт null
     */
    public static <A extends Annotation> A getAnnotationByMethodAndClass(Class clazz, Method method, Class<A> clazzAnnotation) {
        String nameMethod = method.getName();
        Class[] parametrTypes = method.getParameterTypes();
        return getAnnotationByMethodNameAndClass(clazz, nameMethod, parametrTypes, clazzAnnotation);
    }

    /**
     * Метод ищёт аннотацию метода в текущем классе и его родительских классах и интерфейсах. Если не найдёт вернёт null.
     * Если в текущем классе метода нет, то попытается искать в родительском
     * (Например, может быть такая систуация - A<-B<-C в классе C есть метод, который переопрделяет метод класса A, но он не переопределён в B).
     *
     * @param clazz           класс которому принадлежит метод
     * @param methodName      имя метода для которого идёт поиск аннотаций
     * @param parameters      параметры метода
     * @param clazzAnnotation класс искомой аннотации
     * @param <A>             конкретный класс аннотации
     * @return вернёт искомую аннотацию, если аннотации нет то вернёт null
     */
    private static <A extends Annotation> A getAnnotationByMethodNameAndClass(Class clazz, String methodName, Class[] parameters, Class<A> clazzAnnotation) {
        boolean resultNotFound;
        A result = null;
        try {
            Method method = clazz.getMethod(methodName, parameters);

            result = method.getAnnotation(clazzAnnotation);

            resultNotFound = (result == null);
            if (resultNotFound) {
                result = searchMethodAnnotationInInterface(clazz, methodName, parameters, clazzAnnotation, result);
            }
        } catch (NoSuchMethodException e) {
            /*e.printStackTrace();*/
        }
        resultNotFound = (result == null);
        if (resultNotFound) {
            result = searchMethodAnnotationInSuperClass(clazz, methodName, parameters, clazzAnnotation, result);
        }
        return result;
    }

    private static <A extends Annotation> A searchMethodAnnotationInInterface(Class clazz, String methodName, Class[] parameters, Class<A> clazzAnnotation, A result) throws NoSuchMethodException {
        Class[] interfaces = clazz.getInterfaces();
        for (Class clazzInterface : interfaces) {
            result = getAnnotationByMethodNameAndClass(clazzInterface, methodName, parameters, clazzAnnotation);
            boolean resultFound = (result != null);
            if (resultFound) {
                break;
            }
        }
        return result;
    }

    private static <A extends Annotation> A searchMethodAnnotationInSuperClass(Class clazz, String methodName, Class[] parameters, Class<A> clazzAnnotation, A result) {
        Class superClazz = clazz.getSuperclass();
        boolean haveParentForSearch = (superClazz != null && superClazz != Object.class);
        if (haveParentForSearch) {
            result = getAnnotationByMethodNameAndClass(superClazz, methodName, parameters, clazzAnnotation);
        }
        return result;
    }

    public static List<Method> getInheritedMethods(Class<?> type) {
        List<Method> result = new ArrayList<>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            Collections.addAll(result, i.getDeclaredMethods());
            i = i.getSuperclass();
        }

        return result;
    }

    /**
     * Получаем список полей данного класса и его предков
     */
    public static List<Field> getInheritedPrivateFields(Class<?> type) {
        List<Field> result = new ArrayList<>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            Collections.addAll(result, i.getDeclaredFields());
            i = i.getSuperclass();
        }

        return result;
    }

    public static Optional<Method> findMethod(String methodName, Class<?> _class) {
        return getInheritedMethods(_class).stream()
                .filter(method -> method.getName().equalsIgnoreCase(methodName))
                .findFirst();
    }

    public static Optional<Method> getGetter(Field field) {
        Optional<Method> methodOptional = findMethod(
                (field.getType().isAssignableFrom(Boolean.class) ||
                        field.getType().isAssignableFrom(boolean.class) ?
                        (field.getName().startsWith("is") ? "" : BOOLEAN_GETTER_METHOD_PREFIX)
                        : GETTER_METHOD_PREFIX)
                        + field.getName().toLowerCase(), field.getDeclaringClass());
        //Если у метода есть параметры, то это не геттер
        if (methodOptional.isEmpty() || methodOptional.get().getParameterCount() > 0) {
            return Optional.empty();
        }
        return methodOptional;
    }

    public static Optional<Method> getSetter(Field field) {
        return findMethod(SETTER_METHOD_PREFIX + field.getName(), field.getDeclaringClass());
    }

    /**
     * Ищем сеттер по геттеру
     */
    public static Optional<Method> getSetter(Method getter) {
        String getterFieldName = getGetterFieldName(getter);
        return findMethod(SETTER_METHOD_PREFIX + getterFieldName, getter.getDeclaringClass());
    }

    /**
     * Вызывает геттер поля field для объекта и возвращает результат
     *
     * @param field - поле, для которого необходимо вызвать геттер
     * @param <T>   - тип объекта, для которого будет вызван метод
     * @param <V>   - тип значения поля
     */
    public static <T, V> V getValueFromGetter(T object, Field field) {
        Optional<Method> getter = getGetter(field);
        if (getter.isEmpty()) {
            throw new IllegalArgumentException("Not found getter for field: " + field);
        }
        return invokeMethodWithoutArg(object, getter.get());
    }

    /**
     * Вызывает сеттер поля field для объекта object со значением value
     *
     * @param field - поле, для которого необходимо вызвать геттер
     * @param <T>   - тип объекта, для которого будет вызван метод
     * @param <V>   - тип значения поля
     * @param value - значение, которое будет установлено сеттером
     */
    public static <T, V> void setValueBySetter(T object, Field field, V value) {
        Optional<Method> setter = getSetter(field);
        if (setter.isEmpty()) {
            throw new IllegalArgumentException("Not found setter for field: " + field);
        }
        invokeMethod(object, setter.get(), value);
    }

    /**
     * @return Класс первого параметра поля, если оно параметризовано
     */
    public static Class getParameterClass(Field field) {
        Optional<Method> setter = ReflectHelper.getSetter(field);
        if (setter.isEmpty()) {
            throw new IllegalArgumentException("Not found setter for field: " + field);
        }
        Type[] types = setter.get().getGenericParameterTypes();
        if (types.length == 0) throw new RuntimeException("Field " + field.getName() + " is not parameterized");
        ParameterizedType t = (ParameterizedType) types[0];
        Type[] actualTypes = t.getActualTypeArguments();
        if (actualTypes.length == 0) throw new RuntimeException("Field " + field.getName() + " is not parameterized");
        return (Class) actualTypes[0];
    }

    /**
     * Метод ищет во всей иерархии заданного класса ownerClass
     * поле с заданным именем и возвращает аннотацию заданного
     * класса, которым помечено данное поле
     *
     * @param fieldName       Имя поля
     * @param ownerClass      Класс, от которого искать заданное поле
     * @param annotationClass Класс аннотации
     * @param <T>             Тип аннотациии
     * @return Аннотация поля
     */
    public static <T extends Annotation> T getAnnotationByFieldName(String fieldName, Class<?> ownerClass, Class<T> annotationClass) {
        List<Field> allFields = getInheritedPrivateFields(ownerClass);
        for (Field field : allFields) {
            if (fieldName.equals(field.getName())) {
                return field.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    /**
     * @return Список полей класса (и его предков), помеченных аннотацией типа annotationClass
     */
    public static List<Field> getAnnotatedFields(Class<?> ownerClass, Class<? extends Annotation> annotationClass) {
        List<Field> fields = getInheritedPrivateFields(ownerClass);
        fields.removeIf(field -> !field.isAnnotationPresent(annotationClass));
        return fields;
    }

    /**
     * @return Generic класс
     */
    public static Class<?> getGenericMethodClass(Method method) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();

        for (Type genericParameterType : genericParameterTypes) {
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                Type[] parameterArgTypes = aType.getActualTypeArguments();
                for (Type parameterArgType : parameterArgTypes) {
                    if (parameterArgType instanceof Class<?>) {
                        Class<?> parameterArgClass = (Class<?>) parameterArgType;
                        return parameterArgClass;
                    }
                }
            }
        }
        return null;
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Получает поле класса. При этом также ищет это поле в предках
     */
    public static Field getInheritedField(Class<?> clazz, String fieldName) {
        Class<?> i = clazz;
        while (i != null && i != Object.class) {
            Field declaredField = getDeclaredField(i, fieldName);
            if (declaredField != null) {
                return declaredField;
            }
            i = i.getSuperclass();
        }
        return null;
    }

    /**
     *  Alex Tracer (c) 2009
     *  Взято с хабра
     * Для некоторого класса (или интерфейса) определяет,
     * каким классом был параметризован один из его предков (реализующих классов) с generic-параметрами.
     *
     * @param actualClass     анализируемый класс
     * @param genericClass    класс (или интерфейс), для которого определяется значение параметра
     * @param parameterIndex  номер параметра
     * @return класс, являющийся параметром с индексом parameterIndex в genericClass
     */
    public static Class getGenericParameterClass(final Class actualClass, final Class genericClass, final int parameterIndex) {
        // Прекращаем работу если genericClass не является предком actualClass.
        if (!genericClass.isAssignableFrom(actualClass) || genericClass.equals(actualClass)) {
            throw new IllegalArgumentException("Class " + genericClass.getName() + " is not a superclass of "
                    + actualClass.getName() + ".");
        }
        final boolean isInterface = genericClass.isInterface();

        // Нам нужно найти класс, для которого непосредственным родителем будет genericClass.
        // Мы будем подниматься вверх по иерархии, пока не найдем интересующий нас класс.
        // В процессе поднятия мы будем сохранять в genericClasses все классы - они нам понадобятся при спуске вниз.

        // Проейденные классы - используются для спуска вниз.
        Stack<ParameterizedType> genericClasses = new Stack<>();

        // clazz - текущий рассматриваемый класс
        Class clazz = actualClass;

        while (true) {
            Type genericInterface = isInterface ? getGenericInterface(clazz, genericClass) : null;
            Type currentType = genericInterface != null ? genericInterface : clazz.getGenericSuperclass();

            boolean isParameterizedType = currentType instanceof ParameterizedType;
            if (isParameterizedType) {
                // Если предок - параметризованный класс, то запоминаем его - возможно он пригодится при спуске вниз.
                genericClasses.push((ParameterizedType) currentType);
            } else {
                // В иерархии встретился непараметризованный класс. Все ранее сохраненные параметризованные классы будут бесполезны.
                genericClasses.clear();
            }
            // Проверяем, дошли мы до нужного предка или нет.
            Type rawType = isParameterizedType ? ((ParameterizedType) currentType).getRawType() : currentType;
            if (!rawType.equals(genericClass)) {
                // genericClass не является непосредственным родителем для текущего класса.
                // Поднимаемся по иерархии дальше.
                clazz = (Class) rawType;
            } else {
                // Мы поднялись до нужного класса. Останавливаемся.
                break;
            }
        }

        // Нужный класс найден. Теперь мы можем узнать, какими типами он параметризован.
        Type result = genericClasses.pop().getActualTypeArguments()[parameterIndex];

        while (result instanceof TypeVariable && !genericClasses.empty()) {
            // Похоже наш параметр задан где-то ниже по иерархии, спускаемся вниз.

            // Получаем индекс параметра в том классе, в котором он задан.
            int actualArgumentIndex = getParameterTypeDeclarationIndex((TypeVariable) result);
            // Берем соответствующий класс, содержащий метаинформацию о нашем параметре.
            ParameterizedType type = genericClasses.pop();
            // Получаем информацию о значении параметра.
            result = type.getActualTypeArguments()[actualArgumentIndex];
        }

        if (result instanceof TypeVariable) {
            // Мы спустились до самого низа, но даже там нужный параметр не имеет явного задания.
            // Следовательно из-за "Type erasure" узнать класс для параметра невозможно.
            throw new IllegalStateException("Unable to resolve type variable " + result + "."
                    + " Try to replace instances of parametrized class with its non-parameterized subtype.");
        }

        if (result instanceof ParameterizedType) {
            // Сам параметр оказался параметризованным.
            // Отбросим информацию о его параметрах, она нам не нужна.
            result = ((ParameterizedType) result).getRawType();
        }

        if (result == null) {
            // Should never happen. :)
            throw new IllegalStateException("Unable to determine actual parameter type for "
                    + actualClass.getName() + ".");
        }

        if (!(result instanceof Class)) {
            // Похоже, что параметр - массив, примитивный типи, интерфейс или еще-что-то, что не является классом.
            throw new IllegalStateException("Actual parameter type for " + actualClass.getName() + " is not a Class.");
        }

        return (Class) result;
    }

    private static int getParameterTypeDeclarationIndex(final TypeVariable typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();

        // Ищем наш параметр среди всех параметров того класса, где определен нужный нам параметр.
        TypeVariable[] typeVariables = genericDeclaration.getTypeParameters();
        Integer actualArgumentIndex = null;
        for (int i = 0; i < typeVariables.length; i++) {
            if (typeVariables[i].equals(typeVariable)) {
                actualArgumentIndex = i;
                break;
            }
        }
        if (actualArgumentIndex != null) {
            return actualArgumentIndex;
        } else {
            throw new IllegalStateException("Argument " + typeVariable.toString() + " is not found in "
                    + genericDeclaration.toString() + ".");
        }
    }

    private static Type getGenericInterface(final Class sourceClass, final Class genericInterface) {
        Type[] types = sourceClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof Class) {
                if (genericInterface.isAssignableFrom((Class) type)) {
                    return type;
                }
            } else if (type instanceof ParameterizedType) {
                if (genericInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * Находит поле в классе и возвращает его завернутым в Optional
     *
     * @param javaClass класс, где лежит поле
     * @param fieldName имя поля
     */
    public static Optional<Field> findDeclaredField(Class<?> javaClass, String fieldName) {
        return Optional.ofNullable(getDeclaredField(javaClass, fieldName));
    }

    /**
     * Пытается вызвать метод или возвращает дефолтное значение в случае неудачи
     *
     * @param method       вызываемый метод
     * @param defaultValue дефолтное значение
     * @param obj          объект, у которого вызывается метод
     * @param args         список аргументов для вызова
     * @return результат вызова или defaultValue
     */
    public static <T> T invokeMethodOrGetDefault(Method method, T defaultValue, Object obj, Object... args) {
        try {
            return invokeMethod(obj, method, args);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Выполняет метод method без параметров для объекта object и возвращает результат
     */
    public static <T, V> T invokeMethodWithoutArg(V object, Method method) {
        return invokeMethod(object, method);
    }

    /**
     * Выполняет метод method с параметрами args для объекта object и возвращает результат
     *
     * @param method - вызываемый метод
     * @param <T>    - возвращаемый тип метода
     * @param <V>    - тип объекта, для которого будет вызван метод
     */
    public static <T, V> T invokeMethod(V object, Method method, Object... args) {
        try {
            return (T) method.invoke(object, args);
        } catch (Exception e) {
            throw new IllegalStateException("Error on method [" + method.getName() + "] invocation for object of " + object.getClass().getName(), e);
        }
    }

    /**
     * Ищет аннотацию {@code annotationClass} в классе {@code clazz} и всех его родительских классах и интерфейсах по следующему алгоритму:
     * - ищем аннотацию в классе
     * - ищем аннотацию в его интерфейсах
     * - ищем аннотацию в его суперклассах
     *
     * @param clazz           класс объекта, в котором ищем аннотацию
     * @param annotationClass класс аннотации, которую ищем
     * @return аннотация, если она была найдена в классе или его интерфейсах или суперклассах, в противном случае null
     */
    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationClass) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == annotationClass) {
                //noinspection unchecked
                return (A) annotation;
            }
        }

        for (Class<?> interfaces : clazz.getInterfaces()) {
            A annotation = findAnnotation(interfaces, annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null || Object.class == superclass) {
            return null;
        }

        return findAnnotation(superclass, annotationClass);
    }


}
