window.onload = function () {
  fetchTerm("GET", "/api/v1/terms", "all-terms-list");
};

function getWord() {
  const wordValue = document.getElementById("word_name").value;
  sendWord(wordValue);
}

function sendWord(word) {
  fetchTerm("POST", "/api/v1/terms/" + word, "result");
  setTimeout(() => {
    fetchTerm("GET", "/api/v1/terms", "all-terms-list");
  }, 1500);
}

function fetchTerm(verb, url, containerId) {
  const xhttp = new XMLHttpRequest();

  xhttp.open(verb, url, true);
  xhttp.setRequestHeader("Content-type", "application/json");

  xhttp.onreadystatechange = () => {
    if (xhttp.readyState === 4) {
      const responseContainer = document.getElementById(containerId);
      responseContainer.innerHTML = "";

      const jsonResponse = JSON.parse(xhttp.responseText);
      const formattedJson = JSON.stringify(jsonResponse, null, 2);
      responseContainer.innerHTML = "<pre>" + formattedJson + "</pre>";

      if (containerId === "all-terms-list") {
        updateAllTermsList(jsonResponse);
      }
    }
  };

  xhttp.send();
}

// UI-related functions
function createWordButton(term) {
  const wordButton = document.createElement("button");
  wordButton.className = "btn btn-primary text-left";
  wordButton.style.marginBottom = "5px";
  wordButton.style.width = "120px";
  wordButton.innerHTML = `${term.id} : ${term.word}`;
  wordButton.addEventListener("click", () => sendWord(term.word));
  return wordButton;
}

function createDeleteButton(term) {
  const deleteButton = document.createElement("button");
  deleteButton.className = "btn btn-danger";
  deleteButton.style.marginBottom = "5px";
  deleteButton.innerHTML = `<i class="bi bi-trash"></i>`;
  deleteButton.addEventListener("click", () => deleteTerm(term.word));
  return deleteButton;
}

function createTermContainer(wordButton, deleteButton) {
  const termContainer = document.createElement("div");
  termContainer.className =
    "term-container d-flex justify-content-between align-items-center";
  termContainer.appendChild(wordButton);
  termContainer.appendChild(deleteButton);
  return termContainer;
}

function updateAllTermsList(response) {
  const allTermsListContainer = document.getElementById("all-terms-list");
  allTermsListContainer.innerHTML = "";

  const terms = response._embedded.terms;

  terms.forEach((term) => {
    const wordButton = createWordButton(term);
    const deleteButton = createDeleteButton(term);
    const termContainer = createTermContainer(wordButton, deleteButton);

    allTermsListContainer.appendChild(termContainer);
  });
}

function copyResultToClipboard() {
  const resultContainer = document.getElementById("result");
  const textToCopy = resultContainer.textContent;

  navigator.clipboard
    .writeText(textToCopy)
    .then(() => {
      alert("Result copied to clipboard!");
    })
    .catch((err) => {
      console.error("Unable to copy to clipboard", err);
    });
}

function deleteTerm(termId) {
  const url = `/api/v1/terms/${termId}`;
  const xhttp = new XMLHttpRequest();

  xhttp.open("DELETE", url, true);
  xhttp.setRequestHeader("Content-type", "application/json");

  xhttp.onreadystatechange = () => {
    if (xhttp.readyState === 4) {
      setTimeout(() => {
        fetchTerm("GET", "/api/v1/terms", "all-terms-list");
      }, 300);
    }
  };

  xhttp.send();
}
