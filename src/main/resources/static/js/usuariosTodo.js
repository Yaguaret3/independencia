// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
    $('#tablaDeUsuarios').DataTable();
});


async function cargarUsuarios(){

    const request = await fetch('api/listarUsuarios', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        //body: JSON.stringify({a: 1, b: 'Textual content'})
      });
    const usuarios = await request.json();

    let listadoHtml = "";

    for (let usuario of usuarios) {

        let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
        let usuarioHtml = '<tr><th>'+usuario.id+'</th><th>'+usuario.nombre+'</th><th>'+usuario.apellido+'</th><th>'+usuario.nick+'</th><th>'+usuario.password+'</th><th>'+usuario.email+'</th><th>'+botonEliminar+'</th></tr>'
        listadoHtml += usuarioHtml;
      }

      document.querySelector('#tablaDeUsuarios tbody').outerHTML = listadoHtml;

}

async function eliminarUsuario(id){

    if(!confirm("¿Desea eliminar el usuario?")){
        return;
    }

    const request = await fetch('eliminarUsuario/'+id, {
            method: 'DELETE',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
              //'Authorization': localStorage.token
            },
            //body: JSON.stringify({a: 1, b: 'Textual content'})
          });

          location.reload();
}
