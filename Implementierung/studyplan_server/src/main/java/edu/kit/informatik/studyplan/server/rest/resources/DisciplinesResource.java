package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonView;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

/**
 * REST resource for /disciplines.
 */
@Path("/disciplines")
@AuthorizationNeeded
public class DisciplinesResource {
    /**
     * Handler for GET /disciplines
     * @return a JSON representation of all disciplines available.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(StudentResource.Views.DisciplineClass.class)
    public Map<String, List<Discipline>> getAllDisciplines() {
        return SimpleJsonResponse.build("disciplines", Utils.withModuleDao(ModuleDao::getDisciplines));
    }
}
