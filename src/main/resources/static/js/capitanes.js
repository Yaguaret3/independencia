// Call the dataTables jQuery plugin
$(document).ready(function() {
});
async function listarMovimientos(){

    const request = await fetch('api/capitanes/listarMovimientos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const industria = await request.json();
}