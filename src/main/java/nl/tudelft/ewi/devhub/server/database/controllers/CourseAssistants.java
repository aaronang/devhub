package nl.tudelft.ewi.devhub.server.database.controllers;

import nl.tudelft.ewi.devhub.server.database.entities.CourseAssistant;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CourseAssistants extends Controller<CourseAssistant> {

	@Inject
	public CourseAssistants(EntityManager entityManager) {
		super(entityManager);
	}

}
