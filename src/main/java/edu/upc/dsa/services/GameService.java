package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/Game", description = "Endpoint to Game Service")
@Path("/game")
public class GameService {

    private GameManager manager;

    public GameService() {
        this.manager = GameManagerImpl.getInstance(); //GameManagerImpl.getInstance();  new UserDAOImpl();
        //IU

        if (manager.size() == 0) {
            // this.manager.addObjeto("pokeball", "Captura Pokemon", 5.00);
            this.manager.registrarUsuario("Jose", "jose@gmail.com", "123");
            this.manager.registrarUsuario("Jose", "n", "123");
            //this.manager.addUsuarioORM("P","p","12");
            //this.manager.registrarUsuario("Prueba", "prueba@gmail.com", "1234");
            this.manager.addObjeto("Monitor","144Hz",99.99,"https://img.freepik.com/vector-premium/monitor-computadora-realista_88272-327.jpg");
            this.manager.addObjeto("Raton","inalambrico",20.00,"https://www.info-computer.com/156049-medium_default/logitech-lgt-m90-1000-dpi-gris-q.jpg");
            this.manager.addObjeto("Teclado","Retroiluminado",50.00,"https://www.shutterstock.com/image-photo/computer-keyboard-isolated-on-white-260nw-222047851.jpg");
            this.manager.addRanking("Jose","hoy",10,"No");
            this.manager.addRanking("Cristian","hoy",9,"No");
            this.manager.addFaq("¿Esto funciona?","Puede");

        }
    }

    //Registrar usuario
    @POST
    @ApiOperation(value = "Registrar usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UsuarioTO.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/registrarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(UsuarioTO usuario) {
        //Usuario u = this.manager.getUsuarioPorCorreo(usuario.getCorreo());
        Usuario u = this.manager.getUserByEmailORM(usuario.getCorreo());

        if (u != null) {
            return Response.status(500).entity(usuario).build();

        } else {
            //this.manager.registrarUsuario(usuario.getNombre(), usuario.getCorreo(), usuario.getPassword());
            this.manager.addUsuarioORM(usuario.getNombre(), usuario.getCorreo(), usuario.getPassword());
            return Response.status(201).entity(usuario).build();
        }
    }

    @POST
    @ApiOperation(value = "registrar usuario 2", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UsuarioTO.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/registrarUsuario2")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsuario2(UsuarioTO usuarioTO) {//Antes VOUsuario user
        Usuario user = new Usuario(usuarioTO);
        user = this.manager.addUsuario2(user);
        if (user == null) {
            return Response.status(500).build();
        }
        else
            return Response.status(201).entity(user).build();
    }



    //Añadir objeto
    @POST
    @ApiOperation(value = "crear objeto nuevo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Objeto.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addObjeto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addObjeto(Objeto objeto) {

        if (objeto.getNombre()==null || objeto.getDescripcion()==null || objeto.getPrecio()==0.00)  return Response.status(500).entity(objeto).build();
        this.manager.addObjeto(objeto.getNombre(), objeto.getDescripcion(),objeto.getPrecio(), objeto.getFotoimagen());
        return Response.status(201).entity(objeto).build();
    }



