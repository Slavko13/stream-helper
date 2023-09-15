package com.streamershelper.streamers.controller.skills;

import com.streamershelper.streamers.annotations.DemoBean;
import com.streamershelper.streamers.dto.skills.DemoBeanDetails;
import com.streamershelper.streamers.dto.skills.DemoBeanMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/beans")
@RequiredArgsConstructor
public class DemoBeansController {

    private final ApplicationContext applicationContext;

    private final ConfigurableApplicationContext configurableApplicationContext;


    @GetMapping
    public Map<String, String> listBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Map<String, String> beans = new LinkedHashMap<>();

        for (String beanName : beanNames) {
            Object beanInstance = applicationContext.getBean(beanName);
            if (beanInstance.getClass().isAnnotationPresent(DemoBean.class)) {
                String simpleName = beanName.substring(beanName.lastIndexOf('.') + 1);
                simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
                beans.put(simpleName, beanInstance.getClass().getName());
            }
        }

        return beans;
    }

    @GetMapping("/{beanName}/details")
    public DemoBeanDetails getBeanDetails(@PathVariable String beanName) {

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        String[] dependentBeans = beanFactory.getDependentBeans(beanName);
        String[] dependenciesForBean = beanFactory.getDependenciesForBean(beanName);
        AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanFactory.getBeanDefinition(beanName);
        DemoBeanMetadataDto metadataDto = extractMetadata(annotatedBeanDefinition);


        DemoBeanDetails details = new DemoBeanDetails();
        details.setDemoBeanMetadataDto(metadataDto);
        details.setWhichBeansDependOn(List.of(dependentBeans));
        details.setWhereBeanUsed(List.of(dependenciesForBean));

        return details;
    }


    private DemoBeanMetadataDto extractMetadata(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();

        DemoBeanMetadataDto dto = new DemoBeanMetadataDto();

        dto.setClassName(metadata.getClassName());

        // Интерфейсы
        dto.setInterfaces(Arrays.asList(metadata.getInterfaceNames()));

        // Аннотации
        dto.setAnnotations(new ArrayList<>(metadata.getAnnotationTypes()));

        // Методы
        Set<MethodMetadata> methodMetadatas = metadata.getDeclaredMethods();
        List<DemoBeanMetadataDto.MethodMetadataDto> methodDTOs = methodMetadatas.stream().map(methodMetadata -> {
            DemoBeanMetadataDto.MethodMetadataDto methodDTO = new DemoBeanMetadataDto.MethodMetadataDto();
            methodDTO.setMethodName(methodMetadata.getMethodName());
            methodDTO.setReturnTypeName(methodMetadata.getReturnTypeName());
            methodDTO.setAnnotations(Collections.singletonList(methodMetadata.getAnnotations()));
            return methodDTO;
        }).collect(Collectors.toList());

        dto.setMethods(methodDTOs);

        return dto;
    }

}
