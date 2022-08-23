window.onload=(async function( event) {
    var url="http://localhost:8080/api/v1/authorizedUser/getAllAuthorizedUsers";
    var response= await fetch(url);
    var body= await response.json();

    var deleteButton='<input type="submit" name="delete">'



    if(body.length>0){
        var temp='';
        body.forEach((u)=>{
            temp+='<tr>';
            temp+='<td>'+u.username+'</td>';
            temp+='<td>'+u.role+'</td>';
            temp+='<td>'+'<input class="button" type="submit" name="delete" value="Διαγραφή χρήστη" >'+'</td>';
            temp+='</tr>';
            document.getElementById('table-body').innerHTML=temp;
        })}else{
        temp+='<tr>';
        temp+='<td >'+'No data'+'</td>';
        document.getElementById('table-body').innerHTML=temp;
    }

})