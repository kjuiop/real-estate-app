let onReady = function() {
};

let save = function(e) {
    e.preventDefault();

}

let update = function(e) {
    e.preventDefault();
}

$(document).ready(onReady)
    .on('click', '.btnSave', save)
    .on('click', '.btnUpdate', update)
;