let table_head = document.getElementById("table_head");
let table_body = document.getElementById("table_body");

// operators
const normalOperators = ["IS_ON", "DAY_IS", "IN_THE_LAST", "IN_THE_NEXT", "NOT_IN_THE_LAST", "NOT_IN_THE_NEXT", "AFTER", "BEFORE", "SINCE", "IS_BETWEEN"];
const unresolvedOperators = ["IS_ON", "IN_THE_LAST", "IN_THE_NEXT", "SINCE", "IS_BETWEEN"];

const flowType = "unresolvedTickets"


function fetchJson() {
    fetch('./output.json')
        .then(response => response.json())
        .then(data => {
            fetch('./filter_config.json')
                .then(response => response.json())
                .then(keys => {
                    renderTable(data, keys);
                })
                .catch(error => console.error('Error fetching filter config JSON:', error));
        })
        .catch(error => console.error('Error fetching output JSON:', error));
}

function renderTable(data, keys) {

    if (flowType == "normal") {
        const header = `<tr>
            <th>OPERATOR</th>
            <th>UNIT</th>
            <th>WITHOUT_TZ</th>
            <th>WITH_TZ</th>
        </tr>`;

        table_head.innerHTML = header;

        let cat = '';

        for (let i = 0; i < normalOperators.length; i++) {

            const obj = data[normalOperators[i]];

            const operators = keys[normalOperators[i]]["keys"];

            for (let j = 0; j < operators.length; j++) {

                const insideObj = obj[j][operators[j]];

                const res = Diff.diffChars(insideObj["withOutTZ"], insideObj["withTZ"]);

                let oldTextHtml = "";
                let newTextHtml = "";

                for (let diff of res) {
                    if (diff.added === true) {
                        newTextHtml += `<span style="color:green"><b>${diff.value}</b></span>`;
                    } else if (diff.removed === true) {
                        oldTextHtml += `<span style="color:red"><b>${diff.value}</b></span>`;
                    } else {
                        newTextHtml += `<span>${diff.value}</span>`;
                        oldTextHtml += `<span>${diff.value}</span>`;
                    }
                }

                cat += `<tr>
               <td>${normalOperators[i]}</td>  
               <td>${operators[j]}</td>
                <td>${oldTextHtml}</td>
               <td>${newTextHtml}</td>
               </tr>`

                table_body.innerHTML = cat;
            }


        }

    } else if (flowType == "unresolvedTickets") {

        const header = `<tr>
            <th>OPERATOR</th>
            <th>UNIT</th>
            <th>WITHOUT_TZ</th>
            <th>WITH_TZ</th>
        </tr>`

        table_head.innerHTML = header;

        let cat = '';

        for (let i = 0; i < unresolvedOperators.length; i++) {

            const obj = data[unresolvedOperators[i]];

            const operators = keys[unresolvedOperators[i]]["keys"];

            for (let j = 0; j < operators.length; j++) {

                const insideObj = obj[j][operators[j]];

                const total = Diff.diffChars(insideObj["withOutTZ"]["TOTAL_QUERY"], insideObj["withTZ"]["TOTAL_QUERY"]);

                let oldTextTotal = "";
                let newTextTotal = "";

                for (let diff of total) {
                    if (diff.added === true) {
                        newTextTotal += `<span style="color:green"><b>${diff.value}</b></span>`;
                    } else if (diff.removed === true) {
                        oldTextTotal += `<span style="color:red"><b>${diff.value}</b></span>`;
                    } else {
                        newTextTotal += `<span>${diff.value}</span>`;
                        oldTextTotal += `<span>${diff.value}</span>`;
                    }
                }

                const improv = Diff.diffChars(insideObj["withOutTZ"]["IMPROV_QUERY"], insideObj["withTZ"]["IMPROV_QUERY"]);

                let oldTextImprov = "";
                let newTextImprov = "";

                for (let diff of improv) {
                    if (diff.added === true) {
                        newTextImprov += `<span style="color:green"><b>${diff.value}</b></span>`;
                    } else if (diff.removed === true) {
                        oldTextImprov += `<span style="color:red"><b>${diff.value}</b></span>`;
                    } else {
                        newTextImprov += `<span>${diff.value}</span>`;
                        oldTextImprov += `<span>${diff.value}</span>`;
                    }
                }

                cat += `<tr>
               <td>${unresolvedOperators[i]}</td>  
               <td>${operators[j]}</td>
                <td class="justify">
               <h3>Total Query:</h3>
               <p>${oldTextTotal}</p>
                <pre>

                </pre>
               <h3>Improv Query:</h3>
               <p>${oldTextImprov}</p>
               </td>
               <td class="justify">
               <h3>Total Query:</h3>
               <p>${newTextTotal}</p>
                <pre>
                
                </pre>
               <h3>Improv Query:</h3>
               <p>${newTextImprov}</p>
               </td>
              
               </tr>`

                table_body.innerHTML = cat;

            }
        }
    }

}

fetchJson();

