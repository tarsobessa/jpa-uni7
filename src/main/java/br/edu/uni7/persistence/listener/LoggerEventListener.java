package br.edu.uni7.persistence.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class LoggerEventListener {
	
	@PostLoad
	public void postLoad(Object entity){
		log("PostLoad", entity);
	}
	
	@PrePersist
	public void prePersist(Object entity){
		log("PrePersist", entity);
	}
	
	@PostPersist
	public void postPersist(Object entity){
		log("PostPersist", entity);
	}
	
	@PreUpdate
	public void preUpdate(Object entity){
		log("PreUpdate", entity);
	}
	
	@PostUpdate
	public void postUpdate(Object entity){
		log("PostUpdate", entity);
	}
	
	@PreRemove
	public void preRemove(Object entity){
		log("PreRemove", entity);
	}
	
	@PostRemove
	public void postRemove(Object entity){
		log("PostRemove", entity);
	}
	
	public void log(String event, Object entity) {
		System.out.println(String.format("Processando Evento %s na Entidade %s", event, entity.getClass().getName()));
	}
}
