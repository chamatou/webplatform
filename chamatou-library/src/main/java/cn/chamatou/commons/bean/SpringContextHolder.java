package cn.chamatou.commons.bean;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware{
	private ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx=applicationContext;
	}
	/**
	 * 获取spring管理bean
	 * @param name
	 * @return
	 */
	public Object getBean(String name){
		return ctx.getBean(name);
	}
	/**
	 * 获取指定注解的bean
	 * @param annotationType
	 * @return
	 */
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
		return ctx.getBeansWithAnnotation(annotationType);
	}
}
