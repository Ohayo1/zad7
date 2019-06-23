package rest.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("people")
@Stateless
public class PersonResource {
	@PersistenceContext
	EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<People> getAll() {
		return em.createNamedQuery("people.all", People.class).getResultList();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(People people) {
		em.persist(people);
		return Response.ok(people.getId()).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, People p) {
		People result = em.createNamedQuery("people.id", People.class).setParameter("peopleId", id).getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		result.setFirstName(p.getFirstName());
		result.setLastName(p.getLastName());
		result.setGender(p.getGender());
		result.setEmail(p.getEmail());
		result.setAge(p.getAge());
		result.setBirthday(p.getBirthday());
		em.persist(result);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{peopleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("peopleId") int id) {
		People people = em.createNamedQuery("people.id", People.class).setParameter("peopleId", id).getSingleResult();
		em.remove(people);
		return Response.ok().build();
	}

}
