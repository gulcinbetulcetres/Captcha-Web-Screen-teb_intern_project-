
function MyFunc() {
    const url = 'http://localhost:8081/api/v1';
    fetch(url)
        .then(function (response) {
            response.text().
                then(
                    data => document.getElementById("capimg").src = data
                )
        })
}

function UserAction() {
    let data = { captcha: document.getElementById("userInput").value };
    const url = 'http://localhost:8081/api/v1/save';
    fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/json; charset=utf-8' },
        body: JSON.stringify(data)
 
    }).then(function (response) {
        response.text()
            .then(
                result => {
                    if (result == 1) {
                        swal("Correct Captcha!", "You're a human!", "success");
                    }
                    else {
                        swal("Captcha Invalid!", "You're a robot!", "error");                        
                        MyFunc();
                    }
                });
    });
}

