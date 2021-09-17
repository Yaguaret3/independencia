$(document).ready(function() {
    cargarRecursosCiudades();
    cargarEjercitosCiudades()
    cargarActoresPoliticos()
});

async function cargarRecursosCiudades(){

    const request = await fetch('api/control/listarRecursos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const recursosCiudades = await request.json();

    let listadoHtml = "";

    for (let recurso of recursosCiudades) {

        let recursoHtml = '<tr><td>'+recurso.ciudad+'</td><td>'+recurso.estatus+'</td><td>'+recurso.caballos+'</td><td>'+recurso.vacas+'</td><td>'+recurso.hierro+
                        '</td><td>'+recurso.vino+'</td><td>'+recurso.yerba+'</td><td>'+recurso.nivel_industria+'</td></tr>';
        listadoHtml += recursoHtml;
      }
      document.querySelector('#recursosCiudades tbody').outerHTML = listadoHtml;
}

async function cargarEjercitosCiudades(){

    const request = await fetch('api/control/listarEjercitos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const ejercitosCiudades = await request.json();

    let listadoHtmlEjercito = "";
    let listadoHtmlMovimientos = "";

    for (let ejercito of ejercitosCiudades) {

        let ejercitoHtml = '<tr><td>'+ejercito.ciudad+'</td><td>NULL</td><td>'+ejercito.oficial_a+'</td><td>'+ejercito.oficial_b+'</td><td>'+ejercito.oficial_c+'</td><td>'+ejercito.oficial_d+
                        '</td><td>'+ejercito.oficial_e+'</td><td>'+ejercito.nivel_mision_comercial+'</td></tr>';
        listadoHtmlEjercito += ejercitoHtml;

        let movimientoHtml = '<tr><td>'+ejercito.ciudad+'</td><td>'+ejercito.movimiento+'</td><td>'+ejercito.destino_1+'</td></tr>';
        listadoHtmlMovimientos += movimientoHtml;
      }
      document.querySelector('#ejercitosCiudades tbody').outerHTML = listadoHtmlEjercito;
      document.querySelector('#movimientosMilitares tbody').outerHTML = listadoHtmlMovimientos;
}

async function cargarActoresPoliticos(){

    const request = await fetch('api/control/listarActoresPoliticos', {
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