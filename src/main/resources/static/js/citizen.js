document.getElementById('document').onchange=function (){
    document.getElementById('document_right_column').innerHTML='&#10004'
}

function delay(milliseconds){
    return new Promise(resolve => {
        setTimeout(resolve, milliseconds);
    });
}

document.getElementById('form').addEventListener('submit', async(e) => {
    e.preventDefault();
    const fullnameRegex= /^([A-ZΑ-ΩΆΈΎΊΌΏΉ]{1,50}[a-zα-ωίϊΐόάέύϋΰήώ]{1,} *){2,}$/
    const idNumberRegex= /^[A-Z]{2}[\d]{6}$/
    const militaryNumberRegex=/^\d{3}\/\d+\/\d{4}$/

    const fullname= document.getElementById("fullname").value
    const phoneNumber= document.getElementById("phoneNumber").value
    const idNumber= document.getElementById("idNumber").value
    const militaryNumber= document.getElementById("militaryNumber").value

    let errors=[]
    if (!fullname.match(fullnameRegex))
        errors.push("Wrong name format")


    if (phoneNumber.length!==10 ||
        !(phoneNumber.substring(0,1).match('2') || phoneNumber.substring(0,2).match("69")))
        errors.push("Wrong phone number format")

    if(!idNumber.match(idNumberRegex))
        errors.push("Wrong ID number format")

    if(!militaryNumber.match(militaryNumberRegex))
        errors.push("Wrong military number format")


    if(errors.length>0){
        console.log(errors)
        alert(errors.join('\n'))
    }else {
        fetch('http://localhost:8080/api/v1/form/addForm',{
            method: 'POST',
            body: new FormData(e.target)
        }).then(res=>{
            if(res.ok) {
                alert("Success!\nYou will be redirected to home page.")
            }else{
                alert("Server error!\nRedirecting to home page in 5s.")
            }
        }).catch(error=>alert('Client Error')).then(setTimeout(function (){
            window.location.href="http://localhost:8080/login"},5000))


    }
})


