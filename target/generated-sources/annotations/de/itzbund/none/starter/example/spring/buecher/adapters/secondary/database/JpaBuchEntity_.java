package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaBuchEntity.class)
public abstract class JpaBuchEntity_ {

	public static final String AUSGELIEHEN = "ausgeliehen";
	public static final String TITEL = "titel";
	public static final String ISBN = "isbn";
	public static final String ID = "id";
	public static final String AUTOR = "autor";
	public static final String AUSGELIEHEN_VON = "ausgeliehenVon";

	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#ausgeliehen
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, Boolean> ausgeliehen;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#titel
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, String> titel;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#isbn
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, String> isbn;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#id
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, Long> id;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity
	 **/
	public static volatile EntityType<JpaBuchEntity> class_;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#autor
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, String> autor;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database.JpaBuchEntity#ausgeliehenVon
	 **/
	public static volatile SingularAttribute<JpaBuchEntity, Long> ausgeliehenVon;

}

