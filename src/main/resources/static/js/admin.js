

window.onload=(async function( event) {
    var url="http://localhost:8080/api/v1/authorizedUser/getAllAuthorizedUsers";
    var response= await fetch(url);
    var body= await response.json();

    var deleteButton='<input type="submit" name="delete">'
    
    if(body.length>0){
        var temp='';
        var count=1;
        body.forEach((u)=>{
            temp+='<tr>';
            temp+='<td id="username_'+count+'">'+u.username+'</td>';
            temp+='<td id="role_'+count+'">'+u.role+'</td>';
            temp+='<td id="button">'+'<input class="button" type="submit" onclick="delete_function('+count+')" id="delete_button_'+count+'" name="delete" value="&#10006" >'+'</td>';
            temp+='</tr>';
            document.getElementById('table-body').innerHTML=temp;
            count++
        })}else{
            temp=''
        temp+='<td>'+'0'+'</td><td>'+'No data'+'</td><td>-</td>';
        document.getElementById('table-body').innerHTML=temp;
        
    }

})

function delete_function(id){
    const username=document.getElementById("username_"+id).innerHTML
    console.log(username)
    var answer= confirm("Are you sure you want to delete this user?")
    if(answer){
        fetch('http://localhost:8080/api/v1/authorizedUser/deleteUser?username='+username,{method: 'DELETE'})
        .then((response)=>{
            if(response.ok){
                alert("Ο χρήστης "+username+" διεγράφη!")
                window.location.reload()
            }else{
                alert("Πρόβλημα κατά τη διαγραφή του χρήστη"+username+"!")
            }
        })
        .catch((err)=>{
            alert('rejected',err)
        })

    }
}

document.getElementById('new_user_form').addEventListener('submit', async(e) => {
    e.preventDefault();

    fetch('http://localhost:8080/api/v1/authorizedUser/addUser',
    {
        method: 'POST',
        body: new FormData(e.target)
    }).then((response)=>{
        if(response.ok){
            alert('Δημιουργήθηκε νέος χρήστης!')
            window.location.reload()
        }else{
            alert('Πρόβλημα κατά την εγγραφή νέου χρήστη!')
        }
    })

})