
async function downloadFile() {
    let code = document.getElementById("code_input").value;
    if (code.length === 6) {
        let name = document.getElementById("name_input").value;
        if (name == null || name.length === 0) {
            name = "Anonymous";
        }

        let urlCanDownload = 'http://localhost:8080/can_download/' + code;
        let result = await fetch(urlCanDownload, {
            credentials: 'include'
        });

        let url = 'http://localhost:8080/request_download/' + code + "?name=" + name;
        if (result.status === 200) {
            const anchor = document.createElement("a");
            anchor.href = url;
            document.body.appendChild(anchor);
            anchor.click();
            document.body.removeChild(anchor);
            document.getElementById("request_failed").style.display = "none";
        } else if (result.status === 204){
            await fetch(url, {
                credentials: 'include'
            });
            document.getElementById("request_successful").style.display = "block";
            document.getElementById("request_failed").style.display = "none";
        } else {
            document.getElementById("request_failed").style.display = "block";
            document.getElementById("request_successful").style.display = "none";
        }
    } else {
        document.getElementById("request_failed").style.display = "block";
        document.getElementById("request_successful").style.display = "none";
    }
}