package de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(JpaBestellungEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class JpaBestellungEntity_ {

	public static final String QUANTITY = "quantity";
	public static final String PRODUCT_ID = "productId";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity#quantity
	 **/
	public static volatile SingularAttribute<JpaBestellungEntity, Integer> quantity;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity#productId
	 **/
	public static volatile SingularAttribute<JpaBestellungEntity, Long> productId;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity#price
	 **/
	public static volatile SingularAttribute<JpaBestellungEntity, BigDecimal> price;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity#description
	 **/
	public static volatile SingularAttribute<JpaBestellungEntity, String> description;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity#id
	 **/
	public static volatile SingularAttribute<JpaBestellungEntity, Long> id;
	
	/**
	 * @see de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database.JpaBestellungEntity
	 **/
	public static volatile EntityType<JpaBestellungEntity> class_;

}

