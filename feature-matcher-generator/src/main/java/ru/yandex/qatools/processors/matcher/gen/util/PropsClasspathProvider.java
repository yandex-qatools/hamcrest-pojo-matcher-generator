package ru.yandex.qatools.processors.matcher.gen.util;

import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.providers.DefaultPropertyProvider;

import java.util.Properties;

import static org.apache.velocity.util.ClassUtils.getResourceAsStream;
import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 13:43
 *
 * As quick workaround for https://github.com/yandex-qatools/properties/issues/15
 * Can be fixed soon
 */
public class PropsClasspathProvider extends DefaultPropertyProvider {

    @Override
    public <T> Properties provide(T bean, Properties properties) {
        Class<?> clazz = bean.getClass();

        if (have(clazz, Resource.Classpath.class)) {
            String[] paths = classpath(clazz, properties);
            for (String path : paths) {
                properties.putAll(readProperties(getResourceAsStream(clazz, path)));
            }
        }

        properties.putAll(System.getProperties());
        return properties;
    }


}
