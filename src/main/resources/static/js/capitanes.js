// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarRecursos();
    conectarWS();
    cargarTimer();
});

let intervalo = setInterval(function(){

    // Tomamos la fecha actual
    let ahora = new Date().getTime();

    // Restamos para calcular la distancia
    let distancia = localStorage.proximoFinTurno - ahora;
    // Calculamos minutos y segundos
    let minutos = Math.floor((distancia % (1000 * 60 * 60)) / (1000 * 60));
    let segundos = Math.floor((distancia % (1000 * 60)) / 1000);

    let minDec = "";
    let minUni = "";
    let secDec = "";
    let secUni = "";

    // Mostramos el resultado
    if(minutos<10){
        minDec = "0";
        minUni = minutos.toString().charAt(0);
    } else{
        minDec = minutos.toString().charAt(0);
        minUni = minutos.toString().charAt(1);
    }
    if(segundos<10){
        secDec = "0";
        secUni = segundos.toString().charAt(0);
    } else{
        secDec = segundos.toString().charAt(0);
        secUni = segundos.toString().charAt(1);
    }
    $("#minDec").html(minDec);
    $("#minUni").html(minUni);
    $("#secDec").html(secDec);
    $("#secUni").html(secUni);

    // Si la cuenta terminó, dejá en cero todo
    if (distancia < 0) {
        $("#minDec").html(0);
        $("#minUni").html(0);
        $("#secDec").html(0);
        $("#secUni").html(0);
    }
    },1000);

function conectarWS() {
    var socket = new SockJS('/independencia-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/actualizar_capitanes', function (valorFinal) {
          cargarRecursos();
          cargarTimer();
        });
    });
}

function disparoControl(){
    stompClient.send('/actualizar_control', {}, JSON.stringify({'mensaje': ""}));
}
function disparoGobernadores(){
    stompClient.send('/actualizar_gobernadores', {}, JSON.stringify({'mensaje': ""}));
}
function disparoCapitanes(){
    stompClient.send('/actualizar_capitanes', {}, JSON.stringify({'mensaje': ""}));
}

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

async function cargarTimer(){

    const request = await fetch('api/capitanes/cargarTimer', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const cosas = await request.json();

    for (let cosa of cosas){

        // Cargo TURNO
        if(cosa.accion == "turno"){
            if(cosa.valor<10){
                $("#turnoDec").html(0);
                $("#turnoUni").html(cosa.valor.toString().charAt(0));
            } else{
                $("#turnoDec").html(cosa.valor.toString().charAt(0));
                $("#turnoUni").html(cosa.valor.toString().charAt(1));
            }
        }
        // Cargo FASE
        if(cosa.accion == "fase_militar"){
            switch(cosa.valor){
                case 1:
                    $("#fase_base").html("Inicial");
                    break;
                case 2:
                    $("#fase_base").html("Militar 1");
                    break;
                case 3:
                    $("#fase_base").html("Comercial");
                    break;
                case 4:
                    $("#fase_base").html("Militar 2");
                    break;
                case 5:
                    $("#fase_base").html("Final");
                    break;
            }
        }
        // Cargo PAUSA
        if(cosa.accion == "pausa"){
            if(cosa.valor == 1){
                $("#pausado").html('<span class="fase mb-4">PAUSADO</span>');
            }
            if(cosa.valor == 0){
                $("#pausado").html('');
            }
        }

        // Cargo TIMER
        if(cosa.accion == "timer"){
            localStorage.proximoFinTurno = new Date(cosa.valor).getTime();
        }
    }
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
    const respuesta = await request.text();
    if(respuesta == ""){
        disparoControl();
    } else {
        alert(respuesta);
    }
}

async function asignarUnidades(){

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
    const respuesta = await request.text();
    if(respuesta == ""){
        disparoControl();
        disparoCapitanes();

        $("#asignarUnidadesModal").modal("hide");
    } else {
        alert(respuesta);
    }
}

function cerrarSesion(){
    localStorage.removeItem("token");
    window.location.href = "login.html";
}