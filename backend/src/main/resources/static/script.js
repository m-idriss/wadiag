function getWord() {
    const word_name = document.getElementById("word_name").value;

    // Fetch result for the specific term
    fetchTerm("POST", "/api/v1/terms/" + word_name, "result");

    // Fetch all terms
    fetchTerm("GET", "/api/v1/terms", "all-terms-list");
}

function fetchTerm(verb, url, containerId) {
    const xhttp = new XMLHttpRequest();

    xhttp.open(verb, url, true);
    xhttp.setRequestHeader("Content-type", "application/json");

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4) {
            const responseContainer = document.getElementById(containerId);
            responseContainer.innerHTML = "";

            const jsonResponse = JSON.parse(xhttp.responseText);
            const formattedJson = JSON.stringify(jsonResponse, null, 2);
            responseContainer.innerHTML = '<pre>' + formattedJson + '</pre>';
            
        }
    };

    xhttp.send();
}


function copyResultToClipboard() {
    const resultContainer = document.getElementById("result");
    const textToCopy = resultContainer.textContent;

    navigator.clipboard.writeText(textToCopy)
        .then(() => {
            alert("Result copied to clipboard!");
        })
        .catch((err) => {
            console.error('Unable to copy to clipboard', err);
        });
}
