addEventListener("load", (event) => {
    getFilesList();
});

async function getRequests(code) {
    let url = 'http://localhost:8080/requests/' + code;
    return fetch(url, {
        credentials: 'include'
    });
}

async function getFilesList() {
    let url = 'http://localhost:8080/manage_files';
    let response = await fetch(url, {
        credentials: 'include'
    });
    let data = await response.json();
    if (data.length > 0) {
        let element = document.getElementById("no_codes");
        element.remove();
    } else {
        let element = document.getElementById("no_codes");
        element.style.display = "block";
        document.getElementById("codes_container").style.display = "none";
    }

    let ul = document.getElementById("list");
    for (const element of data) {
        let name = "Gracjan";
        let filename = element["fileName"];
        let code = element["code"];

        let li = document.createElement("li");
        ul.appendChild(li);

        let divCodeContainer = document.createElement("div");
        divCodeContainer.classList.add("code-container");
        li.appendChild(divCodeContainer);

        let divCode = document.createElement("div");
        divCode.classList.add("code");
        divCode.appendChild(document.createTextNode(code));
        divCodeContainer.appendChild(divCode);

        let divFileName = document.createElement("div");
        divFileName.classList.add("file_name");
        divFileName.appendChild(document.createTextNode("Filename: " + filename));
        divCodeContainer.appendChild(divFileName);

        let divRequestList = document.createElement("div");
        divRequestList.classList.add("request_list");
        divCodeContainer.appendChild(divRequestList);

        console.log(element);
        let requestsResponse = await getRequests(element["code"]);
        let requests = await requestsResponse.json();
        console.log(requests);
        requests.forEach((request) => {
            console.log(request);
            let requesterName = request["requesterName"];
            let requestId = request["id"];
            let divRequest = document.createElement("div");
            divRequest.classList.add("request");
            divRequestList.appendChild(divRequest);

            let divRequesterName = document.createElement("div");
            divRequesterName.classList.add("requester-name");
            divRequesterName.appendChild(document.createTextNode("Request from " + requesterName));
            divRequest.appendChild(divRequesterName);

            let buttonAccept = document.createElement("button");
            buttonAccept.appendChild(document.createTextNode("Accept "));
            buttonAccept.onclick = function() {
                console.log("Akceptowano id: " + requestId);
                let acceptUrl = 'http://localhost:8080/accept_request?requestId=' + requestId;
                fetch(acceptUrl, {
                    method: 'POST',
                    credentials: 'include'
                });
                divRequestList.removeChild(divRequest);
            };
            divRequest.appendChild(buttonAccept);
            let buttonDecline = document.createElement("button");
            buttonDecline.appendChild(document.createTextNode(" Decline"));
            buttonDecline.onclick = function() {
                console.log("Odrzucono id: " + requestId);
                let declineUrl = 'http://localhost:8080/decline_request?requestId=' + requestId;
                fetch(declineUrl, {
                    method: 'POST',
                    credentials: 'include'
                });
                divRequestList.removeChild(divRequest);
            }
            divRequest.appendChild(buttonDecline);
        });
        ///

        ///

    }
}