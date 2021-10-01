// Call the dataTables jQuery plugin
$(document).ready(function() {

    cargarRecursos();
});


async function cargarRecursos(){

    const request = await fetch('api/capitanes/listarRecursos', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization': localStorage.getItem("token")
            },
          });
        const recursos = await request.json();

        let oficial_a = "img/A"+recursos.oficial_a+".jpg";
        let oficial_b = "img/B"+recursos.oficial_b+".jpg";
        let oficial_c = "img/C"+recursos.oficial_c+".jpg";
        let oficial_d = "img/D"+recursos.oficial_d+".jpg";
        let oficial_e = "img/E"+recursos.oficial_e+".jpg";

        $("#unidadesAAsignarBase").html(recursos.unidades_recien_llegadas);
        $("#unidadesAgrupadasBase").html(recursos.unidades_agrupadas);
        $("#oficial_a_base").attr("src", oficial_a);
        $("#oficial_b_base").attr("src", oficial_b);
        $("#oficial_c_base").attr("src", oficial_c);
        $("#oficial_d_base").attr("src", oficial_d);
        $("#oficial_e_base").attr("src", oficial_e);
}

async function actualizarMovimientos(){

    const request = await fetch('api/capitanes/listarMovimientos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const movimientos = await request.json();

    let listadoHtml = "";

    for (let movimiento of movimientos) {

       let recursoHtml = '<tr><td>'+movimiento.ciudad+'</td><td>'+movimiento.movimiento+'</td><td>'+movimiento.destino_1+'</td><td>'+movimiento.unidades_agrupadas+
                        '</td><td>'+movimiento.nivel_mision_comercial+'</td></tr>';
       listadoHtml += recursoHtml;
    }
    $('#tablaDeMovimientos tbody').html(listadoHtml);
}

async function enviarMovimiento(){

    let datos = {};
    datos.destino_1 = $("input[name='destino']").val();
    datos.movimiento = $("input[name='tipoMovimiento'").val();

    const request = await fetch('api/capitanes/enviarMovimiento', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
}

async function asignarUnidades(){

    $("#asignarUnidadesModal").modal("hide");

    let datos = {};
    datos.unidades_a_asignar = $("#asignandoUnidades").val();
    datos.dar_unidades_a = $("input[name='darUnidadesA']").val();

    const request = await fetch('api/capitanes/asignarUnidades', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
}