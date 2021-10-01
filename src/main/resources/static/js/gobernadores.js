// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarRecursos();
  cargarActoresPoliticos();
});

async function cargarActoresPoliticos(){

  const request = await fetch('api/gobernadores/listarActoresPoliticos', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
    });
  const actoresPoliticos = await request.json();

  let listadoHtml = "";

  for (let actor of actoresPoliticos) {

      let actorHtml = '<tr><td>'+actor.actor+'</td><td>'+actor.valor+'</td></tr>';
      listadoHtml += actorHtml;
    }
    document.querySelector('#tablaActoresPoliticos tbody').outerHTML = listadoHtml;
}


async function cargarRecursos(){
    const request = await fetch('api/gobernadores/cargarRecursos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const respuesta = await request.json();

    // Cargar nombre de ciudad
    $("#nombreCiudad").html(respuesta.ciudad);

    // Cargar recursos
    $("#caballos_base").html(respuesta.caballos);
    $("#vacas_base").html(respuesta.vacas);
    $("#hierro_base").html(respuesta.hierro);
    $("#vino_base").html(respuesta.vino);
    $("#yerba_base").html(respuesta.yerba);
    $("#estatus").html(respuesta.estatus);
    $("#nivel_industria").html(respuesta.nivel_industria);
    $("#unidades_reclutadas_no_enviadas").html(respuesta.unidades);
    $("#unidades_totales_enviadas").html(respuesta.unidades_enviadas);
    $("#oficiales_contratados").html(respuesta.oficiales);
    $("#actorPolitico1").html(respuesta.actor_politico_1);
    $("#actPol1").html(respuesta.actor_politico_1);
    $("#actorPolitico2").html(respuesta.actor_politico_2);
    $("#actPol2").html(respuesta.actor_politico_2);

    // Cargar historial de comercio
    $('#historialComercial').html(respuesta.historial_comercial);

    // Cargar nivel de mision comercial
    cargarNivelMisionComercial();
}

async function cargarNivelMisionComercial(){

    const request = await fetch('api/gobernadores/cargarNivelMisionComercial', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const respuesta = await request.json();
    $("#nivel_mision_comercial").html(respuesta.nivel_mision_comercial);
}

async function aumentarEstatus(){

    $("#aumentarEstatusModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    datos.actor_politico_pedido = $("input[name='actorPedido']:checked").val();

    if($("#caballos_checkbox_estatus").is(":checked")){
            datos.caballos= 1;
        }
        if($("#vacas_checkbox_estatus").is(":checked")){
            datos.vacas= 1;
        }
        if($("#hierro_checkbox_estatus").is(":checked")){
            datos.hierro= 1;
        }
        if($("#vino_checkbox_estatus").is(":checked")){
            datos.vino= 1;
        }
        if($("#yerba_checkbox_estatus").is(":checked")){
            datos.yerba= 1;
        }

    const request = await fetch('api/gobernadores/aumentarEstatus', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
    });
}

async function aumentarIndustria(){

    $("#aumentarIndustriaModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    if($("#caballos_checkbox_industria").is(":checked")){
        datos.caballos= 1;
    }
    if($("#vacas_checkbox_industria").is(":checked")){
        datos.vacas= 1;
    }
    if($("#hierro_checkbox_industria").is(":checked")){
        datos.hierro= 1;
    }
    if($("#vino_checkbox_industria").is(":checked")){
        datos.vino= 1;
    }
    if($("#yerba_checkbox_industria").is(":checked")){
        datos.yerba= 1;
    }

    const request = await fetch('api/gobernadores/aumentarIndustria', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
    });
}

async function aumentarMisionComercial(){

    $("#aumentarNivelMisionComercialModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    if($("#caballos_checkbox_mision").is(":checked")){
        datos.caballos= 1;
    }
    if($("#vacas_checkbox_mision").is(":checked")){
        datos.vacas= 1;
    }
    if($("#hierro_checkbox_mision").is(":checked")){
        datos.hierro= 1;
    }
    if($("#vino_checkbox_mision").is(":checked")){
        datos.vino= 1;
    }
    if($("#yerba_checkbox_mision").is(":checked")){
        datos.yerba= 1;
    }

    const request = await fetch('api/gobernadores/aumentarMisionComercial', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
}

async function reclutarUnidades(){

    $("#reclutarUnidadesModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    if($("#caballos_checkbox_unidades").is(":checked")){
        datos.caballos= 1;
    }
    if($("#vacas_checkbox_unidades").is(":checked")){
        datos.vacas= 1;
    }
    if($("#hierro_checkbox_unidades").is(":checked")){
        datos.hierro= 1;
    }
    if($("#vino_checkbox_unidades").is(":checked")){
        datos.vino= 1;
    }
    if($("#yerba_checkbox_unidades").is(":checked")){
        datos.yerba= 1;
    }

    const request = await fetch('api/gobernadores/reclutarUnidades', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
}

async function contratarOficial(){

    $("#contratarOficialesModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    if($("#caballos_checkbox_unidades").is(":checked")){
        datos.caballos= 1;
    }
    if($("#vacas_checkbox_unidades").is(":checked")){
        datos.vacas= 1;
    }
    if($("#hierro_checkbox_unidades").is(":checked")){
        datos.hierro= 1;
    }
    if($("#vino_checkbox_unidades").is(":checked")){
        datos.vino= 1;
    }
    if($("#yerba_checkbox_unidades").is(":checked")){
        datos.yerba= 1;
    }

    datos.nivel_oficial_pedido = $("#gradoOficial").val();

    const request = await fetch('api/gobernadores/contratarOficiales/', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    body: JSON.stringify(datos)
    });
}

async function enviarUnidades(){

    $("#enviarUnidadesModal").modal("hide");

    const request = await fetch('api/enviarUnidades/', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    });
}

async function comerciar(){

    $("#comerciarModal").modal("hide");

    let datos = {};
    datos.caballos= 0;
    datos.vacas= 0;
    datos.hierro= 0;
    datos.vino= 0;
    datos.yerba= 0;

    datos.destino_comercial = $("#comerciarCon").val();

    switch ($("input[name='recursos']:checked").val()) {
        case 'caballos':
            datos.caballos = ($("#cantidadAEnviarComercio").val())
            break;
        case 'vacas':
            datos.vacas = ($("#cantidadAEnviarComercio").val())
            break;
        case 'hierro':
            datos.hierro = ($("#cantidadAEnviarComercio").val())
            break;
        case 'vino':
            datos.vino = ($("#cantidadAEnviarComercio").val())
            break;
        case 'yerba':
            datos.yerba = ($("#cantidadAEnviarComercio").val())
            break;
    }

    const request = await fetch('api/gobernadores/comerciar', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    body: JSON.stringify(datos)
    });

}