    //login
    @POST
    @ApiOperation(value = "login usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = CredencialTO.class),
            @ApiResponse(code = 404, message = "No existe")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(CredencialTO credencials) {
/*        //Usuario u = this.manager.getUsuarioPorCorreo(credencials.getCorreo());
        Usuario u = this.manager.getUserByEmailORM(credencials.getCorreo());

        if (u == null) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }

        if (credencials.getPassword().equals(u.getPassword())) {
            this.manager.loginORM(credencials.getCorreo(), credencials.getPassword());
            return Response.status(201).entity(credencials).build();

        } else {
            return Response.status(404).entity("credenciales invalidas").build();
        }*/

        this.manager.loginORM(credencials.getCorreo(), credencials.getPassword());
        if (manager.loginORM(credencials.getCorreo(), credencials.getPassword()) == true){
            return Response.status(201).entity(credencials).build();
        }
        else{
            return Response.status(404).entity("credenciales invalidas").build();
        }
    }



    // comprar objetos por parte de un usuario
    @POST
    @ApiOperation(value = "comprar objeto nuevo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/compraObjetos/{correo}/{nombreObjeto}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hacerCompra(@PathParam("correo")  String correo, @PathParam("nombreObjeto")  String nombreObjeto) {
/*        Objeto objeto = this.manager.getObjetoPorNombre(idUsuario);
        Usuario usuario = this.manager.getUsuarioPorNombre(Usuario);
        if (objeto.getNombre()==null || objeto.getDescripcion()==null)  return Response.status(500).build();
        this.manager.hacerCompraORM(usuario.getCorreo(), objeto.getNombre());
        return Response.status(201).entity(objeto).build();*/
        TablaCompra tablaCompra = this.manager.hacerCompraORM(correo, nombreObjeto);
        if (tablaCompra==null) {
            return Response.status(500).build();
        }
        else
            return Response.status(201).build();
    }

    //lista objetos
    @GET
    @ApiOperation(value = "lista objetos", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Objeto.class, responseContainer="List"),
    })
    @Path("/listaObjetos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlistaObjetos() {
        List<Objeto> objeto = this.manager.listadeObjetosORM();
        GenericEntity<List<Objeto>> entity = new GenericEntity<List<Objeto>>(objeto) {};
        return Response.status(201).entity(entity).build()  ;
    }

    //Datos Usuario
    @GET
    @ApiOperation(value = "Datos Usuario", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class),
    })
    @Path("/datosUsuario/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("correo") String email) {
        Usuario u = this.manager.getUserByEmailORM(email);
        if (u != null)
            return Response.status(201).entity(u).build();
        else
            return Response.status(500).build();
    }

    //lista objetos de un usuario
    @GET
    @ApiOperation(value = "lista objetos de un usuario", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = TablaCompra.class, responseContainer="List"),
    })
    @Path("/listaObjetosUsuario/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlistaObjetosUsuario(@PathParam("correo") String correo) {
        List<TablaCompra> listaObjetoUsuario = this.manager.listaObjetosCompradosPorUsuarioORM(correo);
        GenericEntity<List<TablaCompra>> entity = new GenericEntity<List<TablaCompra>>(listaObjetoUsuario) {};
        return Response.status(201).entity(entity).build()  ;
    }

    //Añadir Denuncia
    @POST
    @ApiOperation(value = "Añadir denuncia", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Denuncia.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/añadirDenuncia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarDenuncia(Denuncia denuncia) {

        //this.manager.registrarUsuario(usuario.getNombre(), usuario.getCorreo(), usuario.getPassword());
        this.manager.añadirDenuncia(denuncia.getFecha(), denuncia.getNombre(), denuncia.getComentario());
        return Response.status(201).entity(denuncia).build();
    }

    //lista Faq
    @GET
    @ApiOperation(value = "lista Faq", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Faq.class, responseContainer="List"),
    })
    @Path("/getlistaFAQ")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlistaFAQ() {
        List<Faq> faq = this.manager.listadeFaq();
        GenericEntity<List<Faq>> entity = new GenericEntity<List<Faq>>(faq) {};
        return Response.status(201).entity(entity).build();
    }

    //lista Ranking
    @GET
    @ApiOperation(value = "Ranking", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UsuarioMin.class, responseContainer="List"),
    })
    @Path("/getRanking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking() {
        List<UsuarioMin> um = this.manager.listaRanking();
        GenericEntity<List<UsuarioMin>> entity = new GenericEntity<List<UsuarioMin>>(um) {};
        return Response.status(201).entity(entity).build();
    }

    //lista Prueba
    @GET
    @ApiOperation(value = "Prueba", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UsuarioMin.class, responseContainer="List"),
    })
    @Path("/getPrueba")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrueba() {
        List<UsuarioMin> um = this.manager.listaRanking();
        GenericEntity<List<UsuarioMin>> entity = new GenericEntity<List<UsuarioMin>>(um) {};
        return Response.status(201).entity(entity).build();
    }
    //Idioma
    @PUT
    @ApiOperation(value = "Idioma", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Idioma.class),
    })
    @Path("/putIdioma")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putIdioma(Idioma idioma) {
        this.manager.añadirIdioma(idioma.getCorreo(), idioma.getIdioma());
        return Response.status(201).build();
    }

    //lista objetos ordenados ascendentemente
 /*   @GET
    @ApiOperation(value = "lista objetos ordenados ascendentemente", notes = "asdas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Objeto.class, responseContainer="List"),
    })
    @Path("/listaObjetosAscendente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlistaObjetosOrdenadosAscendentemente() {

       List<Objeto> objeto = this.manager.listadeObjetosOrdenadosPorPrecio();

        GenericEntity<List<Objeto>> entity = new GenericEntity<List<Objeto>>(objeto) {};
        return Response.status(201).entity(entity).build();

    } */

}